package com.goo.curiosities.util;

import com.goo.curiosities.common.ClientProxy;
import com.goo.curiosities.common.item.FootstepCurioItem;
import com.goo.curiosities.common.networking.serverbound.FootstepPayload;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FootstepTracker {
    // stores previous frame's cosine value per entity
    private static final Map<UUID, Float> PREV_PHASE_MAP = new HashMap<>();

    public static void remove(LivingEntity entity) {
        PREV_PHASE_MAP.remove(entity.getUUID());
    }

    public static void clear() {
        PREV_PHASE_MAP.clear();
    }

    public static void processEntityFootsteps(LivingEntity entity) {
        // execute exclusively on the client thread to avoid thread race conditions
        if (!entity.level().isClientSide()) {
            return;
        }
        // we use payloads later

        UUID uuid = entity.getUUID();
        float limbSwingAmount = entity.walkAnimation.speed();

        // purge state when standing still or airborne so landing resets cleanly
        if (limbSwingAmount < 0.05F || !isEligibleForFootsteps(entity)) {
            PREV_PHASE_MAP.remove(uuid);
            return;
        }

        float limbSwing = entity.walkAnimation.position();
        float currentPhase = Mth.cos(limbSwing * 0.6662F);

        // seed phase tracking on the first grounded frame without triggering a step
        if (!PREV_PHASE_MAP.containsKey(uuid)) {
            PREV_PHASE_MAP.put(uuid, currentPhase);
            return;
        }

        float prevPhase = PREV_PHASE_MAP.get(uuid);
        PREV_PHASE_MAP.put(uuid, currentPhase);

        // edge-triggered sign changes (strictly fires on the single frame crossing zero)
        boolean leftFootPlanted = prevPhase <= 0.0F && currentPhase > 0.0F;
        boolean rightFootPlanted = prevPhase >= 0.0F && currentPhase < 0.0F;

        if (!leftFootPlanted && !rightFootPlanted) {
            return;
        }

        Map<Item, Long> itemCounts = CuriosApi.getCuriosInventory(entity)
                .map(handler -> handler.findCurios(stack -> stack.getItem() instanceof FootstepCurioItem)
                        .stream()
                        .map(slotResult -> slotResult.stack().getItem())
                        .collect(Collectors.groupingBy(item -> item, Collectors.counting())))
                .orElse(Collections.emptyMap());

        if (itemCounts.isEmpty()) {
            return;
        }

        float partialTick = entity.level().isClientSide() ? ClientProxy.getPartialTick() : 0.0F;
        Vec3 position = getPosition(entity, leftFootPlanted, partialTick);
        itemCounts.forEach((item, count) -> {
            if (item instanceof FootstepCurioItem footstepCurioItem) {
                handleFootstep(position, entity, footstepCurioItem, leftFootPlanted, count.intValue(), partialTick);
            }
        });

        PacketDistributor.sendToServer(new FootstepPayload(position.toVector3f(), leftFootPlanted));
    }

    private static boolean isEligibleForFootsteps(LivingEntity entity) {
        if (!entity.onGround()) {
            return false;
        }
        if (entity.isSwimming() || entity.isFallFlying()) {
            return false;
        }
        Pose pose = entity.getPose();
        return pose != Pose.SWIMMING && pose != Pose.FALL_FLYING;
    }

    private static Vec3 getPosition(LivingEntity entity, boolean isLeftFoot, float partialTick) {

        float bodyYaw = entity.getPreciseBodyRotation(partialTick);
        float bodyYawRad = bodyYaw * ((float) Math.PI / 180.0F);

        double rightX = Mth.cos(bodyYawRad);
        double rightZ = Mth.sin(bodyYawRad);

        float bbWidth = entity.getBbWidth();
        double legSeparation = bbWidth * 0.22D;
        double sideFactor = isLeftFoot ? -1.0D : 1.0D;
        double px = entity.getX() + (rightX * legSeparation * sideFactor);
        double py = entity.getY() + 0.01D;
        double pz = entity.getZ() + (rightZ * legSeparation * sideFactor);

        return new Vec3(px, py, pz);
    }

    public static void handleFootstep(Vec3 position, LivingEntity entity, FootstepCurioItem curioItem, boolean isLeftFoot, int amount, float partialTick) {
        Level level = entity.level();
        float footprintSize = curioItem.getFootprintSize(entity, curioItem);

        boolean hasValidSurface = hasValidFootprintSurface(level, position.x(), position.y(), position.z(), footprintSize);

        curioItem.onFootstep(entity, position, curioItem, isLeftFoot, hasValidSurface, amount, partialTick);
    }

    private static boolean hasValidFootprintSurface(Level level, double px, double py, double pz, float footprintSize) {
        double halfSize = footprintSize / 2.0D;

        AABB footprintBox = new AABB(
                px - halfSize, py - 0.2D, pz - halfSize,
                px + halfSize, py + 0.05D, pz + halfSize
        );

        Iterable<VoxelShape> collisions = level.getBlockCollisions(null, footprintBox);

        for (VoxelShape shape : collisions) {
            if (shape.isEmpty()) continue;

            for (AABB box : shape.toAabbs()) {
                if (box.maxY >= 0.95D) {
                    return true;
                }
            }
        }

        return false;
    }
}
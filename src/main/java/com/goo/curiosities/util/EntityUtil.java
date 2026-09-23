package com.goo.curiosities.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class EntityUtil {

    /**
     * @return The most optimal and accurate damage source depending if the entity was a player or mob
     */
    public static DamageSource getOptimalDamageSource(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) return livingEntity.damageSources().playerAttack(player);
        return livingEntity.damageSources().mobAttack(livingEntity);
    }
    public static boolean isAlly(Entity tester, Entity toTest) {
        if (tester == null || toTest == null) return false;
        if (tester.equals(toTest)) return true;

        // 1. Vanilla team / scoreboard check
        if (tester.isAlliedTo(toTest) || toTest.isAlliedTo(tester)) {
            return true;
        }

        // 2. Tameable / Owner relationship check
        if (tester instanceof OwnableEntity ownableTester) {
            UUID ownerUUID = ownableTester.getOwnerUUID();
            if (ownerUUID != null) {
                if (toTest.getUUID().equals(ownerUUID)) return true;
                if (toTest instanceof OwnableEntity ownableToTest && ownerUUID.equals(ownableToTest.getOwnerUUID())) {
                    return true;
                }
            }
        }
        if (toTest instanceof OwnableEntity ownableToTest && tester.getUUID().equals(ownableToTest.getOwnerUUID())) {
            return true;
        }

        // 3. Iron Golems created by players
        if (tester instanceof IronGolem golem && toTest instanceof Player) {
            return golem.isPlayerCreated();
        }
        if (toTest instanceof IronGolem golem && tester instanceof Player) {
            return golem.isPlayerCreated();
        }

        // 4. Same species check (exclude Players and Hostile mobs from automatic ally status)
        if (tester.getType() == toTest.getType()) {
            // Players are never allies by default unless on a team
            if (tester instanceof Player) {
                return false;
            }

            // Passive/Ambient mobs of the same type are allies (e.g. cows in a herd)
            return tester instanceof AgeableMob || tester instanceof AmbientCreature;
        }

        return false;
    }

    @Nullable
    public static Double getClosestGroundY(Entity entity, int threshold) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(entity.getX(), entity.getY(), entity.getZ());
        Level level = entity.level();

        while (threshold > 0 && level.getBlockState(mutableBlockPos).isAir() && mutableBlockPos.getY() > level.getMinBuildHeight()) {
            BlockPos below = mutableBlockPos.below();
            if (!level.getBlockState(below).isAir()) {
                mutableBlockPos.move(Direction.DOWN);
                break;
            }

            mutableBlockPos.move(Direction.DOWN);
            threshold--;
        }

        // If we finished the loop and it's still air, we didn't find any ground
        if (level.getBlockState(mutableBlockPos).isAir()) {
            return null;
        }

        double maxHeight = level.getBlockState(mutableBlockPos).getShape(level, mutableBlockPos).max(Direction.Axis.Y, 0.0, 0.0);

        if (!Double.isFinite(maxHeight)) {
            return null;
        }

        // Combine the block's base Y coordinate with its precise collision surface offset
        return mutableBlockPos.getY() + maxHeight;
    }


    public static List<LivingEntity> getFoes(LivingEntity entity, float radius) {
        return entity.level().getNearbyEntities(
                LivingEntity.class,
                TargetingConditions.forCombat()
                        .range(radius)
                        .selector(e -> !EntityUtil.isAlly(entity, e)),
                entity,
                entity.getBoundingBox().inflate(radius)
        );
    }


    public static List<Entity> pullEntities(Level level, AABB aabb, float pullStrength, Predicate<Entity> predicate) {
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, aabb, predicate);

        for (Entity entity : entities) {
            Vec3 toHolder = entity.getBoundingBox().getCenter().vectorTo(aabb.getCenter());
            float maxDist = (float) Math.max(aabb.getXsize(), aabb.getZsize()); // x and z should always be equal, but just in case
            float dist = Mth.sqrt((float) entity.distanceToSqr(aabb.getCenter()));
            float scale = Math.max(0.0F, (maxDist - dist) / maxDist) * pullStrength; // scale 0.0 to 0.1

            toHolder = toHolder.normalize().scale(scale);
            entity.push(toHolder);
            if (entity instanceof ServerPlayer player) {
                player.connection.send(new ClientboundSetEntityMotionPacket(player));
            }
        }
        return entities;
    }

    public static List<Entity> getEntitiesInSphere(Level level, Vec3 origin, float radius) {
        return level.getEntities((Entity) (null), new AABB(origin, origin).inflate(radius), e -> e.distanceToSqr(origin) <= (radius * radius));
    }
}
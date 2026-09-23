package com.goo.curiosities.common.item.curio.ring;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.network.serverbound.DamageEntityPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.SlotContext;

public class RoadrunnersRing extends CuriositiesCurioItem {

    private static final double MIN_COLLISION_SPEED_SQR = 0.16D;
    // around 45 deg
    private static final double MIN_IMPACT_ANGLE_DOT = 0.707D;
    // tracks previous frame velocity strictly for the local client player
    private static Vec3 lastClientVelocity = Vec3.ZERO;

    public RoadrunnersRing(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) return;

        if (player.level().isClientSide()) {
            // only evaluate for the local player instance
            if (player != Minecraft.getInstance().player) return;

            double lastHorizontalSpeedSqr = lastClientVelocity.x * lastClientVelocity.x + lastClientVelocity.z * lastClientVelocity.z;

            if (player.horizontalCollision && lastHorizontalSpeedSqr > MIN_COLLISION_SPEED_SQR) {
                Vec3 moveDir = new Vec3(lastClientVelocity.x, 0, lastClientVelocity.z).normalize();
                Vec3 wallNormal = getWallNormal(player);

                // calculate directness of impact (1.0 = head-on, 0.0 = parallel)
                double impactDirectness = Math.abs(moveDir.dot(wallNormal));

                if (impactDirectness >= MIN_IMPACT_ANGLE_DOT) {
                    float damage = (float) (lastHorizontalSpeedSqr * 25.0D * impactDirectness);
                    triggerCollisionEffects(player, damage);
                    lastClientVelocity = Vec3.ZERO;
                    return;
                }
            }
                lastClientVelocity = player.getDeltaMovement();
        }
    }

    private Vec3 getWallNormal(Player player) {
        BlockPos pos = player.blockPosition();
        Level level = player.level();

        // test surrounding cardinal blocks for solid collision faces
        if (level.getBlockState(pos.east()).isSolidRender(level, pos.east())) return new Vec3(-1, 0, 0);
        if (level.getBlockState(pos.west()).isSolidRender(level, pos.west())) return new Vec3(1, 0, 0);
        if (level.getBlockState(pos.south()).isSolidRender(level, pos.south())) return new Vec3(0, 0, -1);
        if (level.getBlockState(pos.north()).isSolidRender(level, pos.north())) return new Vec3(0, 0, 1);

        // fallback to inverse horizontal movement direction
        return new Vec3(-lastClientVelocity.x, 0, -lastClientVelocity.z).normalize();
    }

    private void triggerCollisionEffects(Player player, float damage) {
        PacketDistributor.sendToServer(new DamageEntityPayload(
                player.getId(),
                damage,
                player.damageSources().flyIntoWall().typeHolder().getKey(),
                player.getId(),
                player.getId()
        ));

        SoundEvent sound = damage > 4.0F ? SoundEvents.GENERIC_BIG_FALL : SoundEvents.GENERIC_SMALL_FALL;
        player.playSound(sound, 1.0F, Mth.nextFloat(player.getRandom(), 0.8F, 1.2F));
    }
}
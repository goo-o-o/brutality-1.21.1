package com.goo.curiosities.common.item.curio.charm.bottles;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.item.DoubleJumpCurioItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SandstormInABottle extends DoubleJumpCurioItem {

    private static final double BOOST_PER_TICK = 0.055;

    public SandstormInABottle(Properties properties) {
        super(properties);
    }

    @Override
    protected double getHorizontalAccelerationMultiplier(LivingEntity livingEntity) {
        return 2;
    }

    @Override
    protected double getHorizontalSpeedModifier(LivingEntity livingEntity) {
        return 3;
    }

    @Override
    public int getTotalDoubleJumpTicks(LivingEntity livingEntity) {
        return 16;
    }

    @Override
    public void onWearerDoubleJumpTick(LivingEntity livingEntity, int ticks) {
        Vec3 current = livingEntity.getDeltaMovement();

        double horizontalAccelerationMultiplier = getHorizontalAccelerationMultiplier(livingEntity);

        double newX = current.x * horizontalAccelerationMultiplier;
        double newZ = current.z * horizontalAccelerationMultiplier;
        double newY = current.y + BOOST_PER_TICK;

        livingEntity.setDeltaMovement(applyHorizontalCap(livingEntity, newX, newZ, newY));

        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    CuriositiesParticles.SANDSTORM_SMOKE.get(),
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    3,
                    0.05,
                    0.05,
                    0.05,
                    0.075
            );
            serverLevel.sendParticles(
                    CuriositiesParticles.SANDSTORM_DUST.get(),
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    10,
                    0.05,
                    0.05,
                    0.05,
                    0.15
            );
        }
    }

    @Override
    public void onWearerStartDoubleJump(LivingEntity livingEntity) {
        super.onWearerStartDoubleJump(livingEntity);
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    CuriositiesParticles.SANDSTORM_SMOKE.get(),
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    10,
                    0,
                    0,
                    0,
                    0.0175
            );
        }
    }

    @Override
    public void onWearerEndDoubleJump(LivingEntity livingEntity) {
        Vec3 current = livingEntity.getDeltaMovement();

        if (current.y > 0) {
            livingEntity.setDeltaMovement(current.x, current.y * 0.5, current.z);
        }
        super.onWearerEndDoubleJump(livingEntity);
    }


}
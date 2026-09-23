package com.goo.curiosities.common.item.curio.charm.bottles;

import com.goo.curiosities.common.item.DoubleJumpCurioItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class TsunamiInABottle extends DoubleJumpCurioItem {
    public TsunamiInABottle(Properties properties) {
        super(properties);
    }

    @Override
    protected double getBaseInitialBoost(LivingEntity livingEntity) {
        return 1.0;
    }

    @Override
    protected double getHorizontalAccelerationMultiplier(LivingEntity livingEntity) {
        return 1.5;
    }

    @Override
    protected double getHorizontalSpeedModifier(LivingEntity livingEntity) {
        return 1.25;
    }

    @Override
    public int getTotalDoubleJumpTicks(LivingEntity livingEntity) {
        // 5 * 1.25 + 1
        return (int) 7.25;
    }

    @Override
    public void onWearerDoubleJumpTick(LivingEntity livingEntity, int ticks) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FALLING_DRIPSTONE_WATER,
                    livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                    10, 0.15, 0.15, 0.15, 0.25);
        }
    }

    @Override
    public void onWearerStartDoubleJump(LivingEntity livingEntity) {
        super.onWearerStartDoubleJump(livingEntity);
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FALLING_DRIPSTONE_WATER,
                    livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                    40, 0.25, 0.25, 0.25, 0.25);
        }
    }
}

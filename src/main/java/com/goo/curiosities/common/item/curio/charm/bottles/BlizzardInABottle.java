package com.goo.curiosities.common.item.curio.charm.bottles;

import com.goo.curiosities.common.item.DoubleJumpCurioItem;
import com.goo.curiosities.util.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class BlizzardInABottle extends DoubleJumpCurioItem {
    public BlizzardInABottle(Properties properties) {
        super(properties);
    }

    @Override
    protected double getHorizontalSpeedModifier(LivingEntity livingEntity) {
        return 3;
    }

    @Override
    protected double getHorizontalAccelerationMultiplier(LivingEntity livingEntity) {
        return 2.5;
    }

    @Override
    protected double getBaseInitialBoost(LivingEntity livingEntity) {
        return 1.25;
    }

    @Override
    public int getTotalDoubleJumpTicks(LivingEntity livingEntity) {
        // 5 x 1.5 + 1
        return (int) 8.5;
    }

    @Override
    public void onWearerStartDoubleJump(LivingEntity livingEntity) {
        super.onWearerStartDoubleJump(livingEntity);
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            ParticleUtil.burstParticles(
                    serverLevel,
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    30,
                    0.25,
                    ParticleTypes.SNOWFLAKE
            );
        }
    }

    @Override
    public void onWearerDoubleJumpTick(LivingEntity livingEntity, int ticks) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 20, 0.1, 0.1, 0.1, 0.25);
        }
    }
}

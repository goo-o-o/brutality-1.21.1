package com.goo.curiosities.common.item.curio.charm.bottles;

import com.goo.curiosities.common.item.DoubleJumpCurioItem;
import com.goo.curiosities.util.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class CloudInABottle extends DoubleJumpCurioItem {
    public CloudInABottle(Properties properties) {
        super(properties);
    }

    @Override
    protected double getHorizontalSpeedModifier(LivingEntity livingEntity) {
        return 1.15;
    }

    @Override
    protected double getHorizontalAccelerationMultiplier(LivingEntity livingEntity) {
        return 1.15;
    }

    @Override
    protected double getBaseInitialBoost(LivingEntity livingEntity) {
        return 0.85;
    }

    @Override
    public int getTotalDoubleJumpTicks(LivingEntity livingEntity) {
        // 5 x 0.75 + 1
        return (int) 4.75;
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
                    10,
                    0.25,
                    ParticleTypes.CLOUD
            );
            serverLevel.sendParticles(ParticleTypes.GUST,
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    1, 0.25, 0.25, 0.25, 0
            );
        }
    }

    @Override
    public void onWearerDoubleJumpTick(LivingEntity livingEntity, int ticks) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CLOUD, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 2, 0.1, 0.1, 0.1, 0.25);
            serverLevel.sendParticles(ParticleTypes.SMALL_GUST, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 2, 0.1, 0.1, 0.1, 0.25);
        }
    }
}

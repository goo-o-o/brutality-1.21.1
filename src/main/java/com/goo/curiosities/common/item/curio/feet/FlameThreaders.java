package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.item.FootstepCurioItem;
import com.goo.curiosities.util.EntityUtil;
import com.goo.goo_lib.client.particle.WaveParticleOption;
import com.goo.goo_lib.util.Easing;
import com.goo.goo_lib.util.phys.ShockwaveUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class FlameThreaders extends FlameWalkers {


    public FlameThreaders(Properties properties) {
        super(properties);
        speedBonus = 0.45F;
    }

    protected float getWaveSize(LivingEntity livingEntity, int amount) {
        return 2;
    }

    @Override
    public void onFootstep(LivingEntity livingEntity, Vec3 position, FootstepCurioItem curioItem, boolean left, boolean hasValidSurface, int amount, float partialTick) {
        super.onFootstep(livingEntity, position, curioItem, left, hasValidSurface, amount, partialTick);

        // wave and fire wave
        if (hasValidSurface) {
            WaveParticleOption particleOption = new WaveParticleOption(
                    CuriositiesParticles.FIRE_WAVE.get(),
                    getWaveSize(livingEntity, amount),
                    90.0F,
                    -livingEntity.getPreciseBodyRotation(partialTick),
                    0.0F,
                    15,
                    Easing.EASE_OUT_SINE
            );

            if (livingEntity.level().isClientSide()) {
                livingEntity.level().addParticle(
                        particleOption,
                        position.x(), position.y(), position.z(),
                        0.0D, 0.0D, 0.0D
                );
            } else {
                ServerLevel serverLevel = ((ServerLevel) livingEntity.level());
                ShockwaveUtils.applyWaveEffect(
                        serverLevel,
                        position.x(),
                        position.y(),
                        position.z(),
                        LivingEntity.class,
                        particleOption,
                        e ->
                                !EntityUtil.isAlly(e, livingEntity),
                        e -> e.igniteForSeconds(3));

            }
        }
    }
}

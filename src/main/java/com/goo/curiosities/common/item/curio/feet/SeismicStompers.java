package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.goo_lib.client.particle.WaveParticleOption;
import com.goo.goo_lib.util.Easing;
import com.goo.goo_lib.util.phys.ShockwaveUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public class SeismicStompers extends CuriositiesCurioItem {
    public SeismicStompers(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerFall(LivingFallEvent event, ItemStack curio) {
        float fallDistance = event.getDistance();
        if (fallDistance < 4) return;

        LivingEntity wearer = event.getEntity();
        Level level = wearer.level();
        float radius = fallDistance * 0.35F;
        DamageSource source = wearer instanceof Player player ? wearer.damageSources().playerAttack(player) : wearer.damageSources().mobAttack(wearer);

        WaveParticleOption waveParticleData = new WaveParticleOption(
                CuriositiesParticles.SEISMIC_SHOCKWAVE.get(),
                radius,
                90,
                0,
                0,
                10,
                Easing.EASE_OUT_SINE);


        if (level instanceof ServerLevel serverLevel) {


            level.playSound(
                    null,
                    wearer.getX(),
                    wearer.getY(0.5),
                    wearer.getZ(),
                    CuriositiesSounds.GROUND_SMASH,
                    SoundSource.BLOCKS,
                    1F,0.6F
                    );

            serverLevel.sendParticles(
                    waveParticleData,
                    wearer.getX(),
                    wearer.getY(0.1),
                    wearer.getZ(),
                    1,
                    0, 0, 0,
                    0
            );

            ShockwaveUtils.applyWaveEffect(
                    serverLevel,
                    wearer.getPosition(0).add(0, 0.05, 0),
                    LivingEntity.class,
                    waveParticleData,
                    e -> e != wearer,
                    e -> {
                        Vec3 from = e.getPosition(0).subtract(wearer.getPosition(0));
                        float intensity = (float) (radius - from.length()); // closer should be stronger

                        from.scale(fallDistance * intensity);

                        e.push(from.x, from.y + intensity * 0.5, from.z);
                        e.hurt(source, intensity * 3);
                    }
            );
        }
    }
}

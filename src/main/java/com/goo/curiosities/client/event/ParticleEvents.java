package com.goo.curiosities.client.event;

import com.goo.curiosities.client.particle.CompositeParticle;
import com.goo.curiosities.client.particle.CompositeParticlePresets;
import com.goo.curiosities.client.particle.custom.*;
import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.util.Colors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class ParticleEvents {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {


        event.registerSpriteSet(CuriositiesParticles.OMEGA.get(), sprites ->
                new CompositeParticle.Provider(sprites, (p, level, xs, ys, zs) -> {
                    CompositeParticlePresets.ROTATING_AGEABLE_SPRITE_RANDOM_MOVEMENT.configure(p, level, xs, ys, zs);
                    p.setRandomRotation(0.5F, 0.5F);
                    p.withTickBehavior(CompositeParticlePresets.OMEGA_COLOR);
                    p.setFullbright(true);
                    p.setColor(Colors.FIRE[0]);
                })
        );


        event.registerSpriteSet(CuriositiesParticles.SANDSTORM_SMOKE.get(), SandstormSmokeParticle.Provider::new);
        event.registerSpriteSet(CuriositiesParticles.SANDSTORM_DUST.get(), SandstormDustParticle.Provider::new);
        event.registerSpriteSet(CuriositiesParticles.ONOMATOPOEIA.get(), OnomatopoeiaParticle.Provider::new);
        event.registerSpecial(CuriositiesParticles.MULTIPLIER.get(), new MultiplierParticle.Provider());
        event.registerSpriteSet(CuriositiesParticles.MOLTEN_FOOTPRINT.get(), MoltenFootprintParticle.Provider::new);
        event.registerSpriteSet(CuriositiesParticles.SEISMIC_SHOCKWAVE.get(), AlphaFadeWaveParticle.Provider::new);
        event.registerSpriteSet(CuriositiesParticles.FIRE_WAVE.get(), FireWaveParticle.Provider::new);
    }

}

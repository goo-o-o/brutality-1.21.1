package com.goo.brutality.client.event;

import com.goo.brutality.client.particle.CompositeParticle;
import com.goo.brutality.client.particle.CompositeParticlePresets;
import com.goo.brutality.client.particle.custom.BloodsplosionSeedParticle;
import com.goo.brutality.client.particle.custom.HexingCircleParticle;
import com.goo.brutality.client.particle.custom.MultiplierParticle;
import com.goo.brutality.client.particle.custom.OnomatopoeiaParticle;
import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.Colors;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class ParticleEvents {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BrutalityParticles.STYGIAN_SOUL.get(), sprites -> new CompositeParticle.Provider(sprites, (p, level, xs, ys, zs) -> {
            p.setSpriteFromAge(sprites);
            p.setLifetime(Math.max(1, 30 + (level.getRandom().nextInt(2) - 1)));
            p.setGravity(-0.1F);
            p.setSize(0.8F, 0.8F);
            p.setQuadSize(p.getQuadSize() * 2);
            p.withTickBehavior(CompositeParticlePresets.AGEABLE_SPRITE);
        }));


        event.registerSpriteSet(BrutalityParticles.ENRAGED.get(), sprites ->
                new CompositeParticle.Provider(sprites, CompositeParticlePresets.POTION_EFFECT_RANDOM_SPRITE));
        event.registerSpriteSet(BrutalityParticles.ASURA.get(), sprites ->
                new CompositeParticle.Provider(sprites, (p, level, xs, ys, zs) -> {
                    CompositeParticlePresets.POTION_EFFECT_AGEABLE_SPRITE.configure(p, level, xs, ys, zs);
                    p.withTickBehavior(CompositeParticlePresets.ASURA_COLOR);
                    p.setColor(Colors.TOLEDO);
                    p.setQuadSize(p.getQuadSize() * 5);
                })
        );

        event.registerSpriteSet(BrutalityParticles.TRANQUILITY.get(), sprites ->
                new CompositeParticle.Provider(sprites, CompositeParticlePresets.POTION_EFFECT_AGEABLE_SPRITE));

        event.registerSpriteSet(BrutalityParticles.MATH.get(), sprites ->
                new CompositeParticle.Provider(sprites, CompositeParticlePresets.POTION_EFFECT_RANDOM_SPRITE));
        event.registerSpriteSet(BrutalityParticles.DIVISION.get(), sprites ->
                new CompositeParticle.Provider(sprites, CompositeParticlePresets.POTION_EFFECT_RANDOM_SPRITE));
        event.registerSpriteSet(BrutalityParticles.OMEGA.get(), sprites ->
                new CompositeParticle.Provider(sprites, (p, level, xs, ys, zs) -> {
                    CompositeParticlePresets.POTION_EFFECT_AGEABLE_SPRITE.configure(p, level, xs, ys, zs);
                    p.setRandomRotation(2.5F, 2.5F);
                    p.withTickBehavior(CompositeParticlePresets.OMEGA_COLOR);
                    p.setFullbright(true);
                    p.setColor(Colors.BRIGHT_YELLOW);
                })
        );

        event.registerSpriteSet(BrutalityParticles.BLACK_HOLE.get(), sprites ->
                new CompositeParticle.Provider(sprites, (p, level, xs, ys, zs) -> {
                    p.setLifetime(Math.max(1, 30 + (level.getRandom().nextInt(2) - 1)));
                    CompositeParticlePresets.POTION_EFFECT_AGEABLE_SPRITE.configure(p, level, xs, ys, zs);
                }));

        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_BLACK.get(), sprites -> new CompositeParticle.Provider(sprites, CompositeParticlePresets.POKER_CHIP));
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_BLUE.get(), sprites -> new CompositeParticle.Provider(sprites, CompositeParticlePresets.POKER_CHIP));
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_GREEN.get(), sprites -> new CompositeParticle.Provider(sprites, CompositeParticlePresets.POKER_CHIP));
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_RED.get(), sprites -> new CompositeParticle.Provider(sprites, CompositeParticlePresets.POKER_CHIP));
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_RED_INVERSE.get(), sprites -> new CompositeParticle.Provider(sprites, CompositeParticlePresets.POKER_CHIP));
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_YELLOW.get(), sprites -> new CompositeParticle.Provider(sprites, CompositeParticlePresets.POKER_CHIP));

        event.registerSpriteSet(BrutalityParticles.ONOMATOPOEIA.get(), OnomatopoeiaParticle.Provider::new);
        event.registerSpecial(BrutalityParticles.BLOODSPLOSION_EMITTER.get(), new BloodsplosionSeedParticle.Provider());
        event.registerSpriteSet(BrutalityParticles.BLOODSPLOSION.get(), HugeExplosionParticle.Provider::new);
        event.registerSpecial(BrutalityParticles.MULTIPLIER.get(), new MultiplierParticle.Provider());
        event.registerSpriteSet(BrutalityParticles.HEXING_CIRCLE.get(), HexingCircleParticle.Provider::new);
    }

}

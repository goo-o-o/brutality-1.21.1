package com.goo.brutality.client.event;

import com.goo.brutality.client.particle.custom.*;
import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.Brutality;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class ParticleEvents {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BrutalityParticles.STYGIAN_SOUL.get(), StygianParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.ENRAGED.get(), PotionEffectParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.ASURA.get(), AsuraParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.TRANQUILITY.get(), PotionEffectParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.BLACK_HOLE.get(), BlackHoleParticle.Provider::new);

        event.registerSpecial(BrutalityParticles.BLOODSPLOSION_EMITTER.get(), new BloodsplosionSeedParticle.Provider());
        event.registerSpriteSet(BrutalityParticles.BLOODSPLOSION.get(), HugeExplosionParticle.Provider::new);

        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_BLACK.get(), PokerChipParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_BLUE.get(), PokerChipParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_GREEN.get(), PokerChipParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_RED.get(), PokerChipParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_RED_INVERSE.get(), PokerChipParticle.Provider::new);
        event.registerSpriteSet(BrutalityParticles.POKER_CHIP_YELLOW.get(), PokerChipParticle.Provider::new);

        event.registerSpecial(BrutalityParticles.MULTIPLIER.get(), new MultiplierParticle.Provider());

        event.registerSpriteSet(BrutalityParticles.HEXING_CIRCLE.get(), HexingCircleParticle.Provider::new);
    }


}

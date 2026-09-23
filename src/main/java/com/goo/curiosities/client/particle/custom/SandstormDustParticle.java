package com.goo.curiosities.client.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SandstormDustParticle extends BaseAshSmokeParticle {

    protected SandstormDustParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed,
            float quadSizeMultiplier,
            SpriteSet sprites
    ) {
        super(level, x, y, z, 0.1F, 0.1F, 0.1F, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, 0.3F, 8, -0.1F, true);        RandomSource random = level.random;

        // base tan/sand color components (r: 0.85-0.95, g: 0.70-0.82, b: 0.45-0.58)
        float red = 0.85f + random.nextFloat() * 0.10f;
        float green = 0.70f + random.nextFloat() * 0.12f;
        float blue = 0.45f + random.nextFloat() * 0.13f;

        // subtle brightness variation per particle instance
        float brightnessShift = (random.nextFloat() - 0.5f) * 0.08f;

        float finalR = Math.clamp(red + brightnessShift, 0.0f, 1.0f);
        float finalG = Math.clamp(green + brightnessShift, 0.0f, 1.0f);
        float finalB = Math.clamp(blue + brightnessShift, 0.0f, 1.0f);

        this.setColor(finalR, finalG, finalB);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(
                SimpleParticleType type,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            return new SandstormDustParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, this.sprites);
        }
    }
}

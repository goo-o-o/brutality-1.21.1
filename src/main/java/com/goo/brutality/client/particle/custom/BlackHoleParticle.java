package com.goo.brutality.client.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlackHoleParticle extends SimpleAnimatedParticle {
    protected BlackHoleParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, sprites, 0);
        setLifetime(Math.max(1, 30 + (this.random.nextInt(2) - 1)));
        hasPhysics = false;
        setParticleSpeed(xSpeed, ySpeed, zSpeed);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BlackHoleParticle(level, x, y, z, spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
}

package com.goo.brutality.client.particle.custom;

import com.goo.brutality.client.registry.BrutalityParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionSeedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BloodsplosionSeedParticle extends HugeExplosionSeedParticle {
    protected BloodsplosionSeedParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Override
    public void tick() {
        for (int i = 0; i < 6; i++) {
            double d0 = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double d1 = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double d2 = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            this.level.addParticle(BrutalityParticles.BLOODSPLOSION.get(), d0, d1, d2, (float)this.age / (float)this.lifetime, 0.0, 0.0);
        }

        this.age++;
        if (this.age == this.lifetime) {
            this.remove();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
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
            return new BloodsplosionSeedParticle(level, x, y, z);
        }
    }
}

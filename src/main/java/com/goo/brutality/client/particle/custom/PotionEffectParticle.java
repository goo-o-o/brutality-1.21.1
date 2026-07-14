package com.goo.brutality.client.particle.custom;

import com.goo.brutality.client.particle.FloatingRotatingRandomSpriteParticle;
import com.goo.goo_lib.util.Easing;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

/**
 * Used for potion effect particles since they are spawned with speed of 1 in all 3 vectors which was used as the color
 */
public class PotionEffectParticle extends FloatingRotatingRandomSpriteParticle {

    protected PotionEffectParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        setLifetime(level.getRandom().nextIntBetweenInclusive(20, 40));
        int direction = level.getRandom().nextBoolean() ? -1 : 1;
        angularVelocity = 0.5F * direction;
        angularAcceleration = (0.5F / (lifetime / 2F)) * -direction;
    }



    @Override
    public void tick() {
        float progress = Mth.clamp((float) this.age / (float) (this.lifetime - 1), 0.0F, 1.0F);
        float eased = Easing.EASE_IN_QUAD.ease(1.0F - progress);
        this.setAlpha(eased);


        super.tick();
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    /**
     * Used for certain potion effect particles since Minecraft spawns them with xSpeed, ySpeed and zSpeed = 1
     */
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new PotionEffectParticle(level, x, y, z,
                    Mth.nextFloat(level.getRandom(), -0.25F, 0.25F),
                    Mth.nextFloat(level.getRandom(), 0.1F, 0.25F),
                    Mth.nextFloat(level.getRandom(), -0.25F, 0.25F),
                    spriteSet);
        }
    }
}

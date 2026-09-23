package com.goo.curiosities.client.particle.custom;

import com.goo.goo_lib.client.particle.WaveParticle;
import com.goo.goo_lib.client.particle.WaveParticleOption;
import com.goo.goo_lib.util.Easing;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlphaFadeWaveParticle extends WaveParticle {
    public AlphaFadeWaveParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float radius, float pitch, float yaw, float roll, int growthDuration, Easing easing) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, radius, pitch, yaw, roll, growthDuration, easing);
    }

    @Override
    protected int getLightColor(float partialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    @Override
    public void tick() {
        super.tick();

        float progress = (float) this.age / (float) this.lifetime;
        this.setAlpha(1 -progress);
    }


    public static class Provider implements ParticleProvider<WaveParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public @Nullable Particle createParticle(WaveParticleOption data, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AlphaFadeWaveParticle waveParticle = new AlphaFadeWaveParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, data.radius(), data.rotX(), data.rotY(), data.rotZ(), data.growthDuration(), data.easing());
            waveParticle.pickSprite(this.sprites);
            return waveParticle;
        }
    }
}

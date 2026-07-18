package com.goo.brutality.client.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OnomatopoeiaParticle extends TextureSheetParticle {
    private final float initialAlpha;
    private final float initialSize;

    protected OnomatopoeiaParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        setLifetime(60);
        this.quadSize *= 20;
        this.initialSize = this.quadSize;
        this.initialAlpha = this.alpha;
        pickSprite(spriteSet);
    }

    @Override
    protected int getLightColor(float partialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    @Override
    public void tick() {
        super.tick();

        float progress = (float) this.age / (float) this.lifetime; // 0 to 1
        float timeLeft = 1.0F - progress; // 1 to 0
        // anim windows
        float fadeInWindow = 0.05F;
        float fadeOutWindow = 0.33F;

        if (progress < fadeInWindow) {
            // fade in
            float fadeInProgress = progress / fadeInWindow;

            // no need alpha
            this.quadSize = this.initialSize * fadeInProgress;

        } else if (timeLeft < fadeOutWindow) {
            // fade out
            float fadeProgress = timeLeft / fadeOutWindow;

            this.alpha = this.initialAlpha * fadeProgress;
            this.quadSize = this.initialSize * fadeProgress;

        } else {
            this.alpha = this.initialAlpha;
            this.quadSize = this.initialSize;
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new OnomatopoeiaParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }
}

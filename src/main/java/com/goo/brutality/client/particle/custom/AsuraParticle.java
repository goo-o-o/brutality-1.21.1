package com.goo.brutality.client.particle.custom;

import com.goo.brutality.util.Colors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class AsuraParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    protected AsuraParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        setParticleSpeed(xSpeed, ySpeed, zSpeed);
        this.spriteSet = spriteSet;
        setSpriteFromAge(spriteSet);
        setLifetime(Mth.randomBetweenInclusive(level.getRandom(), 10, 20));
        setColor(Colors.TOLEDO);
        quadSize *= 5;
    }

    private void setColor(int color) {
        rCol = FastColor.ARGB32.red(color) / 255F;
        gCol = FastColor.ARGB32.green(color) / 255F;
        bCol = FastColor.ARGB32.blue(color) / 255F;
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteFromAge(spriteSet);

        float halfLife = lifetime / 2.0F;

        if (age < halfLife) {
            float progress = age / halfLife;
            setColor(FastColor.ARGB32.lerp(progress, Colors.TOLEDO, Colors.DEEP_CARMINE_PINK));
        } else {
            float progress = (age - halfLife) / halfLife;
            setColor(FastColor.ARGB32.lerp(progress, Colors.DEEP_CARMINE_PINK, Colors.VALENTINE));
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AsuraParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }

}

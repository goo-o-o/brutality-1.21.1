package com.goo.curiosities.client.particle.custom;

import com.goo.goo_lib.client.particle.TrailParticle;
import com.goo.goo_lib.client.particle.TrailParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class RainbowTrailParticle extends TrailParticle {


    public RainbowTrailParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, Integer entityId, float minVertexDistance, float width, boolean smooth, boolean bloomEnabled, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, entityId, minVertexDistance, width, smooth, bloomEnabled, spriteSet);
    }

    @Override
    public int getColorAtProgress(float progress, float partialTicks) {
        int rgb = Mth.hsvToRgb(progress, 0.8F, 1.5F);
        return FastColor.ARGB32.color(255, FastColor.ARGB32.red(rgb), FastColor.ARGB32.green(rgb), FastColor.ARGB32.blue(rgb));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<TrailParticleOption> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public @Nullable Particle createParticle(TrailParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RainbowTrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.entityId(), options.minVertexDistance(), options.width(), options.smoothInterpolation(), options.bloom(), this.spriteSet);
        }
    }
}

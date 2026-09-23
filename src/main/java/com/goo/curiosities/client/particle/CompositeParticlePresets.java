package com.goo.curiosities.client.particle;

import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.util.Easing;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class CompositeParticlePresets {
    public static final Consumer<CompositeParticle> AGEABLE_SPRITE = p -> {
        p.setSpriteFromAge(p.getSpriteSet());
    };

    public static Consumer<CompositeParticle> createQuadEaseShrinking(CompositeParticle p) {
        float baseSize = p.getQuadSize();
        return particle -> {
            float progress = Mth.clamp((float) particle.getAge() / (float) (particle.getLifetime() - 1), 0.0F, 1.0F);
            particle.setQuadSize(baseSize * Easing.EASE_IN_QUAD.ease(baseSize - progress));
        };
    }
    public static final Consumer<CompositeParticle> QUAD_EASE_FADE = p -> {
        float progress = Mth.clamp((float) p.getAge() / (float) (p.getLifetime() - 1), 0.0F, 1.0F);
        p.setAlpha(Easing.EASE_IN_QUAD.ease(1.0F - progress));
    };


    public static final Consumer<CompositeParticle> OMEGA_COLOR = p -> {
        float halfLife = p.getLifetime() / 2.0F;

        if (p.getAge() < halfLife) {
            float progress = p.getAge() / halfLife;
            p.setColor(FastColor.ARGB32.lerp(progress, Colors.FIRE[0], Colors.FIRE[1]));
        } else {
            float progress = (p.getAge() - halfLife) / halfLife;
            p.setColor(FastColor.ARGB32.lerp(progress, Colors.FIRE[1], Colors.FIRE[2]));
        }
    };

    public static CompositeParticle.Builder ROTATING_RANDOM_SPRITE_RANDOM_MOVEMENT = (p, level, xs, ys, zs) -> {
        p.pickSprite(p.getSpriteSet());
        p.setRandomRotation(0.5F, 0.5F);
        p.setGravity(0);
        p.withRenderType(ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT);
        p.setParticleSpeed(Mth.nextFloat(
                        level.getRandom(), -0.25F, 0.25F),
                Mth.nextFloat(level.getRandom(), 0.1F, Math.max(0.1F, 0.25F)),
                Mth.nextFloat(level.getRandom(), -0.25F, 0.25F)
        );
        p.withTickBehavior(QUAD_EASE_FADE);
        p.setLifetime(level.getRandom().nextIntBetweenInclusive(20, 40));
    };

    public static CompositeParticle.Builder ROTATING_AGEABLE_SPRITE_RANDOM_MOVEMENT = (p, level, xs, ys, zs) -> {
        p.setSpriteFromAge(p.getSpriteSet());
        p.setRandomRotation(0.5F, 0.5F);
        p.setGravity(0);
        p.withRenderType(ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT);
        p.setParticleSpeed(Mth.nextFloat(
                        level.getRandom(), -0.25F, 0.25F),
                Mth.nextFloat(level.getRandom(), 0.1F, Math.max(0.1F, 0.25F)),
                Mth.nextFloat(level.getRandom(), -0.25F, 0.25F)
        );
        p.withTickBehavior(QUAD_EASE_FADE);
        p.setLifetime(level.getRandom().nextIntBetweenInclusive(20, 40));
        p.withTickBehavior(AGEABLE_SPRITE);
    };


}


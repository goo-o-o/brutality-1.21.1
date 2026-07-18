package com.goo.brutality.client.particle;

import com.goo.brutality.util.Colors;
import com.goo.goo_lib.util.Easing;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class CompositeParticlePresets {
    public static final Consumer<CompositeParticle> AGEABLE_SPRITE = p -> {
        p.setSpriteFromAge(p.getSpriteSet());
    };

    public static final Consumer<CompositeParticle> QUAD_EASE_FADE = p -> {
        float progress = Mth.clamp((float) p.getAge() / (float) (p.getLifetime() - 1), 0.0F, 1.0F);
        p.setAlpha(Easing.EASE_IN_QUAD.ease(1.0F - progress));
    };



    public static final Consumer<CompositeParticle> ASURA_COLOR = p -> {
        float halfLife = p.getLifetime() / 2.0F;

        if (p.getAge() < halfLife) {
            float progress = p.getAge() / halfLife;
            p.setColor(FastColor.ARGB32.lerp(progress, Colors.TOLEDO, Colors.DEEP_CARMINE_PINK));
        } else {
            float progress = (p.getAge() - halfLife) / halfLife;
            p.setColor(FastColor.ARGB32.lerp(progress, Colors.DEEP_CARMINE_PINK, Colors.VALENTINE));
        }
    };
    public static final Consumer<CompositeParticle> OMEGA_COLOR = p -> {
        float halfLife = p.getLifetime() / 2.0F;

        if (p.getAge() < halfLife) {
            float progress = p.getAge() / halfLife;
            p.setColor(FastColor.ARGB32.lerp(progress, Colors.BRIGHT_YELLOW, Colors.ORANGE));
        } else {
            float progress = (p.getAge() - halfLife) / halfLife;
            p.setColor(FastColor.ARGB32.lerp(progress, Colors.ORANGE, Colors.GRENADIER));
        }
    };

    public static CompositeParticle.Builder POKER_CHIP = (p, level, xs, ys, zs) -> {
        p.setRandomRotation(0.5F, 0.5F);
        p.setGravity(0.8F);
        p.withTickBehavior(AGEABLE_SPRITE);
        p.setSpriteFromAge(p.getSpriteSet());
    };

    public static CompositeParticle.Builder POTION_EFFECT_RANDOM_SPRITE = (p, level, xs, ys, zs) -> {
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

    public static CompositeParticle.Builder POTION_EFFECT_AGEABLE_SPRITE = (p, level, xs, ys, zs) -> {
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


package com.goo.curiosities.common.registry;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.mob_effect.CaffeinatedEffect;
import com.goo.curiosities.common.mob_effect.FrozenEffect;
import com.goo.curiosities.common.mob_effect.GlitchedEffect;
import com.goo.curiosities.util.Colors;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class CuriositiesEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Curiosities.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> FROZEN = MOB_EFFECTS.register("frozen", () ->
            new FrozenEffect(MobEffectCategory.HARMFUL, new Color(113, 234, 255).getRGB())
    );
    public static final DeferredHolder<MobEffect, MobEffect> GLITCHED = MOB_EFFECTS.register("glitched", () ->
            new GlitchedEffect(MobEffectCategory.NEUTRAL, new Color(233, 39, 255).getRGB())
    );

    public static final DeferredHolder<MobEffect, MobEffect> CAFFEINATED = MOB_EFFECTS.register("caffeinated", () ->
            new CaffeinatedEffect(MobEffectCategory.NEUTRAL, new Color(157, 91, 16).getRGB()));

    public static final DeferredHolder<MobEffect, MobEffect> REDACTED = MOB_EFFECTS.register("redacted", () ->
            new MobEffect(MobEffectCategory.NEUTRAL, Colors.BLACK){});

    public static final DeferredHolder<MobEffect, MobEffect> INCOGNITO = MOB_EFFECTS.register("incognito", () ->
            new MobEffect(MobEffectCategory.NEUTRAL, Colors.BLACK){});

    //FIXME: Make it work in inventory entity renders
    public static final DeferredHolder<MobEffect, MobEffect> CENSORED = MOB_EFFECTS.register("censored", () ->
            new MobEffect(MobEffectCategory.NEUTRAL, Colors.BLACK){});

    public static final DeferredHolder<MobEffect, MobEffect> IMPERVIOUS = MOB_EFFECTS.register("impervious", () ->
            new MobEffect(MobEffectCategory.BENEFICIAL, FastColor.ARGB32.color(0, 0, 0)){}
    );

}

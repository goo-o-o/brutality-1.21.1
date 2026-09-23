package com.goo.curiosities.util;

import com.goo.goo_lib.client.text.effect.*;
import com.goo.goo_lib.client.text.effect.base.ConfiguredEffect;
import com.goo.goo_lib.common.registry.TextEffects;
import com.goo.goo_lib.util.StyleEffectUtil;
import net.minecraft.network.chat.Style;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * Reorganized design profiles for text effects.
 */
public class Styles {

    // ─── REUSABLE NON-GRADIENT EFFECT CONFIGURATIONS ─────────────────────────
    private static final Supplier<ConfiguredEffect<Float>> BLOOM_SHR = () -> new ConfiguredEffect<>(
            TextEffects.BLOOM_TYPE.get(), new BloomEffect(), 0.5F
    );
    private static final Supplier<ConfiguredEffect<AcidEffect.Config>> ACID_SHR = () -> new ConfiguredEffect<>(
            TextEffects.ACID_TYPE.get(), new AcidEffect(), AcidEffect.Config.builder().build()
    );
    private static final Supplier<ConfiguredEffect<ShakeEffect.Config>> SHAKE_SHR = () -> new ConfiguredEffect<>(
            TextEffects.SHAKE_TYPE.get(), new ShakeEffect(), ShakeEffect.Config.builder().speed(1.0F).intensity(0.5F).build()
    );
    private static final Supplier<ConfiguredEffect<JitterEffect.Config>> JITTER_SHR = () -> new ConfiguredEffect<>(
            TextEffects.JITTER_TYPE.get(), new JitterEffect(), JitterEffect.Config.builder().speed(1.5F).intensity(1.5F).build()
    );
    private static final Supplier<ConfiguredEffect<SmoothWaveEffect.Config>> SMOOTH_WAVE_SHR = () -> new ConfiguredEffect<>(
            TextEffects.SMOOTH_WAVE_TYPE.get(), new SmoothWaveEffect(), SmoothWaveEffect.Config.builder().speed(0.25F).amplitude(0.75F).frequency(0.05F).build()
    );
    private static final Supplier<ConfiguredEffect<SmoothWaveEffect.Config>> FAST_WAVE_SHR = () -> new ConfiguredEffect<>(
            TextEffects.SMOOTH_WAVE_TYPE.get(), new SmoothWaveEffect(), SmoothWaveEffect.Config.builder().speed(2F).amplitude(1.5F).frequency(0.25F).build()
    );
    private static final Supplier<ConfiguredEffect<SmoothWaveEffect.Config>> SLOW_WAVE_SHR = () -> new ConfiguredEffect<>(
            TextEffects.SMOOTH_WAVE_TYPE.get(), new SmoothWaveEffect(), SmoothWaveEffect.Config.builder().speed(1.4F).amplitude(1.5F).frequency(0.1F).build()
    );

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> RAINBOW_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(Colors.RAINBOW_LIST)
                    .spread(180).waveSpeed(0.77F).build()
    );
    public static final Style RAINBOW = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(RAINBOW_GRADIENT.get(), FAST_WAVE_SHR.get(), BLOOM_SHR.get()));


    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> STRENGTH_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(
                            new Color(196, 46, 46).getRGB(),
                            new Color(206, 151, 151, 255).getRGB()))
                    .spread(300).waveSpeed(0.4F).build()
    );
    public static final Style STRENGTH = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(STRENGTH_GRADIENT.get(), SHAKE_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> POISON_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(75, 180, 50).getRGB(), new Color(140, 240, 60).getRGB()))
                    .spread(200).waveSpeed(0.4F).build()
    );
    public static final Style POISON = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(POISON_GRADIENT.get(), ACID_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> WITHER_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(60, 55, 65).getRGB(), new Color(25, 20, 30).getRGB()))
                    .spread(120).waveSpeed(0.65F).build()
    );
    public static final Style WITHER = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(WITHER_GRADIENT.get(), JITTER_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> ABSORPTION_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(
                            new Color(255, 230, 59).getRGB(),
                            new Color(210, 174, 55).getRGB()))
                    .spread(120).waveSpeed(0.65F).build()
    );
    public static final Style ABSORPTION = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(ABSORPTION_GRADIENT.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> REGENERATION_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(
                            new Color(255, 0, 0).getRGB(),
                            new Color(211, 12, 88).getRGB()))
                    .spread(120).waveSpeed(0.65F).build()
    );
    public static final Style REGENERATION = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(REGENERATION_GRADIENT.get(), SMOOTH_WAVE_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> RESISTANCE_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(130, 110, 95).getRGB(), new Color(200, 185, 160).getRGB()))
                    .spread(150).waveSpeed(0.25F).build()
    );
    public static final Style RESISTANCE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(RESISTANCE_GRADIENT.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> SWIFTNESS_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(100, 210, 255).getRGB(), new Color(220, 245, 255).getRGB()))
                    .spread(100).waveSpeed(2.5F).build()
    );
    public static final Style SWIFTNESS = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(SWIFTNESS_GRADIENT.get(), FAST_WAVE_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> INVISIBILITY_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(255, 255, 255, 125).getRGB(), new Color(255, 255, 255, 200).getRGB()))
                    .spread(100).waveSpeed(2.5F).build()
    );
    public static final Style INVISIBILITY = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(INVISIBILITY_GRADIENT.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> SLOWNESS_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(137, 137, 147).getRGB(), new Color(87, 87, 105).getRGB(),
                            new Color(63, 63, 77).getRGB(), new Color(40, 40, 51).getRGB()))
                    .spread(75).waveSpeed(1.5F).build()
    );
    public static final Style SLOWNESS = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(SLOWNESS_GRADIENT.get(), SLOW_WAVE_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> FIRE_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(Colors.FIRE_LIST)
                    .spread(100).waveSpeed(1.0F).build()
    );
    public static final Style FIRE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(FIRE_GRADIENT.get(), SMOOTH_WAVE_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> WATER_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(15, 50, 160).getRGB(), new Color(50, 170, 255).getRGB()))
                    .spread(220).waveSpeed(0.5F).build()
    );
    public static final Style WATER = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(WATER_GRADIENT.get(), SMOOTH_WAVE_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> WIND_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(List.of(new Color(210, 225, 220).getRGB(), new Color(160, 185, 180).getRGB()))
                    .spread(250).waveSpeed(1.2F).build()
    );
    public static final Style WIND = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(WIND_GRADIENT.get(), FAST_WAVE_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> ICE_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(Colors.ICE_LIST)
                    .spread(180).waveSpeed(0.77F).build()
    );
    public static final Style ICE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(ICE_GRADIENT.get(), JITTER_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> MATRIX_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(Colors.MATRIX_LIST)
                    .spread(150).waveSpeed(0.5F).build()
    );
    public static final Style MATRIX = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(MATRIX_GRADIENT.get(), JITTER_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> GOLD_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(Colors.GOLD_INGOT_LIST)
                    .spread(150).waveSpeed(0.5F).build()
    );
    public static final Style GOLD = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(GOLD_GRADIENT.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> ENDER_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(Colors.ENDER_LIST)
                    .spread(150).waveSpeed(0.5F).build()
    );
    public static final Style ENDER = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(ENDER_GRADIENT.get(), JITTER_SHR.get()));

    private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> SOUL_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
            ColorGradientEffect.Config.builder()
                    .colors(Colors.SOUL_LIST)
                    .spread(150).waveSpeed(0.5F).build()
    );
    public static final Style SOUL = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(SOUL_GRADIENT.get(), ACID_SHR.get()));
}
package com.goo.brutality.util;

import com.goo.goo_lib.client.text.effect.BloomEffect;
import com.goo.goo_lib.client.text.effect.ColorWaveEffect;
import com.goo.goo_lib.client.text.effect.ShakeEffect;
import com.goo.goo_lib.client.text.effect.WaveEffect;
import com.goo.goo_lib.client.text.effect.base.ConfiguredEffect;
import com.goo.goo_lib.client.text.effect.config.BloomConfig;
import com.goo.goo_lib.client.text.effect.config.GradientConfig;
import com.goo.goo_lib.client.text.effect.config.ShakeConfig;
import com.goo.goo_lib.client.text.effect.config.WaveConfig;
import com.goo.goo_lib.common.registry.TextEffects;
import com.goo.goo_lib.util.StyleEffectUtil;
import net.minecraft.network.chat.Style;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * Commonly used styles
 */
public class Styles {
    private static final Supplier<ConfiguredEffect<BloomConfig>> BLOOM_EFFECT = () -> new ConfiguredEffect<>(
            TextEffects.BLOOM_TYPE.get(),
            new BloomEffect(),
            new BloomConfig(0.5F));


    private static final Supplier<ConfiguredEffect<GradientConfig>> LEGENDARY_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(255, 240, 120).getRGB(),
                            new Color(255, 205, 20).getRGB(),
                            new Color(255, 170, 40).getRGB()
                    ),
                    200,
                    0.35F
            ));
    public static final Style LEGENDARY_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(LEGENDARY_GRADIENT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> FABLED_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(255, 255, 255).getRGB(),
                            new Color(128, 200, 230).getRGB(),
                            new Color(200, 150, 255).getRGB(),
                            new Color(210, 105, 225).getRGB()
                    ),
                    180,
                    0.4F
            ));
    public static final Style FABLED_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(FABLED_GRADIENT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> MYTHIC_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(20, 205, 255).getRGB(),
                            new Color(20, 255, 165).getRGB()
                    ),
                    180,
                    0.4F
            ));
    public static final Style MYTHIC_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(MYTHIC_GRADIENT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> DIVINE_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(255, 255, 175).getRGB(),
                            new Color(175, 235, 240).getRGB()
                    ),
                    180,
                    0.77F
            ));
    public static final Style DIVINE_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(DIVINE_GRADIENT.get(), BLOOM_EFFECT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> CATACLYSMIC_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(164, 252, 255).getRGB(),
                            new Color(77, 140, 220).getRGB(),
                            new Color(203, 130, 225).getRGB(),
                            new Color(255, 30, 50).getRGB(),
                            new Color(203, 130, 225).getRGB(),
                            new Color(77, 140, 220).getRGB()
                    ),
                    180,
                    0.77F
            ));
    private static final Supplier<ConfiguredEffect<ShakeConfig>> CATACLYSMIC_SHAKE = () -> new ConfiguredEffect<>(
            TextEffects.SHAKE_TYPE.get(),
            new ShakeEffect(),
            new ShakeConfig(
                    5,
                    0.77F
            ));
    public static final Style CATACLYSMIC_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(CATACLYSMIC_GRADIENT.get(), CATACLYSMIC_SHAKE.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> GODLY_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(255, 90, 90).getRGB(),
                            new Color(255, 180, 90).getRGB(),
                            new Color(255, 255, 90).getRGB(),
                            new Color(120, 255, 120).getRGB(),
                            new Color(120, 255, 255).getRGB(),
                            new Color(120, 120, 255).getRGB(),
                            new Color(150, 100, 255).getRGB(),
                            new Color(255, 100, 255).getRGB()
                    ),
                    180,
                    0.77F
            ));
    private static final Supplier<ConfiguredEffect<WaveConfig>> GODLY_WAVE = () -> new ConfiguredEffect<>(
            TextEffects.WAVE_TYPE.get(),
            new WaveEffect(),
            new WaveConfig(0.5F, 2.0F, 0.5F));
    public static final Style GODLY_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(GODLY_GRADIENT.get(), GODLY_WAVE.get(), BLOOM_EFFECT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> GLACIAL_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(150, 220, 245).getRGB(),
                            new Color(255, 255, 255).getRGB()
                    ),
                    180,
                    0.77F
            ));
    private static final Supplier<ConfiguredEffect<ShakeConfig>> GLACIAL_SHAKE = () -> new ConfiguredEffect<>(
            TextEffects.SHAKE_TYPE.get(),
            new ShakeEffect(),
            new ShakeConfig(0.5F, 1.0F));
    public static final Style GLACIAL_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(GLACIAL_GRADIENT.get(), GLACIAL_SHAKE.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> ENCRYPTED_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            Colors.SPRING_BUD, Colors.PHTHALO_GREEN, Colors.ERIN, Colors.DARK_MOSS_GREEN
                    ),
                    150,
                    2.0F
            ));
    public static final Style ENCRYPTED_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(ENCRYPTED_GRADIENT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> CORALINE_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(246, 186, 93).getRGB(),
                            new Color(193, 100, 54).getRGB(),
                            new Color(223, 75, 129).getRGB(),
                            new Color(130, 68, 124).getRGB()
                    ),
                    300,
                    0.5F
            ));
    public static final Style CORALINE_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(CORALINE_GRADIENT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    // TODO: Temporary until 1.0
    private static final Supplier<ConfiguredEffect<GradientConfig>> SMOLDERING_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            Colors.BRIGHT_YELLOW, Colors.ORANGE, Colors.GRENADIER
                    ),
                    100,
                    1F
            ));
    private static final Supplier<ConfiguredEffect<WaveConfig>> SMOLDERING_WAVE = () -> new ConfiguredEffect<>(
            TextEffects.WAVE_TYPE.get(),
            new WaveEffect(),
            new WaveConfig(
                    0.25F,
                    0.25F,
                    0.15F
            ));
    public static final Style SMOLDERING_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(SMOLDERING_GRADIENT.get(), SMOLDERING_WAVE.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> CONDUCTIVE_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(62, 50, 43).getRGB(),
                            new Color(93, 77, 65).getRGB(),
                            new Color(255, 223, 81).getRGB(),
                            new Color(93, 77, 65).getRGB(),
                            new Color(62, 50, 43).getRGB()
                    ),
                    50,
                    1F
            ));
    private static final Supplier<ConfiguredEffect<ShakeConfig>> CONDUCTIVE_SHAKE = () -> new ConfiguredEffect<>(
            TextEffects.SHAKE_TYPE.get(),
            new ShakeEffect(),
            new ShakeConfig(
                    1.5F,
                    0.5F
            ));
    public static final Style CONDUCTIVE_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(CONDUCTIVE_GRADIENT.get(), CONDUCTIVE_SHAKE.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> STYGIAN_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(255, 0, 0).getRGB(),
                            new Color(160, 0, 0).getRGB()
                    ),
                    120,
                    0.65F
            ));
    public static final Style STYGIAN_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(STYGIAN_GRADIENT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> VOIDTOUCHED_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(250, 252, 255).getRGB(),
                            new Color(191, 202, 223).getRGB(),
                            new Color(127, 119, 145).getRGB(),
                            new Color(71, 80, 107).getRGB(),
                            new Color(50, 51, 61).getRGB(),
                            new Color(27, 28, 56).getRGB()
                    ),
                    120,
                    0.65F
            ));
    public static final Style VOIDTOUCHED_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(VOIDTOUCHED_GRADIENT.get()));
    // ─────────────────────────────────────────────────────────────────────────────
    private static final Supplier<ConfiguredEffect<GradientConfig>> COSMIC_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(200, 136, 231).getRGB(),
                            new Color(173, 96, 255).getRGB(),
                            new Color(116, 52, 213).getRGB(),
                            new Color(81, 0, 188).getRGB()
                    ),
                    300,
                    0.65F
            ));
    private static final Supplier<ConfiguredEffect<WaveConfig>> COSMIC_WAVE = () -> new ConfiguredEffect<>(
            TextEffects.WAVE_TYPE.get(),
            new WaveEffect(),
            new WaveConfig(
                    0.25F,
                    1,
                    0.15F
            ));
    public static final Style COSMIC_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(COSMIC_GRADIENT.get(), SMOLDERING_WAVE.get()));

    // Basic Color Styles ─────────────────────────────────────────────────────────────────────────────

    private static final Supplier<ConfiguredEffect<GradientConfig>> VITALITY_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(139, 0, 0).getRGB(),   // Dark Red
                            new Color(255, 75, 75).getRGB(), // Light Red
                            new Color(139, 0, 0).getRGB()
                    ),
                    150,
                    0.5F
            ));
    public static final Style VITALITY_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(VITALITY_GRADIENT.get()));

    private static final Supplier<ConfiguredEffect<GradientConfig>> TOXIC_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(34, 139, 34).getRGB(),  // Forest Green
                            new Color(124, 252, 0).getRGB()   // Lime Green
                    ),
                    200,
                    0.4F
            ));
    private static final Supplier<ConfiguredEffect<WaveConfig>> TOXIC_WAVE = () -> new ConfiguredEffect<>(
            TextEffects.WAVE_TYPE.get(),
            new WaveEffect(),
            new WaveConfig(0.3F, 1.5F, 0.2F));
    public static final Style TOXIC_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(TOXIC_GRADIENT.get(), TOXIC_WAVE.get()));


    private static final Supplier<ConfiguredEffect<GradientConfig>> GRAY_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(75, 75, 75).getRGB(),    // Dark Gray
                            new Color(165, 165, 165).getRGB(), // Light Stone Gray
                            new Color(75, 75, 75).getRGB()
                    ),
                    250,
                    0.25F
            ));
    public static final Style GRAY_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(GRAY_GRADIENT.get()));

    private static final Supplier<ConfiguredEffect<GradientConfig>> OCEANIC_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(0, 128, 128).getRGB(),  // Dark Teal
                            new Color(64, 224, 208).getRGB()  // Turquoise
                    ),
                    180,
                    0.6F
            ));
    public static final Style OCEANIC_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(OCEANIC_GRADIENT.get()));

    private static final Supplier<ConfiguredEffect<GradientConfig>> WATER_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(10, 30, 120).getRGB(),  // Deep Blue
                            new Color(30, 144, 255).getRGB()  // Dodger Blue
                    ),
                    220,
                    0.5F
            ));
    private static final Supplier<ConfiguredEffect<WaveConfig>> WATER_WAVE = () -> new ConfiguredEffect<>(
            TextEffects.WAVE_TYPE.get(),
            new WaveEffect(),
            new WaveConfig(0.1F, 1F, 0.1F));
    public static final Style WATER_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(WATER_GRADIENT.get(), WATER_WAVE.get()));
    public static final Supplier<ConfiguredEffect<GradientConfig>> RAGE_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            Colors.GRENADIER,
                            Colors.RUSSIAN_RED
                    ),
                    220,
                    0.5F
            ));
    private static final Supplier<ConfiguredEffect<ShakeConfig>> RAGE_SHAKE = () -> new ConfiguredEffect<>(
            TextEffects.SHAKE_TYPE.get(),
            new ShakeEffect(),
            new ShakeConfig(1F, 2F));
    public static final Style RAGE_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(RAGE_GRADIENT.get(), RAGE_SHAKE.get()));

    public static final Supplier<ConfiguredEffect<GradientConfig>> ENDER_GRADIENT = () -> new ConfiguredEffect<>(
            TextEffects.COLOR_GRADIENT_TYPE.get(),
            new ColorWaveEffect(),
            new GradientConfig(
                    List.of(
                            new Color(224, 121, 250).getRGB(),
                            new Color(204, 0, 250).getRGB()
                    ),
                    180,
                    0.3F
            ));

    public static final Style ENDER_STYLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(ENDER_GRADIENT.get()));

}

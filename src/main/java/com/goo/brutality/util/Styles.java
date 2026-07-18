package com.goo.brutality.util;

import com.goo.goo_lib.client.text.effect.*;
import com.goo.goo_lib.client.text.effect.base.ConfiguredEffect;
import com.goo.goo_lib.common.registry.TextEffects;
import com.goo.goo_lib.util.StyleEffectUtil;
import net.minecraft.network.chat.Style;

import java.awt.*;
import java.util.Arrays;
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
            TextEffects.SMOOTH_WAVE_TYPE.get(), new SmoothWaveEffect(), SmoothWaveEffect.Config.builder().speed(1.4F).amplitude(1.5F).frequency(0.1F).build()
    );

    // ─── 1. RARITY STYLES ────────────────────────────────────────────────────
    public static class Rarity {
        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> LEGENDARY_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(255, 240, 120).getRGB(), new Color(255, 205, 20).getRGB(), new Color(255, 170, 40).getRGB()))
                        .spread(200).waveSpeed(0.35F).build()
        );
        public static final Style LEGENDARY = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(LEGENDARY_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> FABLED_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(255, 255, 255).getRGB(), new Color(128, 200, 230).getRGB(), new Color(200, 150, 255).getRGB(), new Color(210, 105, 225).getRGB()))
                        .spread(180).waveSpeed(0.4F).build()
        );
        public static final Style FABLED = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(FABLED_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> MYTHIC_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(20, 205, 255).getRGB(), new Color(20, 255, 165).getRGB()))
                        .spread(180).waveSpeed(0.4F).build()
        );
        public static final Style MYTHIC = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(MYTHIC_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> DIVINE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(255, 255, 175).getRGB(), new Color(175, 235, 240).getRGB()))
                        .spread(180).waveSpeed(0.77F).build()
        );
        public static final Style DIVINE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(DIVINE_GRADIENT.get(), BLOOM_SHR.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> CATACLYSMIC_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(164, 252, 255).getRGB(), new Color(77, 140, 220).getRGB(), new Color(203, 130, 225).getRGB(), new Color(255, 30, 50).getRGB(), new Color(203, 130, 225).getRGB(), new Color(77, 140, 220).getRGB()))
                        .spread(180).waveSpeed(0.77F).build()
        );
        public static final Style CATACLYSMIC = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(CATACLYSMIC_GRADIENT.get(), SHAKE_SHR.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> GODLY_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(Arrays.asList(Colors.GODLY))
                        .spread(180).waveSpeed(0.77F).build()
        );
        public static final Style GODLY = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(GODLY_GRADIENT.get(), FAST_WAVE_SHR.get(), BLOOM_SHR.get()));
    }

    // ─── 2. GAMEPLAY STATUS EFFECTS ──────────────────────────────────────────
    public static class PotionEffects {
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
                        .spread(100).waveSpeed(1.5F).build()
        );
        public static final Style SWIFTNESS = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(SWIFTNESS_GRADIENT.get(), FAST_WAVE_SHR.get()));
    }

    // ─── 3. NATURAL ELEMENT STYLES ───────────────────────────────────────────
    public static class Elements {
        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> FIRE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(255, 140, 0).getRGB(), new Color(255, 60, 0).getRGB()))
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

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> EARTH_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(110, 80, 50).getRGB(), new Color(75, 50, 25).getRGB()))
                        .spread(140).waveSpeed(0.1F).build()
        );
        public static final Style EARTH = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(EARTH_GRADIENT.get()));
    }

    // ─── 4. CHROMATIC BASE COLORS ────────────────────────────────────────────
    public static class BasicColors {
        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> WHITE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(240, 240, 240).getRGB(), new Color(255, 255, 255).getRGB()))
                        .spread(200).waveSpeed(0.2F).build()
        );
        public static final Style WHITE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(WHITE_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> LIGHT_GRAY_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(140, 140, 140).getRGB(), new Color(200, 200, 200).getRGB()))
                        .spread(200).waveSpeed(0.25F).build()
        );
        public static final Style LIGHT_GRAY = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(LIGHT_GRAY_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> GRAY_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(70, 70, 70).getRGB(), new Color(130, 130, 130).getRGB()))
                        .spread(250).waveSpeed(0.25F).build()
        );
        public static final Style GRAY = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(GRAY_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> BLACK_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(20, 20, 20).getRGB(), new Color(60, 60, 60).getRGB()))
                        .spread(150).waveSpeed(0.15F).build()
        );
        public static final Style BLACK = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(BLACK_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> RED_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(180, 20, 20).getRGB(), new Color(255, 80, 80).getRGB()))
                        .spread(150).waveSpeed(0.5F).build()
        );
        public static final Style RED = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(RED_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> ORANGE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(220, 100, 10).getRGB(), new Color(255, 170, 40).getRGB()))
                        .spread(160).waveSpeed(0.45F).build()
        );
        public static final Style ORANGE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(ORANGE_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> YELLOW_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(220, 190, 10).getRGB(), new Color(255, 245, 100).getRGB()))
                        .spread(180).waveSpeed(0.4F).build()
        );
        public static final Style YELLOW = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(YELLOW_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> LIME_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(70, 190, 20).getRGB(), new Color(150, 250, 80).getRGB()))
                        .spread(140).waveSpeed(0.55F).build()
        );
        public static final Style LIME = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(LIME_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> GREEN_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(30, 110, 40).getRGB(), new Color(90, 190, 100).getRGB()))
                        .spread(150).waveSpeed(0.4F).build()
        );
        public static final Style GREEN = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(GREEN_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> CYAN_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(15, 140, 160).getRGB(), new Color(80, 230, 245).getRGB()))
                        .spread(170).waveSpeed(0.5F).build()
        );
        public static final Style CYAN = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(CYAN_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> LIGHT_BLUE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(60, 150, 240).getRGB(), new Color(160, 215, 255).getRGB()))
                        .spread(180).waveSpeed(0.45F).build()
        );
        public static final Style LIGHT_BLUE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(LIGHT_BLUE_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> BLUE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(25, 65, 190).getRGB(), new Color(100, 150, 255).getRGB()))
                        .spread(160).waveSpeed(0.4F).build()
        );
        public static final Style BLUE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(BLUE_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> PURPLE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(110, 25, 190).getRGB(), new Color(190, 100, 255).getRGB()))
                        .spread(150).waveSpeed(0.45F).build()
        );
        public static final Style PURPLE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(PURPLE_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> MAGENTA_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(170, 30, 150).getRGB(), new Color(245, 110, 230).getRGB()))
                        .spread(150).waveSpeed(0.5F).build()
        );
        public static final Style MAGENTA = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(MAGENTA_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> PINK_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(220, 100, 140).getRGB(), new Color(255, 180, 205).getRGB()))
                        .spread(140).waveSpeed(0.35F).build()
        );
        public static final Style PINK = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(PINK_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> BROWN_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(95, 60, 30).getRGB(), new Color(150, 110, 70).getRGB()))
                        .spread(130).waveSpeed(0.2F).build()
        );
        public static final Style BROWN = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(BROWN_GRADIENT.get()));

    }

    // ─── 5. COSMIC & SPECIAL STYLES ──────────────────────────────────────────
    public static class Special {
        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> GLACIAL_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(150, 220, 245).getRGB(), new Color(255, 255, 255).getRGB()))
                        .spread(180).waveSpeed(0.77F).build()
        );
        public static final Style GLACIAL = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(GLACIAL_GRADIENT.get(), JITTER_SHR.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> ENCRYPTED_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(Colors.SPRING_BUD, Colors.PHTHALO_GREEN, Colors.ERIN, Colors.DARK_MOSS_GREEN))
                        .spread(150).waveSpeed(0.5F).build()
        );
        public static final Style ENCRYPTED = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(ENCRYPTED_GRADIENT.get(), JITTER_SHR.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> CORALINE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(246, 186, 93).getRGB(), new Color(193, 100, 54).getRGB(), new Color(223, 75, 129).getRGB(), new Color(130, 68, 124).getRGB()))
                        .spread(300).waveSpeed(0.5F).build()
        );
        public static final Style CORALINE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(CORALINE_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> SMOLDERING_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(Colors.BRIGHT_YELLOW, Colors.ORANGE, Colors.GRENADIER))
                        .spread(100).waveSpeed(1.0F).build()
        );
        public static final Style SMOLDERING = StyleEffectUtil.createStyleWithEffects(Style.EMPTY, List.of(SMOLDERING_GRADIENT.get(), SMOOTH_WAVE_SHR.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> CONDUCTIVE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(62, 50, 43).getRGB(), new Color(93, 77, 65).getRGB(), new Color(255, 223, 81).getRGB(), new Color(93, 77, 65).getRGB(), new Color(62, 50, 43).getRGB()))
                        .spread(50).waveSpeed(1.0F).build()
        );
        public static final Style CONDUCTIVE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(CONDUCTIVE_GRADIENT.get(), SHAKE_SHR.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> STYGIAN_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(255, 0, 0).getRGB(), new Color(160, 0, 0).getRGB()))
                        .spread(120).waveSpeed(0.65F).build()
        );
        public static final Style STYGIAN = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(STYGIAN_GRADIENT.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> VOIDTOUCHED_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(250, 252, 255).getRGB(), new Color(191, 202, 223).getRGB(), new Color(127, 119, 145).getRGB(), new Color(71, 80, 107).getRGB(), new Color(50, 51, 61).getRGB(), new Color(27, 28, 56).getRGB()))
                        .spread(120).waveSpeed(0.65F).build()
        );
        private static final Supplier<ConfiguredEffect<FadeEffect.Config>> VOIDTOUCHED_FADE = () -> new ConfiguredEffect<>(
                TextEffects.FADE_TYPE.get(), new FadeEffect(),
                FadeEffect.Config.builder().minAlpha(0.25F).frequency(0.8F).phase(0.0F).build()
        );
        public static final Style VOIDTOUCHED = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(VOIDTOUCHED_GRADIENT.get(), VOIDTOUCHED_FADE.get()));

        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> COSMIC_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(200, 136, 231).getRGB(), new Color(173, 96, 255).getRGB(), new Color(116, 52, 213).getRGB(), new Color(81, 0, 188).getRGB()))
                        .spread(300).waveSpeed(0.65F).build()
        );
        public static final Style COSMIC = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(COSMIC_GRADIENT.get(), SMOOTH_WAVE_SHR.get()));
        private static final Supplier<ConfiguredEffect<ColorGradientEffect.Config>> RAGE_GRADIENT = () -> new ConfiguredEffect<>(
                TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                ColorGradientEffect.Config.builder()
                        .colors(List.of(new Color(230, 40, 40).getRGB(), new Color(140, 10, 10).getRGB()))
                        .spread(220).waveSpeed(0.5F).build()
        );
        public static final Style RAGE = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(RAGE_GRADIENT.get(), JITTER_SHR.get()));
    }
}
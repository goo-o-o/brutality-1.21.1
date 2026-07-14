package com.goo.brutality.client;

import com.goo.brutality.client.gui.RageMeterOverlay;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BrutalityClientConfig {
    public static final ModConfigSpec SPEC;
    public static final BrutalityClientConfig CONFIG;

    static {
        Pair<BrutalityClientConfig, ModConfigSpec> clientPair = new ModConfigSpec.Builder().configure(BrutalityClientConfig::new);
        CONFIG = clientPair.getLeft();
        SPEC = clientPair.getRight();
    }

    public final ModConfigSpec.BooleanValue RENDER_CUSTOM_TOOLTIPS;

    public final ModConfigSpec.DoubleValue ENCRYPTED_TEXT_SCALE;
    public final ModConfigSpec.IntValue ENCRYPTED_MAX_DROPLETS;
    public final ModConfigSpec.LongValue ENCRYPTED_UPDATE_INTERVAL;
    public final ModConfigSpec.IntValue ENCRYPTED_OBJECT_PADDING;

    public final ModConfigSpec.IntValue CORALINE_BUBBLE_SPAWN_INTERVAL;
    public final ModConfigSpec.DoubleValue CORALINE_MARINE_OBJECT_AMOUNT_MULTIPLIER;
    public final ModConfigSpec.DoubleValue CORALINE_SEAGRASS_CHANCE;
    public final ModConfigSpec.DoubleValue CORALINE_TALL_CHANCE;
    public final ModConfigSpec.DoubleValue CORALINE_BUBBLE_SIZE_MULTIPLIER;
    public final ModConfigSpec.DoubleValue CORALINE_BUBBLE_SPEED_MULTIPLIER;

    public final ModConfigSpec.IntValue SMOLDERING_EMBER_AMOUNT;
    public final ModConfigSpec.DoubleValue SMOLDERING_SMOKE_INTENSITY;

    public final ModConfigSpec.DoubleValue CONDUCTIVE_CORE_BRIGHTNESS_MULTIPLIER;
    public final ModConfigSpec.DoubleValue CONDUCTIVE_STRIKE_FADEOUT;
    public final ModConfigSpec.DoubleValue CONDUCTIVE_STRIKE_CHANCE;
    public final ModConfigSpec.DoubleValue CONDUCTIVE_STRIKE_SPEED;
    public final ModConfigSpec.DoubleValue CONDUCTIVE_TENDRIL_VOLATILITY;

    public final ModConfigSpec.IntValue STYGIAN_ITERATIONS;
    public final ModConfigSpec.IntValue STYGIAN_SOUL_SPAWN_INTERVAL;
    public final ModConfigSpec.DoubleValue STYGIAN_BRIGHTNESS;
    public final ModConfigSpec.DoubleValue STYGIAN_SOUL_SIZE_MULTIPLIER;

    public final ModConfigSpec.DoubleValue VOIDTOUCHED_MOTE_SPAWN_BOUNDS_MULTIPLIER;
    public final ModConfigSpec.DoubleValue VOIDTOUCHED_MOTE_ACCELERATION;
    public final ModConfigSpec.DoubleValue VOIDTOUCHED_SHADOW_RADIUS;
    public final ModConfigSpec.IntValue VOIDTOUCHED_MOTE_SPAWN_INTERVAL;
    public final ModConfigSpec.IntValue VOIDTOUCHED_MOTE_LIFETIME;

    public final ModConfigSpec.DoubleValue COSMIC_STAR_CHANCE;
    public final ModConfigSpec.DoubleValue COSMIC_STAR_SIZE_MULTIPLIER;
    public final ModConfigSpec.DoubleValue COSMIC_STAR_ROTATION_SPEED;
    public final ModConfigSpec.DoubleValue COSMIC_STAR_BLOOM_SIZE;

    public final ModConfigSpec.EnumValue<RageMeterOverlay.Position> RAGE_METER_POSITION;
    public final ModConfigSpec.EnumValue<RageMeterOverlay.Style> RAGE_METER_STYLE;
    public final ModConfigSpec.DoubleValue ENRAGED_SCREEN_SHAKE_INTENSITY;

    private BrutalityClientConfig(ModConfigSpec.Builder builder) {
        builder.push("rage");

        RAGE_METER_POSITION = builder
                .comment("Position of the Rage Meter")
                .defineEnum("rage_meter_position", RageMeterOverlay.Position.HOTBAR_RIGHT);
        RAGE_METER_STYLE = builder
                .comment("Style of the Rage Meter")
                .defineEnum("rage_meter_style", RageMeterOverlay.Style.CLASSIC);
        ENRAGED_SCREEN_SHAKE_INTENSITY = builder
                .comment("Intensity of Screen Shake when Enraged")
                .defineInRange("enraged_screen_shake_intensity", 1, 0D, 100D);

        builder.pop();


        builder.push("tooltips"); // ─────────────────────────────────────────────────────────────────────────────


        RENDER_CUSTOM_TOOLTIPS = builder
                .comment("Should custom tooltips be rendered?")
                .define("render_custom_tooltips", true);

        builder.push("encrypted"); // ─────────────────────────────────────────────────────────────────────────────

        ENCRYPTED_TEXT_SCALE = builder
                .comment("Matrix waterfall droplet text size")
                .defineInRange("encrypted_text_scale", 0.5D, 0.0, 10.0D); // defined as double since spec handles decimals this way

        ENCRYPTED_MAX_DROPLETS = builder
                .comment("Maximum amount of concurrent matrix waterfall droplets")
                .defineInRange("encrypted_max_droplets", 8, 1, 64);

        ENCRYPTED_UPDATE_INTERVAL = builder
                .comment("Animation interval in ms (affects glitch boxes and matrix droplets)")
                .defineInRange("encrypted_update_interval", 75L, 1L, 5000L);

        ENCRYPTED_OBJECT_PADDING = builder
                .comment("How far glitch boxes and droplets can spawn outside the tooltip")
                .defineInRange("encrypted_object_padding", 10, 0, 100);

        builder.pop();

        builder.push("coraline"); // ─────────────────────────────────────────────────────────────────────────────

        CORALINE_MARINE_OBJECT_AMOUNT_MULTIPLIER = builder
                .comment("Multiplier on how many total marine objects are generated in total")
                .defineInRange("coraline_marine_object_amount_multiplier", 1.0D, 0.0, 10.0D);

        CORALINE_SEAGRASS_CHANCE = builder
                .comment("Chance for a marine object to be seagrass, if not it will be coral")
                .defineInRange("coraline_seagrass_chance", 0.65D, 0.0, 1D);

        CORALINE_TALL_CHANCE = builder
                .comment("Chance for a marine object to be tall")
                .defineInRange("coraline_marine_object_tall_chance", 0.33D, 0.0, 1D);

        CORALINE_BUBBLE_SPAWN_INTERVAL = builder
                .comment("Bubble spawn interval in ms (affects how many bubbles are spawned)")
                .defineInRange("coraline_bubble_spawn_interval", 200, 1, Integer.MAX_VALUE);

        CORALINE_BUBBLE_SIZE_MULTIPLIER = builder
                .comment("Bubble particle size")
                .defineInRange("coraline_bubble_size_multiplier", 1, 0, 10D);

        CORALINE_BUBBLE_SPEED_MULTIPLIER = builder
                .comment("Bubble particle speed")
                .defineInRange("coraline_bubble_speed_multiplier", 1, 0, 10D);

        builder.pop();

        builder.push("smoldering"); // ─────────────────────────────────────────────────────────────────────────────

        SMOLDERING_EMBER_AMOUNT = builder
                .comment("How many embers to show concurrently")
                .defineInRange("smoldering_ember_amount", 1, 0, 20);

        SMOLDERING_SMOKE_INTENSITY = builder
                .comment("Intensity of the smoke")
                .defineInRange("smoldering_smoke_intensity", 2.0, 0.0, 10);

        builder.pop();

        builder.push("conductive"); // ─────────────────────────────────────────────────────────────────────────────

        CONDUCTIVE_CORE_BRIGHTNESS_MULTIPLIER = builder
                .comment("Inner lightning brightness multiplier")
                .defineInRange("conductive_core_brightness_multiplier", 1.15, 0D, 10D);

        CONDUCTIVE_STRIKE_FADEOUT = builder
                .comment("How long it takes for lightning to fade out")
                .defineInRange("conductive_strike_fadeout", 0.6, 0D, 1D);

        CONDUCTIVE_STRIKE_CHANCE = builder
                .comment("Chance for lightning to strike per cycle") // early return in code
                .defineInRange("conductive_strike_chance", 0.7, 0D, 1D);

        CONDUCTIVE_STRIKE_SPEED = builder
                .comment("Makes lightning strike more often and the strikes themselves shorter")
                .defineInRange("conductive_strike_speed", 0.5, 0, 10.0);

        CONDUCTIVE_TENDRIL_VOLATILITY = builder
                .comment("How fast and erratic the tendrils are")
                .defineInRange("conductive_tendril_volatility", 0.5, 0, 10.0);

        builder.pop();

        builder.push("stygian"); // ─────────────────────────────────────────────────────────────────────────────

        STYGIAN_ITERATIONS = builder
                .comment("How detailed the Shader will be, lower values can improve performance")
                .defineInRange("stygian_iterations", 32, 0, 128);

        STYGIAN_BRIGHTNESS = builder
                .comment("How bright is the Shader")
                .defineInRange("stygian_brightness", 5, 0D, 10);

        STYGIAN_SOUL_SPAWN_INTERVAL = builder
                .comment("Stygian soul particle spawn interval in ms")
                .defineInRange("stygian_soul_spawn_interval", 50, 1, Integer.MAX_VALUE);

        STYGIAN_SOUL_SIZE_MULTIPLIER = builder
                .comment("Stygian soul particle size multiplier")
                .defineInRange("stygian_soul_size_multiplier", 1, 0D, 10);

        builder.pop();

        builder.push("voidtouched"); // ─────────────────────────────────────────────────────────────────────────────

        VOIDTOUCHED_MOTE_SPAWN_BOUNDS_MULTIPLIER = builder
                .comment("Multiplier on how far away mote particles can spawn")
                .defineInRange("voidtouched_mote_spawn_bounds_multiplier", 1, 1D, 10);

        VOIDTOUCHED_MOTE_ACCELERATION = builder
                .comment("How fast mote particles move towards the center")
                .defineInRange("voidtouched_mote_acceleration", 1000, 1, 50000D);

        VOIDTOUCHED_MOTE_SPAWN_INTERVAL = builder
                .comment("Mote Particle spawn interval in ms")
                .defineInRange("voidtouched_mote_spawn_interval", 50, 1, Integer.MAX_VALUE);

        VOIDTOUCHED_MOTE_LIFETIME = builder
                .comment("Mote Particle lifetime in ticks")
                .defineInRange("voidtouched_mote_lifetime", 100, 1, Integer.MAX_VALUE);

        VOIDTOUCHED_SHADOW_RADIUS = builder
                .comment("Radius of the box shadow in px")
                .defineInRange("voidtouched_shadow_radius", 60, 0, 1000D);

        builder.pop();

        builder.push("cosmic"); // ─────────────────────────────────────────────────────────────────────────────

        COSMIC_STAR_CHANCE = builder
                .comment("Spawn chance factor for stars")
                .defineInRange("cosmic_star_chance", 0.25D, 0.0D, 1.0D);

        COSMIC_STAR_SIZE_MULTIPLIER = builder
                .comment("Size scaling factor for stars")
                .defineInRange("cosmic_star_size_multiplier", 0.25D, 0.0D, 5.0D);

        COSMIC_STAR_ROTATION_SPEED = builder
                .comment("Rotation speed modifier")
                .defineInRange("cosmic_star_rotation_speed", 1.0D, -10.0D, 10.0D);

        COSMIC_STAR_BLOOM_SIZE = builder
                .comment("Size scaling factor for the bloom glow effect")
                .defineInRange("cosmic_star_bloom_size", 0.35D, 0.0D, 5.0D);

        builder.pop();

        builder.pop();
        // ─────────────────────────────────────────────────────────────────────────────
    }


}

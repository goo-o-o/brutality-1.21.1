package com.goo.curiosities.client;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CuriositiesClientConfig {
    public static final ModConfigSpec SPEC;
    public static final CuriositiesClientConfig CONFIG;
    public final ModConfigSpec.BooleanValue RENDER_CUSTOM_TOOLTIPS;


    public final ModConfigSpec.DoubleValue MATRIX_TEXT_SCALE;
    public final ModConfigSpec.IntValue MATRIX_MAX_DROPLETS;
    public final ModConfigSpec.LongValue MATRIX_UPDATE_INTERVAL;
    public final ModConfigSpec.IntValue MATRIX_OBJECT_PADDING;

    public final ModConfigSpec.IntValue FIRE_EMBER_AMOUNT;
    public final ModConfigSpec.DoubleValue FIRE_SMOKE_INTENSITY;

    public final ModConfigSpec.DoubleValue SNOW_SPEED;

    public final ModConfigSpec.IntValue WATER_BUBBLE_SPAWN_INTERVAL;
    public final ModConfigSpec.DoubleValue WATER_MARINE_OBJECT_AMOUNT_MULTIPLIER;
    public final ModConfigSpec.DoubleValue WATER_SEAGRASS_CHANCE;
    public final ModConfigSpec.DoubleValue WATER_TALL_CHANCE;
    public final ModConfigSpec.DoubleValue WATER_BUBBLE_SIZE_MULTIPLIER;
    public final ModConfigSpec.DoubleValue WATER_BUBBLE_SPEED_MULTIPLIER;

    static {
        Pair<CuriositiesClientConfig, ModConfigSpec> clientPair = new ModConfigSpec.Builder().configure(CuriositiesClientConfig::new);
        CONFIG = clientPair.getLeft();
        SPEC = clientPair.getRight();
    }

    public CuriositiesClientConfig(ModConfigSpec.Builder builder) {
        builder.push("tooltips"); // ─────────────────────────────────────────────────────────────────────────────


        RENDER_CUSTOM_TOOLTIPS = builder
                .comment("Should custom tooltips be rendered?")
                .define("render_custom_tooltips", true);


        builder.push("matrix"); // ─────────────────────────────────────────────────────────────────────────────

        MATRIX_TEXT_SCALE = builder
                .comment("Matrix waterfall droplet text size")
                .defineInRange("matrix_text_scale", 0.5D, 0.0, 10.0D); // defined as double since spec handles decimals this way

        MATRIX_MAX_DROPLETS = builder
                .comment("Maximum amount of concurrent matrix waterfall droplets")
                .defineInRange("matrix_max_droplets", 8, 1, 64);

        MATRIX_UPDATE_INTERVAL = builder
                .comment("Animation interval in ms (affects glitch boxes and matrix droplets)")
                .defineInRange("matrix_update_interval", 75L, 1L, 5000L);

        MATRIX_OBJECT_PADDING = builder
                .comment("How far glitch boxes and droplets can spawn outside the tooltip")
                .defineInRange("matrix_object_padding", 10, 0, 100);

        builder.pop();


        builder.push("water"); // ─────────────────────────────────────────────────────────────────────────────

        WATER_MARINE_OBJECT_AMOUNT_MULTIPLIER = builder
                .comment("Multiplier on how many total marine objects are generated in total")
                .defineInRange("water_marine_object_amount_multiplier", 1.0D, 0.0, 10.0D);

        WATER_SEAGRASS_CHANCE = builder
                .comment("Chance for a marine object to be seagrass, if not it will be coral")
                .defineInRange("water_seagrass_chance", 0.65D, 0.0, 1D);

        WATER_TALL_CHANCE = builder
                .comment("Chance for a marine object to be tall")
                .defineInRange("water_marine_object_tall_chance", 0.33D, 0.0, 1D);

        WATER_BUBBLE_SPAWN_INTERVAL = builder
                .comment("Bubble spawn interval in ms (affects how many bubbles are spawned)")
                .defineInRange("water_bubble_spawn_interval", 200, 1, Integer.MAX_VALUE);

        WATER_BUBBLE_SIZE_MULTIPLIER = builder
                .comment("Bubble particle size")
                .defineInRange("water_bubble_size_multiplier", 1, 0, 10D);

        WATER_BUBBLE_SPEED_MULTIPLIER = builder
                .comment("Bubble particle speed")
                .defineInRange("water_bubble_speed_multiplier", 1, 0, 10D);

        builder.pop();

        builder.push("fire"); // ─────────────────────────────────────────────────────────────────────────────

        FIRE_EMBER_AMOUNT = builder
                .comment("How many embers to show concurrently")
                .defineInRange("fire_ember_amount", 1, 0, 20);

        FIRE_SMOKE_INTENSITY = builder
                .comment("Intensity of the smoke")
                .defineInRange("fire_smoke_intensity", 2.0, 0.0, 10);

        builder.pop();

        builder.push("snow"); // ─────────────────────────────────────────────────────────────────────────────

        SNOW_SPEED = builder
                .comment("How fast should Motes move")
                .defineInRange("snow_speed", 1.5D, 0.25D, 20);


        builder.pop();


        builder.pop();
    }

}

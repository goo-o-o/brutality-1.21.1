package com.goo.brutality.common;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BrutalityServerConfig {
    public static final BrutalityServerConfig CONFIG;
    public static final ModConfigSpec SPEC;


    static {
        Pair<BrutalityServerConfig, ModConfigSpec> serverPair = new ModConfigSpec.Builder().configure(BrutalityServerConfig::new);
        CONFIG = serverPair.getLeft();
        SPEC = serverPair.getRight();
    }

    public final ModConfigSpec.BooleanValue TWO_HANDED_DISABLES_OFFHAND;
    public final ModConfigSpec.DoubleValue RAGE_GAIN_MULTIPLIER;


    public final ModConfigSpec.IntValue FLIPPERS_OF_ICARUS_BURN_HEIGHT;


    private BrutalityServerConfig(ModConfigSpec.Builder builder) {
        TWO_HANDED_DISABLES_OFFHAND = builder
                .comment("Should Two-Handed items disable offhand slot?")
                .define("two_handed_disables_offhand", true);

        builder.push("rage");

        RAGE_GAIN_MULTIPLIER = builder
                .comment("Global multiplier for how much Rage is gained")
                .defineInRange("rage_gain_multiplier", 1, 0, Double.MAX_VALUE);

        builder.pop();

        FLIPPERS_OF_ICARUS_BURN_HEIGHT = builder
                .comment("Y Level at which the Wearer burns when exposed to Sunlight")
                .defineInRange("flippers_of_icarus_burn_height", 150, -64, 1000);

        builder.push("curio");


        builder.pop();
    }
}

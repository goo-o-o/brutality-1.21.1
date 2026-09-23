package com.goo.curiosities.common;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CuriositiesServerConfig {
    public static final CuriositiesServerConfig CONFIG;
    public static final ModConfigSpec SPEC;


    static {
        Pair<CuriositiesServerConfig, ModConfigSpec> serverPair = new ModConfigSpec.Builder().configure(CuriositiesServerConfig::new);
        CONFIG = serverPair.getLeft();
        SPEC = serverPair.getRight();
    }
    public final ModConfigSpec.IntValue FLIPPERS_OF_ICARUS_BURN_HEIGHT;
    private CuriositiesServerConfig(ModConfigSpec.Builder builder) {

        FLIPPERS_OF_ICARUS_BURN_HEIGHT = builder
                .comment("Y Level at which the Wearer burns when exposed to Sunlight")
                .defineInRange("flippers_of_icarus_burn_height", 150, -64, 1000);

    }
}

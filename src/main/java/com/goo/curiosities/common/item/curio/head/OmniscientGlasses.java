package com.goo.curiosities.common.item.curio.head;

public class OmniscientGlasses extends MiningGoggles {
    public OmniscientGlasses(Properties properties) {
        super(properties);
        // all ores regardless of mod, also radius of 10
        this.statePredicate = ((livingEntity, blockState, pos) -> true);
    }
}

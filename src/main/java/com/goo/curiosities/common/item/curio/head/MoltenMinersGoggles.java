package com.goo.curiosities.common.item.curio.head;

public class MoltenMinersGoggles extends MiningGoggles {
    public MoltenMinersGoggles(Properties properties) {
        super(properties);
        // all ores regardless of mod, also radius of 10
        this.statePredicate = ((livingEntity, blockState, pos) ->
                livingEntity.getPosition(0).distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= 100);
    }
}

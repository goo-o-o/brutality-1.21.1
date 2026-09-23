package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;

import java.util.List;

public class RedactingTape extends CuriositiesCurioItem {
    public RedactingTape(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.unconditional(CuriositiesEffects.REDACTED, 0)
        );
    }
}

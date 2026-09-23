package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;

import java.util.List;

public class CensorshipCloak extends CuriositiesCurioItem {
    public CensorshipCloak(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.unconditional(CuriositiesEffects.CENSORED, 0)
        );
    }
}

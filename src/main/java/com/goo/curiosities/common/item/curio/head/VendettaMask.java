package com.goo.curiosities.common.item.curio.head;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;

import java.util.List;

public class VendettaMask extends CuriositiesCurioItem {
    public VendettaMask(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.unconditional(CuriositiesEffects.REDACTED, 0),
                PassiveMobEffect.unconditional(CuriositiesEffects.INCOGNITO, 0)
        );
    }
}

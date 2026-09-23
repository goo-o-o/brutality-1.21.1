package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class PoseidonsBlessing extends CuriositiesCurioItem {

    public PoseidonsBlessing(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
            PassiveMobEffect.unconditional(MobEffects.WATER_BREATHING, 0),
            PassiveMobEffect.unconditional(MobEffects.CONDUIT_POWER, 0)
        );
    }
}

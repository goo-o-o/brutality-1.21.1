package com.goo.curiosities.common.item.curio.heart;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.util.CombatTracker;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class WarmogsHeart extends CuriositiesCurioItem {
    public WarmogsHeart(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.conditional(MobEffects.REGENERATION, 3, e -> !CombatTracker.isInCombat(e, 100, true))
        );
    }
}
package com.goo.curiosities.common.item.curio.body;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.util.CombatTracker;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class CloakOfInvisibility extends CuriositiesCurioItem {
    public CloakOfInvisibility(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.conditional(MobEffects.INVISIBILITY, 0, e -> !CombatTracker.isInCombat(e, 100))
        );
    }
}

package com.goo.curiosities.common.item.curio.belt;


import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class MiniatureAnchor extends CuriositiesCurioItem {
    public MiniatureAnchor(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.conditional(MobEffects.DAMAGE_RESISTANCE, 3, Entity::isUnderWater)
        );
    }
}

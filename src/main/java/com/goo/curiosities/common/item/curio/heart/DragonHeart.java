package com.goo.curiosities.common.item.curio.heart;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class DragonHeart extends CuriositiesCurioItem {
    public DragonHeart(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.unconditional(MobEffects.DAMAGE_BOOST, 2),
                PassiveMobEffect.unconditional(MobEffects.DAMAGE_RESISTANCE, 0)
        );
    }
}
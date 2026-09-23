package com.goo.curiosities.common.item.curio.heart;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;
import java.util.List;

public class OgresHeart extends CuriositiesCurioItem {
    public OgresHeart(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.conditional(MobEffects.REGENERATION, 3, e -> e.getHealth() / e.getMaxHealth() <= 0.25F)
        );
    }
}
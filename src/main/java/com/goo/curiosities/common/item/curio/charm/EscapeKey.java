package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class EscapeKey extends CuriositiesCurioItem {
    public EscapeKey(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.conditional(MobEffects.MOVEMENT_SPEED, 1, e -> e.getHealth() / e.getMaxHealth() <= 0.25F),
                PassiveMobEffect.conditional(MobEffects.INVISIBILITY, 0, e -> e.getHealth() / e.getMaxHealth() <= 0.25F)
        );
    }
}

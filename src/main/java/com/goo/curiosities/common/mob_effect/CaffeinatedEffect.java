package com.goo.curiosities.common.mob_effect;

import com.goo.curiosities.common.Curiosities;
import com.goo.goo_lib.common.mob_effect.ExpirableEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;


public class CaffeinatedEffect extends ExpirableEffect {
    public CaffeinatedEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                Curiosities.loc("effect.caffeinated.buff.speed"),
                0.20D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                Curiosities.loc("effect.caffeinated.buff.attack_speed"),
                0.20D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.MINING_EFFICIENCY,
                Curiosities.loc("effect.caffeinated.buff.mining_speed"),
                0.20D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

}

package com.goo.curiosities.common.mob_effect;

import com.goo.curiosities.common.Curiosities;
import com.goo.goo_lib.common.mob_effect.ClientSyncableEffect;
import com.goo.goo_lib.common.registry.GLAttributes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;


public class GlitchedEffect extends ClientSyncableEffect {
    public GlitchedEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(
                GLAttributes.DODGE_CHANCE,
                Curiosities.loc("effect.glitched.buff.dodge"),
                0.5,
                AttributeModifier.Operation.ADD_VALUE
        );

    }



}

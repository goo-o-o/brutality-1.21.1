package com.goo.brutality.util;

import com.goo.brutality.common.Brutality;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeUtil {

    public static AttributeModifier getModifier(Holder<Attribute> attribute, double amount, AttributeModifier.Operation operation) {
        String attributePath = attribute.unwrapKey()
                .map(key -> key.location().getPath())
                .orElse("custom_modifier");

        ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath(Brutality.MOD_ID, "modifier." + attributePath);

        return new AttributeModifier(modifierId, amount, operation);
    }
}

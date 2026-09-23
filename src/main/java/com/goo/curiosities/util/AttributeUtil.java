package com.goo.curiosities.util;

import com.goo.curiosities.common.Curiosities;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class AttributeUtil {
    public static double getItemBaseAttackDamage(ItemStack stack) {
        ItemAttributeModifiers modifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            if (entry.attribute().equals(Attributes.ATTACK_DAMAGE) && entry.modifier().id().equals(Item.BASE_ATTACK_DAMAGE_ID)) {
                return entry.modifier().amount();
            }
        }
        return 0.0D;
    }
    public static AttributeModifier getModifier(Holder<Attribute> attribute, double amount, AttributeModifier.Operation operation) {
        String attributePath = attribute.unwrapKey()
                .map(key -> key.location().getPath())
                .orElse("custom_modifier");

        ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath(Curiosities.MOD_ID, "modifier." + attributePath);

        return new AttributeModifier(modifierId, amount, operation);
    }
}

package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class HoneyBoots extends CuriositiesCurioItem {
    public HoneyBoots(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            if (entity.onGround()) {
                Multimap<Holder<Attribute>, AttributeModifier> map = super.getAttributeModifiers(slotContext, id, stack);

                map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, Double.POSITIVE_INFINITY, AttributeModifier.Operation.ADD_VALUE));
                return map;
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.KNOCKBACK_RESISTANCE)) {
            if (slotContext.entity().onGround())
                return Double.POSITIVE_INFINITY;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }
}

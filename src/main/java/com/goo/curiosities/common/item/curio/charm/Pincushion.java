package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
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

public class Pincushion extends CuriositiesCurioItem {
    public Pincushion(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {

            Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
            int arrowsStuck = entity.getArrowCount();
            if (arrowsStuck > 0) {

                map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, 0.5F * arrowsStuck, AttributeModifier.Operation.ADD_VALUE));
                return map;
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.ATTACK_DAMAGE)) {
            return slotContext.entity().getArrowCount() * 0.5F;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }
}

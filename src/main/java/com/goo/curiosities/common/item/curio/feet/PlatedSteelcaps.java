package com.goo.curiosities.common.item.curio.feet;

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

public class PlatedSteelcaps extends CuriositiesCurioItem {
    public PlatedSteelcaps(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {

            Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
            map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(id, 0.15F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            if (entity.isSprinting()) {
                map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, 0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                map.put(Attributes.ARMOR, new AttributeModifier(id, 4, AttributeModifier.Operation.ADD_VALUE));
            }

            return map;

        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (slotContext.entity().isSprinting()) {
            if (attributeInstance.getAttribute().is(Attributes.ARMOR)) {
                return 4F;
            }
            if (attributeInstance.getAttribute().is(Attributes.KNOCKBACK_RESISTANCE)) {
                return 0.5 * total;
            }
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }
}

package com.goo.brutality.common.item.curio.function;

import com.goo.brutality.common.item.BrutalityFunctionCurioItem;
import com.goo.brutality.util.EntityUtil;
import com.google.common.collect.ImmutableMultimap;
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

import java.util.List;

public class Addition extends BrutalityFunctionCurioItem {
    public Addition(Properties properties) {
        super(properties);
    }

    private static float getCurrentBonus(LivingEntity livingEntity) {
        List<LivingEntity> hostiles = EntityUtil.getFoes(livingEntity, 5);
        return hostiles.size() * 0.5F;
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute() == Attributes.ATTACK_DAMAGE) {
            return getCurrentBonus(slotContext.entity());
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, getCurrentBonus(entity), AttributeModifier.Operation.ADD_VALUE));

            return builder.build();
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

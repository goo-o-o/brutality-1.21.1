package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityCurioItem;
import com.goo.brutality.util.CurioUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.BooleanAttribute;
import top.theillusivec4.curios.api.SlotContext;

public class MoltenMacronutrients extends BrutalityCurioItem {
    public MoltenMacronutrients(Properties properties) {
        super(properties);
    }



    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return !CurioUtil.isWearingCurio(slotContext.entity(), this);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (slotContext.entity().isOnFire()) {
            double amount = attributeInstance.getAttribute().value().sentiment == Attribute.Sentiment.NEGATIVE ? -0.05D : 0.05D;
            return total * amount;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            if (slotContext.entity().isOnFire()) {
                ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

                net.minecraft.core.registries.BuiltInRegistries.ATTRIBUTE.holders().forEach(attributeHolder -> {
                    Attribute attribute = attributeHolder.value();

                    double amount = attribute.sentiment == Attribute.Sentiment.NEGATIVE ? -0.05D : 0.05D;
                    if (!(attribute instanceof BooleanAttribute)) {
                        builder.put(attributeHolder, new AttributeModifier(
                                id,
                                amount,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        ));
                    }
                });

                return builder.build();
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

package com.goo.brutality.common.item.curio.function;

import com.goo.brutality.common.item.BrutalityFunctionCurioItem;
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
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

public class Sine extends BrutalityFunctionCurioItem {
    public Sine(Properties properties) {
        super(properties);
    }

    private static float getCurrentBonus(Level level) {
        return (float) ((Math.cos(level.getGameTime() * 0.05) + 1F) * 0.5F * 0.125F);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute() == Attributes.ATTACK_DAMAGE) {
            return getCurrentBonus(slotContext.entity().level()) * total;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, getCurrentBonus(entity.level()), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            return builder.build();
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

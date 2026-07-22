package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.registry.BrutalityAttributes;
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

public class InfernalBlood extends BoilingBlood {
    public InfernalBlood(Properties properties) {
        super(properties);
    }


    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        LivingEntity entity = slotContext.entity();
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (attribute.is(Attributes.ATTACK_DAMAGE) && slotContext.entity().isOnFire()) {
            return slotContext.entity().getRemainingFireTicks() / 20F * 0.5F;
        } else if (attribute.is(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO) && entity.getHealth() / entity.getMaxHealth() < 0.5) {
            return total + 0.25F;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null) {
            if (entity.level().isClientSide()) {
                if (entity.getHealth() / entity.getMaxHealth() < 0.5) {
                    ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
                    builder.put(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, new AttributeModifier(id, 0.25, AttributeModifier.Operation.ADD_VALUE));
                    return builder.build();
                }
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

package com.goo.brutality.common.items.curio.belt;

import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.common.registry.BrutalityEffects;
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

public class GammaSerum extends BrutalityRageCurioItem {
    public GammaSerum(Properties properties) {
        super(properties);
    }


    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (attribute.is(Attributes.SCALE) && slotContext.entity().hasEffect(BrutalityEffects.ENRAGED)) {
            return total * 0.5;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null) {
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

            if (entity.level().isClientSide())
                if (entity.hasEffect(BrutalityEffects.ENRAGED))
                    builder.put(Attributes.SCALE, new AttributeModifier(id, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            builder.put(BrutalityAttributes.ENRAGED_LEVEL, new AttributeModifier(id, 1, AttributeModifier.Operation.ADD_VALUE));

            return builder.build();
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

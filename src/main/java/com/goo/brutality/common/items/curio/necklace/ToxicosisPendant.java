package com.goo.brutality.common.items.curio.necklace;

import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class ToxicosisPendant extends BrutalityRageCurioItem {
    public ToxicosisPendant(Properties properties) {
        super(properties);
    }


    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().value() == BrutalityAttributes.DAMAGE_TO_RAGE_RATIO.value()) {
            int count = 0;
            for (MobEffectInstance mobEffectInstance : slotContext.entity().getActiveEffects())
                if (mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) count++;

            return count * 0.15;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() != null && slotContext.entity().level().isClientSide()) {
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

            int count = 0;
            for (MobEffectInstance mobEffectInstance : slotContext.entity().getActiveEffects())
                if (mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) count++;

            builder.put(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, new AttributeModifier(id, count * 0.15F, AttributeModifier.Operation.ADD_VALUE));

            return builder.build();
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

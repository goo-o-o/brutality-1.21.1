package com.goo.brutality.common.items.curio.ring;

import com.goo.brutality.common.items.BrutalityRageCurioItem;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class RingOfRings extends BrutalityRageCurioItem {
    public RingOfRings(Properties properties) {
        super(properties);
    }

    private int getRingCount(LivingEntity livingEntity) {
        return CuriosApi.getCuriosInventory(livingEntity).map(handler -> handler.findCurios("ring").size()).orElse(0);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (attribute.value() == BrutalityAttributes.LETHALITY.value() || attribute.value() == Attributes.ARMOR.value()) {
            return getRingCount(slotContext.entity());
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() != null && slotContext.entity().level().isClientSide()) {
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

            double ringCount = getRingCount(slotContext.entity());

            builder.put(BrutalityAttributes.LETHALITY, new AttributeModifier(id, ringCount, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Attributes.ARMOR, new AttributeModifier(id, ringCount, AttributeModifier.Operation.ADD_VALUE));

            return builder.build();
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

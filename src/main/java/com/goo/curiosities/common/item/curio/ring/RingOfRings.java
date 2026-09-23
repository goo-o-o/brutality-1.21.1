package com.goo.curiosities.common.item.curio.ring;

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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class RingOfRings extends CuriositiesCurioItem {
    public RingOfRings(Properties properties) {
        super(properties);
    }

    private int getRingCount(LivingEntity livingEntity) {
        return CuriosApi.getCuriosInventory(livingEntity).map(handler -> handler.findCurios("ring").size()).orElse(0);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (attribute.is(Attributes.ATTACK_DAMAGE) || attribute.value() == Attributes.ARMOR.value()) {
            return getRingCount(slotContext.entity());
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() != null && slotContext.entity().level().isClientSide()) {
            Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();

            double ringCount = getRingCount(slotContext.entity());
            if (ringCount > 0) {
                map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, ringCount, AttributeModifier.Operation.ADD_VALUE));
                map.put(Attributes.ARMOR, new AttributeModifier(id, ringCount, AttributeModifier.Operation.ADD_VALUE));

                return map;
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

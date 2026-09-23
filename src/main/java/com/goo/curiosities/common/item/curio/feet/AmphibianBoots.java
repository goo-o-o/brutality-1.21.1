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
import net.neoforged.neoforge.common.NeoForgeMod;
import top.theillusivec4.curios.api.SlotContext;

public class AmphibianBoots extends CuriositiesCurioItem {
    public AmphibianBoots(Properties properties) {
        super(properties);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute() == Attributes.ATTACK_SPEED && slotContext.entity().isInWater()) {
            return unmodified * 1.5;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }


    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
        if (entity != null) {
            map.put(NeoForgeMod.SWIM_SPEED, new AttributeModifier(id, 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            if (entity.level().isClientSide()) {
                if (entity.isInWater()) {
                    map.put(Attributes.ATTACK_SPEED, new AttributeModifier(id, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                }
            }
            return map;
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

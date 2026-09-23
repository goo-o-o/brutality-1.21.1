package com.goo.curiosities.common.item.curio.necklace;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
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

public class BedrockNecklace extends CuriositiesCurioItem {

    public BedrockNecklace(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            if (HandOfDestruction.canActivate(slotContext.entity())) {
                Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
                map.put(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(id, 0.35F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                return map;
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.BLOCK_BREAK_SPEED)) {
            if (HandOfDestruction.canActivate(slotContext.entity())) return 0.35F;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }


}

package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.registry.GLAttributes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class IceSkates extends CuriositiesCurioItem {


    public IceSkates(Properties properties) {
        super(properties);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(GLAttributes.FRICTION_MODIFIER) && slotContext.entity().getBlockStateOn().is(BlockTags.ICE)) {
            return -0.95F;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null) {

            if (entity.level().isClientSide()) {
                if (entity.getBlockStateOn().is(BlockTags.ICE)) {
                    Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
                    map.put(GLAttributes.FRICTION_MODIFIER, new AttributeModifier(id, -0.95, AttributeModifier.Operation.ADD_VALUE));
                    return map;
                }
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

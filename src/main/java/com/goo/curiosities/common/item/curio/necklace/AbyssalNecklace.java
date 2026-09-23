package com.goo.curiosities.common.item.curio.necklace;

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

public class AbyssalNecklace extends CuriositiesCurioItem {
    public AbyssalNecklace(Properties properties) {
        super(properties);
    }


    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (attribute.is(Attributes.ATTACK_DAMAGE) || attribute.value() == NeoForgeMod.SWIM_SPEED.value()) {
            double percentageBonus = getPercentageBonus(slotContext.entity());
            return total * (percentageBonus * 0.5);
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    private static double getPercentageBonus(LivingEntity livingEntity) {
        if (!livingEntity.isInWater()) return 0;
        double y = livingEntity.getY();
        if (y > 63) return 0;

        // bottom of world is -64 so theres 127 blocks
        return 1D - (y / 127);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null) {

            if (entity.level().isClientSide()) {
                double bonus = getPercentageBonus(entity);
                if (bonus > 0) {
                    Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
                    map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    map.put(NeoForgeMod.SWIM_SPEED, new AttributeModifier(id, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    return map;
                }
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesAttachments;
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
import top.theillusivec4.curios.api.SlotContext;

public class SlipstreamTracers extends CuriositiesCurioItem {
    public SlipstreamTracers(Properties properties) {
        super(properties);
    }

    private float getBonus(LivingEntity livingEntity) {
        // 0.005 = 0.5% per tick (10% per second)
        return livingEntity.getExistingData(CuriositiesAttachments.SPRINT_START_TIME)
                .map(startTime -> startTime == -1L ? 0F : (livingEntity.level().getGameTime() - startTime) * 0.005F)
                .orElse(0F);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            float bonus = getBonus(entity);
            if (bonus > 0) {
                Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();

                map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(id, bonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                return map;
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.MOVEMENT_SPEED)) {
            return unmodified * getBonus(slotContext.entity());
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }
}

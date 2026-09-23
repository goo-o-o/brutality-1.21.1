package com.goo.curiosities.common.item.curio.necklace;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesAttachments;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

public class HandOfDestruction extends CuriositiesCurioItem {

    public HandOfDestruction(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            int combo = entity.getData(CuriositiesAttachments.MOMENTUM_COMBO).combo();
            double modifierValue = combo * 0.01;
            double amount = canActivate(slotContext.entity()) ? 1.25F : 0.75F;
            amount += modifierValue;
            Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
            map.put(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            return map;
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.BLOCK_BREAK_SPEED)) {
            LivingEntity entity = slotContext.entity();
            int combo = entity.getData(CuriositiesAttachments.MOMENTUM_COMBO).combo();

            double baseBonus = 0.75D;
            if (canActivate(entity)) {
                baseBonus += 0.50D;
            }

            double comboBonus = combo * 0.01D;

            return baseBonus + comboBonus;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    public static boolean canActivate(LivingEntity wearer) {
        if (wearer.getY() <= 63) {
            BlockPos eyePos = BlockPos.containing(wearer.getEyePosition());
            Level level = wearer.level();
            return !level.canSeeSky(eyePos);
        }
        return false;
    }
}

package com.goo.brutality.common.item.curio.hand;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.rage.RageHandler;
import com.goo.goo_lib.common.registry.GLAttributes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class AngerManagement extends BrutalityRageCurioItem {
    public AngerManagement(Properties properties) {
        super(properties);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (slotContext.entity() instanceof Player player && RageHandler.getCurrentRagePercentage(player) >= 1) {
            if (attribute.is(Attributes.ATTACK_DAMAGE)) {
                return 5;
            } else if (attribute.is(Attributes.ARMOR)) {
                return 4;
            } else if (attribute.is(GLAttributes.CRITICAL_DAMAGE)) {
                return 0.25F;
            }
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() instanceof Player player && RageHandler.getCurrentRagePercentage(player) >= 1) {
            if (player.level().isClientSide()) {
                ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

                builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, 5, AttributeModifier.Operation.ADD_VALUE));
                builder.put(Attributes.ARMOR, new AttributeModifier(id, 4, AttributeModifier.Operation.ADD_VALUE));
                builder.put(GLAttributes.CRITICAL_DAMAGE, new AttributeModifier(id, 0.25, AttributeModifier.Operation.ADD_VALUE));

                return builder.build();
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

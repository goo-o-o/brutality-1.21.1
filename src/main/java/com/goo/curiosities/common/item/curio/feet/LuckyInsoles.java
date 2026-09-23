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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class LuckyInsoles extends CuriositiesCurioItem {
    public LuckyInsoles(Properties properties) {
        super(properties);
    }


    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();

        if (attribute.is(Attributes.MOVEMENT_SPEED)) {
            if (slotContext.entity() instanceof Player player)
                return 0.1 * total * player.getLuck();
        }
        if (attribute.getRegisteredName().equals(Attributes.SAFE_FALL_DISTANCE.getRegisteredName())) {
            if (slotContext.entity() instanceof Player player)
                return player.getLuck();
        }

        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.level().isClientSide()) {

                float luck = player.getLuck();
                if (luck > 0) {
                    Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
                    map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(id, luck * 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    map.put(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(id, luck, AttributeModifier.Operation.ADD_VALUE));
                    return map;

                }
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

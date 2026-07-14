package com.goo.brutality.common.items.curio.charm;

import com.goo.brutality.common.items.BrutalityCurioItem;
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
import top.theillusivec4.curios.api.SlotContext;

public class NumismaticCatalyst extends BrutalityCurioItem {
    public NumismaticCatalyst(Properties properties) {
        super(properties);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.ATTACK_DAMAGE)) {
            // TODO: Implement CoinHelper
            //            LivingEntity player = slotContext.entity();
            //            return CoinHelper.getNearbyCoinCount(player, 5, coinRigidBody -> coinRigidBody.getOwner() == player);
            return 1;
        }
        return unmodified;
    }


    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() != null && slotContext.entity().level().isClientSide()) {
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            LivingEntity player = slotContext.entity();

            //            double damageBonus = CoinHelper.getNearbyCoinCount(player, 5, coinRigidBody -> coinRigidBody.getOwner() == player);
            int damageBonus = 1;
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, damageBonus, AttributeModifier.Operation.ADD_VALUE));

            return builder.build();
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

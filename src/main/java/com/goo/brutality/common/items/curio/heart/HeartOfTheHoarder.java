package com.goo.brutality.common.items.curio.heart;


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

public class HeartOfTheHoarder extends BrutalityCurioItem {

    public HeartOfTheHoarder(Properties properties) {
        super(properties);
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.ATTACK_DAMAGE)) {
            // TODO: Implement CoinHelper
            //            return CoinHelper.getNearbyCoinCount(slotContext.entity(), 5, null);
            return 1;
        }
        return unmodified;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() != null && slotContext.entity().level().isClientSide()) {
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            LivingEntity player = slotContext.entity();

//            double healthBonus = CoinHelper.getNearbyCoinCount(player, 5, null);
            int healthBonus = 1;
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(id, healthBonus, AttributeModifier.Operation.ADD_VALUE));

            return builder.build();
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }


}

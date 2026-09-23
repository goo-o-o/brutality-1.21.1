package com.goo.curiosities.common.item.curio.heart;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.util.DelayedTaskScheduler;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

public class HeartOfGold extends CuriositiesCurioItem {
    public HeartOfGold(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        float damageTaken = event.getNewDamage();
        float absorption = wearer.getAbsorptionAmount();

        float healthDamageTaken = Math.max(0F, damageTaken - absorption);
        if (healthDamageTaken >= 0F) {
            float amountToHeal = healthDamageTaken * 0.25F;
            DelayedTaskScheduler.queueServerWork(wearer.level(), 1, () ->
                    wearer.setAbsorptionAmount(wearer.getAbsorptionAmount() + amountToHeal)
            );
        }
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (attribute.is(Attributes.MAX_ABSORPTION)) {
            return slotContext.entity().getMaxHealth();
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null) {

            if (entity.level().isClientSide()) {
                Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
                map.put(Attributes.MAX_ABSORPTION, new AttributeModifier(id, entity.getMaxHealth(), AttributeModifier.Operation.ADD_VALUE));
                return map;
            }

        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

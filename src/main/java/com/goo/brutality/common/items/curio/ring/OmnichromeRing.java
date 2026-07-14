package com.goo.brutality.common.items.curio.ring;

import com.goo.brutality.common.items.BrutalityCurioItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class OmnichromeRing extends BrutalityCurioItem {
    public OmnichromeRing(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 40 == 0) {
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60));
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 60));
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60));
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60));
        }
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}

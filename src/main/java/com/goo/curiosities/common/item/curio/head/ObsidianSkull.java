package com.goo.curiosities.common.item.curio.head;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ObsidianSkull extends CuriositiesCurioItem {
    public ObsidianSkull(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerIncomingHurt(LivingEntity wearer, ItemStack curio, LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypeTags.BURN_FROM_STEPPING)) event.setCanceled(true);
    }
}
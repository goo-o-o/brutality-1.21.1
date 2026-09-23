package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class BeadOfLife extends CuriositiesCurioItem {
    public BeadOfLife(Properties properties) {
        super(properties);
    }


    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        wearer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 2), wearer);
    }
}

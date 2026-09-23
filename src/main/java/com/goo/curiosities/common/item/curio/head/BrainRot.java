package com.goo.curiosities.common.item.curio.head;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class BrainRot extends CuriositiesCurioItem {
    public BrainRot(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerMeleeHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource damageSource, LivingDamageEvent.Pre event) {
        if (victim instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 80, 0), attacker);
        }
    }
}
package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.util.MobEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class SoldiersSyringe extends CuriositiesCurioItem {

    public SoldiersSyringe(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerMeleeHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource damageSource, LivingDamageEvent.Pre event) {
        MobEffectUtil.modifyEffect(
                attacker,
                attacker,
                MobEffects.DIG_SPEED,
                new MobEffectUtil.ModValue(40, true),
                new MobEffectUtil.ModValue(1, false),
                9,
                e -> e.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40, 0)),
                null
                );
    }
}

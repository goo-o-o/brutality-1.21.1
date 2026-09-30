package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.QuiverCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class ToxicQuiver extends QuiverCurioItem {
    public ToxicQuiver(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (source.is(DamageTypes.ARROW)) {
            if (victim instanceof LivingEntity livingEntity)
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 0), attacker);
        }
    }
}

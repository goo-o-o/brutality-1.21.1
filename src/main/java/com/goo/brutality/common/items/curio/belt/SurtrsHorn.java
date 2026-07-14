package com.goo.brutality.common.items.curio.belt;

import com.goo.brutality.common.items.BrutalityCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import top.theillusivec4.curios.api.SlotContext;

public class SurtrsHorn extends BrutalityCurioItem {
    public SurtrsHorn(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 40 == 0)
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60));
    }

    @Override
    public void onWearerMeleeHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource damageSource, DamageContainer container) {
        victim.igniteForSeconds(10);
    }
}

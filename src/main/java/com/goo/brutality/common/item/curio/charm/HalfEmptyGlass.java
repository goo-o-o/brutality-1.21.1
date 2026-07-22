package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityCurioItem;
import com.goo.brutality.common.registry.BrutalityEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class HalfEmptyGlass extends BrutalityCurioItem {
    public HalfEmptyGlass(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, DamageContainer container) {
        if (victim instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(BrutalityEffects.DESPAIR, 100, 0), attacker);
        }
    }
}

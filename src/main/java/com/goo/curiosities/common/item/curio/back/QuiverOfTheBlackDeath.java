package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.util.EntityUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class QuiverOfTheBlackDeath extends CuriositiesCurioItem {
    public QuiverOfTheBlackDeath(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (source.is(DamageTypes.ARROW)) {
            if (victim instanceof LivingEntity livingEntity) {
                if (!livingEntity.hasEffect(MobEffects.WITHER)) {
                    event.setNewDamage(event.getNewDamage() * 1.5F);
                }
            }
            List<LivingEntity> nearby = EntityUtil.getNearbyEnemies(attacker, source.getSourcePosition(), 2);
            nearby.forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 2), attacker));

        }
    }
}

package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class QuiverOfFamine extends CuriositiesCurioItem {
    public QuiverOfFamine(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (source.is(DamageTypes.ARROW)) {
            if (victim instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20, 99), attacker);
                if (livingEntity instanceof Player player) {
                    event.setNewDamage(event.getNewDamage() + (20 - player.getFoodData().getFoodLevel()));
                }
            }
        }
    }
}

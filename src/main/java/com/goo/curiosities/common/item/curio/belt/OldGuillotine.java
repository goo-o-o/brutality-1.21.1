package com.goo.curiosities.common.item.curio.belt;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class OldGuillotine extends CuriositiesCurioItem {
    // todo: add config
    private static final float THRESHOLD = 5;

    public OldGuillotine(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerMeleeHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource damageSource, LivingDamageEvent.Pre event) {
        if (victim instanceof LivingEntity livingEntity) {
            if (livingEntity.getHealth() <= THRESHOLD) {
                livingEntity.kill();
            }
        }
    }
}

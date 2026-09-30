package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.QuiverCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class PhoenixQuiver extends QuiverCurioItem {
    public PhoenixQuiver(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (source.is(DamageTypes.ARROW)) {
            int fireTicks = victim.getRemainingFireTicks();
            float fireSeconds = fireTicks / 20F;
            // 1 second will heal for 0.25hp, so 1hp/4s
            victim.extinguishFire();
            attacker.heal(fireSeconds * 0.25F);
        }
    }
}

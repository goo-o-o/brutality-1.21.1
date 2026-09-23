package com.goo.curiosities.common.item.curio.head;


import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class CrownOfTyranny extends CuriositiesCurioItem {
    public CrownOfTyranny(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (!(victim instanceof LivingEntity target)) return;

        float missingHealthPercent = 1.0f - (target.getHealth() / target.getMaxHealth());
        float damageMultiplier = 1.0f + (missingHealthPercent * 0.75f);

        event.setNewDamage(event.getNewDamage() * damageMultiplier);
    }
}

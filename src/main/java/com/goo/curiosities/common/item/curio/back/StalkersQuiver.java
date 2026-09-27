package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class StalkersQuiver extends CuriositiesCurioItem {
    public StalkersQuiver(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (source.is(DamageTypes.ARROW)) {
            if (victim instanceof Mob mob) {
                if (mob.getTarget() != attacker) {
                    event.setNewDamage(event.getNewDamage() * 1.5F);
                }
            }
        }
    }
}

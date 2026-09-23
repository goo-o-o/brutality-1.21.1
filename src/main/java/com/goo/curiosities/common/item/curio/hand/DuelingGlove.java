package com.goo.curiosities.common.item.curio.hand;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.util.EntityUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class DuelingGlove extends CuriositiesCurioItem {
    public DuelingGlove(Properties properties) {
        super(properties);
    }

    private LivingEntity getNearest(LivingEntity wearer) {
        return wearer.level().getNearestEntity(
                LivingEntity.class,
                TargetingConditions.DEFAULT.ignoreLineOfSight().ignoreInvisibilityTesting().selector(e -> !EntityUtil.isAlly(e, wearer)),
                wearer,
                wearer.getX(),
                wearer.getY(0.5),
                wearer.getZ(),
                wearer.getBoundingBox().inflate(25)
                );
    }

    @Override
    public void onWearerIncomingHurt(LivingEntity wearer, ItemStack curio, LivingIncomingDamageEvent event) {
        if (getNearest(wearer) != event.getSource().getEntity()) {
            event.setAmount(event.getAmount() * 1.5F);
        }
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (getNearest(attacker) == victim) {
            event.setNewDamage(event.getNewDamage() * 1.5F);
        }
    }
}

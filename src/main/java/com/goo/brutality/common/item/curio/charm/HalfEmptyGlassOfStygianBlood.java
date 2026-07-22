package com.goo.brutality.common.item.curio.charm;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class HalfEmptyGlassOfStygianBlood extends HalfEmptyGlass {
    public HalfEmptyGlassOfStygianBlood(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, DamageContainer container) {
        super.onWearerHit(attacker, victim, curio, source, container);
        if (victim instanceof LivingEntity livingEntity) {
            if (livingEntity.getRandom().nextFloat() <= 0.1) {
                // TODO: 10% chance to inflict Hexed to victim
            }
        }
    }
}

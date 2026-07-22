package com.goo.brutality.common.item.curio.function;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.item.BrutalityFunctionCurioItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class Division extends BrutalityFunctionCurioItem {
    public Division(Properties properties) {
        super(properties);
    }


    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, DamageContainer container) {
        if (wearer.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(BrutalityParticles.DIVISION.get(), wearer.getX(), wearer.getY(0.5), wearer.getZ(),
                    10, 0.1, 0.1, 0.1, 0.25);
        }
        container.setNewDamage(container.getNewDamage() / 1.2F);
    }
}

package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class BootsOfSwiftness extends CuriositiesCurioItem {
    public BootsOfSwiftness(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerMobEffectApplicable(LivingEntity wearer, ItemStack curio, MobEffectEvent.Applicable event) {
        if (event.getEffectInstance().is(MobEffects.MOVEMENT_SLOWDOWN)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }
}

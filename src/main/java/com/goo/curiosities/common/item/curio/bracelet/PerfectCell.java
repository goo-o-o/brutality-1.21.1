package com.goo.curiosities.common.item.curio.bracelet;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class PerfectCell extends CuriositiesCurioItem {
    public PerfectCell(Properties properties) {
        super(properties);
    }


    @Override
    public void onWearerMobEffectApplicable(LivingEntity wearer, ItemStack curio, MobEffectEvent.Applicable event) {
        MobEffectInstance instance = event.getEffectInstance();
        if (
                instance.is(MobEffects.POISON) || instance.is(MobEffects.WITHER)
        ) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }
}

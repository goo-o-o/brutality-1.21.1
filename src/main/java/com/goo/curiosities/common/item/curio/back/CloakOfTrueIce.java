package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class CloakOfTrueIce extends CuriositiesCurioItem {
    public CloakOfTrueIce(Properties properties) {
        super(properties);
    }
    
    @Override
    public void onWearerMobEffectApplicable(LivingEntity wearer, ItemStack curio, MobEffectEvent.Applicable event) {
        if (event.getEffectInstance().is(CuriositiesEffects.FROZEN)) event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }
}

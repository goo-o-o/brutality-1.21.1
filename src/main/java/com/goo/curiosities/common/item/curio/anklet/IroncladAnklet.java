package com.goo.curiosities.common.item.curio.anklet;

import com.goo.curiosities.common.item.AnkletCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class IroncladAnklet extends AnkletCurioItem {
    public IroncladAnklet(Properties properties) {
        super(properties);
    }

    @Override
    protected void onWearerDodge(LivingEntity entity, DamageSource source, double roll, DamageContainer container, ItemStack stack) {
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2));
    }
}

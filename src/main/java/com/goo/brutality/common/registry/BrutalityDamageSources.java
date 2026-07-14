package com.goo.brutality.common.registry;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;

public class BrutalityDamageSources {
    /**
     * No causes to prevent certain death messages, used for {@link BrutalityEffects#ASURA_FORM} as a marker for damage
     */
    public static DamageSource asuraForm(LivingEntity entity) {
        DamageSources sources = entity.level().damageSources();
        return sources.source(BrutalityDamageTypes.ASURA_FORM);
    }
}
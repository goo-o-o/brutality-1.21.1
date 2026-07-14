package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class BrutalityDamageTypes {
    public static final ResourceKey<DamageType> ASURA_FORM = ResourceKey.create(
            Registries.DAMAGE_TYPE, Brutality.loc("asura_form")
    );

    
}

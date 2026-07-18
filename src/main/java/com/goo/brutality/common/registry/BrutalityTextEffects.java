package com.goo.brutality.common.registry;

import com.goo.brutality.client.render.text.ReefEffect;
import com.goo.brutality.common.Brutality;
import com.goo.goo_lib.client.text.EffectType;
import com.goo.goo_lib.common.registry.TextEffects;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BrutalityTextEffects {
    public static final DeferredRegister<EffectType<?>> REGISTRY =
            DeferredRegister.create(EffectType.REGISTRY_KEY, Brutality.MOD_ID);

    public static final Supplier<EffectType<Float>> REEF_TYPE = TextEffects.register("reef", ReefEffect::new);

}

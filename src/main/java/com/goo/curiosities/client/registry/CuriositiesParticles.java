package com.goo.curiosities.client.registry;

import com.goo.curiosities.common.Curiosities;
import com.goo.goo_lib.client.particle.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CuriositiesParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Curiosities.MOD_ID);

    public static final Supplier<SimpleParticleType> OMEGA = PARTICLE_TYPES.register(
            "omega", () -> new SimpleParticleType(false)
    );

    public static final Supplier<SimpleParticleType> SANDSTORM_SMOKE = PARTICLE_TYPES.register(
            "sandstorm_smoke", () -> new SimpleParticleType(false)
    );

    public static final Supplier<SimpleParticleType> SANDSTORM_DUST = PARTICLE_TYPES.register(
            "sandstorm_dust", () -> new SimpleParticleType(false)
    );

    public static final Supplier<SimpleParticleType> ONOMATOPOEIA = PARTICLE_TYPES.register(
            "onomatopoeia", () -> new SimpleParticleType(false)
    );

    // ─────────────────────────────────────────────────────────────────────────────────────

    public static final DeferredHolder<ParticleType<?>, ParticleType<ComponentParticleOption>> MULTIPLIER = PARTICLE_TYPES.register(
            "multiplier", () -> new ComponentParticleType(true)
    );
    // ─────────────────────────────────────────────────────────────────────────────────────


    public static final DeferredHolder<ParticleType<?>, ParticleType<FlatParticleOption>> MOLTEN_FOOTPRINT = PARTICLE_TYPES.register(
            "molten_footprint", () -> new FlatParticleType(true)
    );

    public static final DeferredHolder<ParticleType<?>, ParticleType<WaveParticleOption>> SEISMIC_SHOCKWAVE =
            PARTICLE_TYPES.register("seismic_shockwave", () -> new WaveParticleType(false));

    public static final DeferredHolder<ParticleType<?>, ParticleType<WaveParticleOption>> FIRE_WAVE =
            PARTICLE_TYPES.register("fire_wave", () -> new WaveParticleType(false));



}

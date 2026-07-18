package com.goo.brutality.client.registry;

import com.goo.brutality.common.Brutality;
import com.goo.goo_lib.client.particle.ComponentParticleOption;
import com.goo.goo_lib.client.particle.ComponentParticleType;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import com.goo.goo_lib.client.particle.FlatParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BrutalityParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Brutality.MOD_ID);


    public static final Supplier<SimpleParticleType> STYGIAN_SOUL = PARTICLE_TYPES.register(
            "stygian_soul", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> ENRAGED = PARTICLE_TYPES.register(
            "enraged", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> ASURA = PARTICLE_TYPES.register(
            "asura", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> TRANQUILITY = PARTICLE_TYPES.register(
            "tranquility", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> BLACK_HOLE = PARTICLE_TYPES.register(
            "black_hole", () -> new SimpleParticleType(false)
    );

    public static final Supplier<SimpleParticleType> BLOODSPLOSION_EMITTER = PARTICLE_TYPES.register(
            "bloodsplosion_emitter", () -> new SimpleParticleType(true)
    );
    public static final Supplier<SimpleParticleType> BLOODSPLOSION = PARTICLE_TYPES.register(
            "bloodsplosion", () -> new SimpleParticleType(true)
    );

    // ── Poker Chips ──────────────────────────────────────────────────────────────────
    public static final Supplier<SimpleParticleType> POKER_CHIP_BLACK = PARTICLE_TYPES.register(
            "poker_chip_black", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> POKER_CHIP_BLUE = PARTICLE_TYPES.register(
            "poker_chip_blue", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> POKER_CHIP_GREEN = PARTICLE_TYPES.register(
            "poker_chip_green", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> POKER_CHIP_RED = PARTICLE_TYPES.register(
            "poker_chip_red", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> POKER_CHIP_RED_INVERSE = PARTICLE_TYPES.register(
            "poker_chip_red_inverse", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> POKER_CHIP_YELLOW = PARTICLE_TYPES.register(
            "poker_chip_yellow", () -> new SimpleParticleType(false)
    );
    // ─────────────────────────────────────────────────────────────────────────────────────

    public static final Supplier<SimpleParticleType> MATH = PARTICLE_TYPES.register(
            "math", () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> OMEGA = PARTICLE_TYPES.register(
            "omega", () -> new SimpleParticleType(false)
    );

    public static final Supplier<SimpleParticleType> ONOMATOPOEIA = PARTICLE_TYPES.register(
            "onomatopoeia", () -> new SimpleParticleType(false)
    );

    // ─────────────────────────────────────────────────────────────────────────────────────

    public static final DeferredHolder<ParticleType<?>, ParticleType<ComponentParticleOption>> MULTIPLIER = PARTICLE_TYPES.register(
            "multiplier", () -> new ComponentParticleType(true)
    );
    // ─────────────────────────────────────────────────────────────────────────────────────

    public static final DeferredHolder<ParticleType<?>, ParticleType<FlatParticleOption>> HEXING_CIRCLE = PARTICLE_TYPES.register(
            "hexing_circle", () -> new FlatParticleType(true)
    );

}

package com.goo.curiosities.common.registry;

import com.goo.curiosities.common.Curiosities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CuriositiesSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Curiosities.MOD_ID);


    public static final DeferredHolder<SoundEvent, SoundEvent> BOING = SOUND_EVENTS.register(
            "boing", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("boing"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> TIME_STOP = SOUND_EVENTS.register(
            "time_stop", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("time_stop"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> TIME_RESUME = SOUND_EVENTS.register(
            "time_resume", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("time_resume"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> HEAVY_STOMP = SOUND_EVENTS.register(
            "heavy_stomp", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("heavy_stomp"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> DOUBLE_JUMP = SOUND_EVENTS.register(
            "double_jump", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("double_jump"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> POP = SOUND_EVENTS.register(
            "pop", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("pop"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> ICE_FREEZE = SOUND_EVENTS.register(
            "ice_freeze", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("ice_freeze"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> GROUND_SMASH = SOUND_EVENTS.register(
            "ground_smash", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("ground_smash"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> GROUND_SMASH_LOUD = SOUND_EVENTS.register(
            "ground_smash_loud", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("ground_smash_loud"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> EMERGENCY_MEETING = SOUND_EVENTS.register(
            "emergency_meeting", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("emergency_meeting"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> LIGHT_SWITCH = SOUND_EVENTS.register(
            "light_switch", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("light_switch"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> TREASURE_CHEST_LOCK = SOUND_EVENTS.register(
            "treasure_chest_lock", () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("treasure_chest_lock"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> OMEGA_GAUNTLET_IMPACT = SOUND_EVENTS.register(
            "omega_gauntlet_impact",
            () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("omega_gauntlet_impact"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> GOOPY_SLIME = SOUND_EVENTS.register(
            "goopy_slime",
            () -> SoundEvent.createVariableRangeEvent(Curiosities.loc("goopy_slime"))
    );

}
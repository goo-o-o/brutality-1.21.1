package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class BrutalitySounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Brutality.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ASURA_FORM = SOUND_EVENTS.register(
            "asura_form", () -> SoundEvent.createVariableRangeEvent(Brutality.loc("asura_form"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_SPLATTER = SOUND_EVENTS.register(
            "blood_splatter", () -> SoundEvent.createVariableRangeEvent(Brutality.loc("blood_splatter"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> CARTOON_PUNCH_1 = SOUND_EVENTS.register(
            "cartoon_punch_1", () -> SoundEvent.createVariableRangeEvent(Brutality.loc("cartoon_punch_1"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> CARTOON_PUNCH_2 = SOUND_EVENTS.register(
            "cartoon_punch_2", () -> SoundEvent.createVariableRangeEvent(Brutality.loc("cartoon_punch_2"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> CARTOON_PUNCH_3 = SOUND_EVENTS.register(
            "cartoon_punch_3", () -> SoundEvent.createVariableRangeEvent(Brutality.loc("cartoon_punch_3"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BONK = SOUND_EVENTS.register(
            "bonk", () -> SoundEvent.createVariableRangeEvent(Brutality.loc("bonk"))
    );

    public static final List<DeferredHolder<SoundEvent, SoundEvent>> PUNCH_SOUNDS = List.of(
            CARTOON_PUNCH_1,
            CARTOON_PUNCH_2,
            CARTOON_PUNCH_3,
            BONK
    );

    public static DeferredHolder<SoundEvent, SoundEvent> getRandomSound(List<DeferredHolder<SoundEvent, SoundEvent>> sounds, RandomSource random) {
        return sounds.get(random.nextInt(sounds.size()));
    }
}
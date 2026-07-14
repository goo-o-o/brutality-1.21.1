package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BrutalitySounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Brutality.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ASURA_FORM = SOUND_EVENTS.register(
            "asura_form", () -> SoundEvent.createVariableRangeEvent(Brutality.loc( "asura_form"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_SPLATTER = SOUND_EVENTS.register(
            "blood_splatter", () -> SoundEvent.createVariableRangeEvent(Brutality.loc( "blood_splatter"))
    );

}
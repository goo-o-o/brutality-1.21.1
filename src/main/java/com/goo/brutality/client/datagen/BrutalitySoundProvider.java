package com.goo.brutality.client.datagen;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalitySounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BrutalitySoundProvider extends SoundDefinitionsProvider {

    public BrutalitySoundProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    private void sound(DeferredHolder<SoundEvent, SoundEvent> sound) {
        String path = sound.getId().getPath();
        this.add(sound, definition()
                .subtitle("subtitles." + Brutality.MOD_ID + "." + path)
                .with(sound(sound.getId()))
        );
    }

    @Override
    public void registerSounds() {

        sound(BrutalitySounds.ASURA_FORM);
        sound(BrutalitySounds.BLOOD_SPLATTER);
        BrutalitySounds.PUNCH_SOUNDS.forEach(this::sound);

        // example
//        this.add(BrutalitySounds.ENRAGED_AMBIENT, definition()
//                .subtitle("subtitles.brutality.enraged_ambient")
//                .with(
//                    sound(ResourceLocation.fromNamespaceAndPath(BrutalityMod.MOD_ID, "enraged_growl_1"))
//                        .weight(3)
//                        .volume(0.8F),
//                    sound(ResourceLocation.fromNamespaceAndPath(BrutalityMod.MOD_ID, "enraged_growl_2"))
//                        .weight(1)
//                        .volume(0.8F)
//                )
//        );
    }
}
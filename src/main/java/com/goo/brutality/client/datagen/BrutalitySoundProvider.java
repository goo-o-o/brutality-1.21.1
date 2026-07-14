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

    private void sound(DeferredHolder<SoundEvent, SoundEvent> sound, String name) {
        this.add(sound, definition()
                .subtitle("subtitles." + Brutality.MOD_ID + "." + name) // Translation key
                .with(sound(Brutality.loc(name)))
        );
    }

    @Override
    public void registerSounds() {

        sound(BrutalitySounds.ASURA_FORM, "asura_form");
        sound(BrutalitySounds.BLOOD_SPLATTER, "blood_splatter");

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
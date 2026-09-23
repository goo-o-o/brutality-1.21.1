package com.goo.curiosities.client.datagen;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class CuriositiesSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public CuriositiesSoundDefinitionsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    private void sound(DeferredHolder<SoundEvent, SoundEvent> sound) {
        String path = sound.getId().getPath();
        this.add(sound, definition()
                .subtitle("subtitles." + Curiosities.MOD_ID + "." + path)
                .with(sound(sound.getId()))
        );
    }

    private void sound(DeferredHolder<SoundEvent, SoundEvent> sound, SoundDefinition.Sound... sounds) {
        String path = sound.getId().getPath();
        SoundDefinition def = definition().subtitle("subtitles." + Curiosities.MOD_ID + "." + path);

        for (SoundDefinition.Sound s : sounds) {
            def.with(s);
        }

        this.add(sound, def);
    }

    private void multiSound(DeferredHolder<SoundEvent, SoundEvent> sound, int count) {
        String path = sound.getId().getPath();
        SoundDefinition def = definition().subtitle("subtitles." + Curiosities.MOD_ID + "." + path);

        for (int i = 1; i <= count; i++) {
            ResourceLocation fileLoc = Curiosities.loc(path + "_" + i);
            def.with(sound(fileLoc));
        }

        this.add(sound, def);
    }

    @Override
    public void registerSounds() {
        sound(CuriositiesSounds.GROUND_SMASH_LOUD);
        sound(CuriositiesSounds.LIGHT_SWITCH);
        sound(CuriositiesSounds.TREASURE_CHEST_LOCK);
        sound(CuriositiesSounds.EMERGENCY_MEETING);
        sound(CuriositiesSounds.DOUBLE_JUMP);
        sound(CuriositiesSounds.HEAVY_STOMP);
        sound(CuriositiesSounds.TIME_STOP);
        sound(CuriositiesSounds.TIME_RESUME);

        multiSound(CuriositiesSounds.GROUND_SMASH, 2);
        multiSound(CuriositiesSounds.POP, 2);
        multiSound(CuriositiesSounds.ICE_FREEZE, 3);
        multiSound(CuriositiesSounds.BOING, 4);
        multiSound(CuriositiesSounds.GOOPY_SLIME, 3);

        sound(CuriositiesSounds.OMEGA_GAUNTLET_IMPACT,
                sound(Curiosities.loc("cartoon_punch_1")).weight(1),
                sound(Curiosities.loc("cartoon_punch_2")).weight(1),
                sound(Curiosities.loc("cartoon_punch_3")).weight(1),
                sound(Curiosities.loc("bonk")).weight(1)
        );
    }
}
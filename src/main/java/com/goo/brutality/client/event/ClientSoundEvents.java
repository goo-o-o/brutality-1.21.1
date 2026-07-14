package com.goo.brutality.client.event;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalitySounds;
import com.goo.goo_lib.client.sound.UncappedSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class ClientSoundEvents {

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (event.getOriginalSound() instanceof SimpleSoundInstance simpleSoundInstance) {
            if (simpleSoundInstance.getLocation().equals(BrutalitySounds.ASURA_FORM.getId())) {
                event.setSound(UncappedSoundInstance.fromSimpleSoundInstance(simpleSoundInstance));
            }
        }
    }
}

package com.goo.curiosities.client.event;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.goo_lib.client.sound.UncappedSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

import java.util.function.Predicate;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class ClientSoundEvents {

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (event.getOriginalSound() instanceof SimpleSoundInstance simpleSoundInstance) {
            Predicate<ResourceLocation> predicate = res ->
                    res.equals(CuriositiesSounds.HEAVY_STOMP.getId()) ||
                            res.equals(CuriositiesSounds.TIME_STOP.getId()) ||
                            res.equals(CuriositiesSounds.TIME_RESUME.getId());

            if (predicate.test(simpleSoundInstance.getLocation())) {
                event.setSound(UncappedSoundInstance.fromSimpleSoundInstance(simpleSoundInstance));
            }
        }
    }
}

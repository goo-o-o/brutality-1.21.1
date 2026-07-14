package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.networking.BrutalityClientGamePacketListener;
import com.goo.brutality.common.networking.BrutalityServerGamePacketListener;
import com.goo.brutality.common.networking.clientbound.DisplayItemActivationPayload;
import com.goo.brutality.common.networking.serverbound.TriggerRagePayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Brutality.MOD_ID)
public class BrutalityPayloads {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(TriggerRagePayload.TYPE, TriggerRagePayload.STREAM_CODEC, BrutalityServerGamePacketListener::handleTriggerRage);
        registrar.playToClient(DisplayItemActivationPayload.TYPE, DisplayItemActivationPayload.STREAM_CODEC, BrutalityClientGamePacketListener::handleItemActivation);
    }
}

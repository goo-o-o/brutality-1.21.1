package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.networking.serverbound.divine_immortals_ring.OpenDivineImmortalsRingPayload;
import com.goo.brutality.common.networking.serverbound.SetItemStackInCurioSlotPayload;
import com.goo.brutality.common.networking.serverbound.TriggerActiveAbilityPayload;
import com.goo.brutality.common.networking.serverbound.TriggerRagePayload;
import com.goo.brutality.common.networking.serverbound.divine_immortals_ring.ToggleDivineImmortalsRingOverlayPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Brutality.MOD_ID)
public class BrutalityPayloads {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(ToggleDivineImmortalsRingOverlayPayload.TYPE, ToggleDivineImmortalsRingOverlayPayload.STREAM_CODEC, ToggleDivineImmortalsRingOverlayPayload::handle);
        registrar.playToServer(OpenDivineImmortalsRingPayload.TYPE, OpenDivineImmortalsRingPayload.STREAM_CODEC, OpenDivineImmortalsRingPayload::handle);

        registrar.playToServer(SetItemStackInCurioSlotPayload.TYPE, SetItemStackInCurioSlotPayload.STREAM_CODEC, SetItemStackInCurioSlotPayload::handle);
        registrar.playToServer(TriggerRagePayload.TYPE, TriggerRagePayload.STREAM_CODEC, TriggerRagePayload::handle);
        registrar.playToServer(TriggerActiveAbilityPayload.TYPE, TriggerActiveAbilityPayload.STREAM_CODEC, TriggerActiveAbilityPayload::handle);
    }
}

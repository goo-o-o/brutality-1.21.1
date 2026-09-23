package com.goo.curiosities.common.registry;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.networking.serverbound.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class CuriositiesPayloads {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                FootstepPayload.TYPE,
                FootstepPayload.STREAM_CODEC,
                FootstepPayload::handle);

        registrar.playToServer(
                JumpStatePayload.TYPE,
                JumpStatePayload.STREAM_CODEC,
                JumpStatePayload::handle);

        registrar.playToServer(
                SetItemStackInCurioSlotPayload.TYPE,
                SetItemStackInCurioSlotPayload.STREAM_CODEC,
                SetItemStackInCurioSlotPayload::handle);

        registrar.playToServer(
                TriggerActiveAbilityPayload.TYPE,
                TriggerActiveAbilityPayload.STREAM_CODEC,
                TriggerActiveAbilityPayload::handle);

        registrar.playToServer(
                DoubleJumpPayload.TYPE,
                DoubleJumpPayload.STREAM_CODEC,
                DoubleJumpPayload::handle);


    }
}

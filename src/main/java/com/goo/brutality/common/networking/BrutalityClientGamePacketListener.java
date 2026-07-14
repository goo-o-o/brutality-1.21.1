package com.goo.brutality.common.networking;

import com.goo.brutality.common.networking.clientbound.DisplayItemActivationPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class BrutalityClientGamePacketListener {
    public static void handleItemActivation(final DisplayItemActivationPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            mc.gameRenderer.displayItemActivation(payload.stack());
        });
    }
}

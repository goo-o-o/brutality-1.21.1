package com.goo.brutality.common.networking;

import com.goo.brutality.common.networking.serverbound.TriggerRagePayload;
import com.goo.brutality.common.rage.RageHandler;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class BrutalityServerGamePacketListener {

    public static void handleTriggerRage(final TriggerRagePayload packet, final IPayloadContext context) {
        Player player = context.player();
        if (CurioUtil.isWearingCurio(player, BrutalityItems.Curio.Rage.ANGER_MANAGEMENT.value()))
            RageHandler.tryTriggerRage(player, true);
    }
}

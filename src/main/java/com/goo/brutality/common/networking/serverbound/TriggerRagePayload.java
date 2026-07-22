package com.goo.brutality.common.networking.serverbound;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.rage.RageHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record TriggerRagePayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TriggerRagePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Brutality.MOD_ID, "trigger_rage_payload"));

    // StreamCodec.unit passes a pre-constructed instance and processes 0 bytes across the wire
    public static final StreamCodec<FriendlyByteBuf, TriggerRagePayload> STREAM_CODEC =  StreamCodec.unit(new TriggerRagePayload());

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TriggerRagePayload packet, final IPayloadContext context) {
        Player player = context.player();
        RageHandler.tryTriggerRage(player);
    }

}
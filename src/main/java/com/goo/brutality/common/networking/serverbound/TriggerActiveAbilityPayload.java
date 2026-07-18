package com.goo.brutality.common.networking.serverbound;

import com.goo.brutality.common.Brutality;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TriggerActiveAbilityPayload() implements CustomPacketPayload {

    public static final Type<TriggerActiveAbilityPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Brutality.MOD_ID, "trigger_active_ability_payload"));

    // StreamCodec.unit passes a pre-constructed instance and processes 0 bytes across the wire
    public static final StreamCodec<FriendlyByteBuf, TriggerActiveAbilityPayload> STREAM_CODEC =  StreamCodec.unit(new TriggerActiveAbilityPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
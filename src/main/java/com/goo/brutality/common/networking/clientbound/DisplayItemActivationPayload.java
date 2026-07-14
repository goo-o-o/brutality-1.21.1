package com.goo.brutality.common.networking.clientbound;

import com.goo.brutality.common.Brutality;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record DisplayItemActivationPayload(
        ItemStack stack
) implements CustomPacketPayload {
    public static final Type<DisplayItemActivationPayload> TYPE = new Type<>(Brutality.loc("display_item_activation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DisplayItemActivationPayload> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            DisplayItemActivationPayload::stack,
            DisplayItemActivationPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
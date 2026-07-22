package com.goo.brutality.common.networking.serverbound;

import com.goo.brutality.common.Brutality;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

public record SetItemStackInCurioSlotPayload(String identifier, int index,
                                             ItemStack stack) implements CustomPacketPayload {
    public static final Type<SetItemStackInCurioSlotPayload> TYPE =
            new Type<>(Brutality.loc("set_item_stack_in_curio_slot"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetItemStackInCurioSlotPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SetItemStackInCurioSlotPayload::identifier,
            ByteBufCodecs.VAR_INT,
            SetItemStackInCurioSlotPayload::index,
            ItemStack.OPTIONAL_STREAM_CODEC,
            SetItemStackInCurioSlotPayload::stack,
            SetItemStackInCurioSlotPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SetItemStackInCurioSlotPayload payload, IPayloadContext context) {
        if (context.flow().isServerbound()) {
            ServerPlayer player = ((ServerPlayer) context.player());
            CuriosApi.getCuriosInventory(player).flatMap(handler ->
                    handler.getStacksHandler(payload.identifier)).ifPresent(stackHandler ->
                    stackHandler.getStacks().setStackInSlot(payload.index, payload.stack));
        }
    }


}

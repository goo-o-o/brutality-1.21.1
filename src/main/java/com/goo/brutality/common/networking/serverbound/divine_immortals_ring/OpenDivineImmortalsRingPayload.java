package com.goo.brutality.common.networking.serverbound.divine_immortals_ring;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.curio.ring.DivineImmortalsRing;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

/**
 * Payload that enables opening the {@link com.goo.brutality.common.registry.BrutalityItems.Curio#DIVINE_IMMORTALS_RING} by clicking on the slot from within the Creative Inventory
 * @param slotIndex
 */
public record OpenDivineImmortalsRingPayload(int slotIndex) implements CustomPacketPayload {

    public static final Type<OpenDivineImmortalsRingPayload> TYPE =
            new Type<>(Brutality.loc("open_divine_immortals_ring"));

    public static final StreamCodec<ByteBuf, OpenDivineImmortalsRingPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            OpenDivineImmortalsRingPayload::slotIndex,
            OpenDivineImmortalsRingPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final OpenDivineImmortalsRingPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                // verify slot index bounds in active container
                if (payload.slotIndex() >= 0 && payload.slotIndex() < player.containerMenu.slots.size()) {
                    ItemStack stack = player.getInventory().getItem(payload.slotIndex);

                    if (stack.getItem() instanceof DivineImmortalsRing) {
                        DivineImmortalsRing.openRingMenu(player, stack);
                    }
                }
            }
        });
    }

}
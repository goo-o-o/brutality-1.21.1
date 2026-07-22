package com.goo.brutality.common.networking.serverbound.divine_immortals_ring;

import com.goo.brutality.common.Brutality;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Allows the Server to track which players have their {@link com.goo.brutality.common.registry.BrutalityItems.Curio#DIVINE_IMMORTALS_RING} open
 * @param active
 */
public record ToggleDivineImmortalsRingOverlayPayload(boolean active) implements CustomPacketPayload {

    // track players with the overlay active server-side
    public static final Set<UUID> ACTIVE_PLAYERS = new HashSet<>();

    public static final Type<ToggleDivineImmortalsRingOverlayPayload> TYPE =
            new Type<>(Brutality.loc("toggle_divine_immortals_ring_overlay"));

    public static final StreamCodec<ByteBuf, ToggleDivineImmortalsRingOverlayPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ToggleDivineImmortalsRingOverlayPayload::active,
            ToggleDivineImmortalsRingOverlayPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ToggleDivineImmortalsRingOverlayPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                if (payload.active()) {
                    ACTIVE_PLAYERS.add(player.getUUID());
                } else {
                    ACTIVE_PLAYERS.remove(player.getUUID());
                }
            }
        });
    }
}
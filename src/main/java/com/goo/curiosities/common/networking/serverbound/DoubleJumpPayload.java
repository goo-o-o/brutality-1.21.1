package com.goo.curiosities.common.networking.serverbound;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.ServerDoubleJumpHandler;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.item.DoubleJumpCurioItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;
import java.util.UUID;

public record DoubleJumpPayload(Action action, Holder<Item> curioItem) implements CustomPacketPayload {
    public enum Action { START, STOP }

    public static final Type<DoubleJumpPayload> TYPE =
            new Type<>(Curiosities.loc("double_jump"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DoubleJumpPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(i -> Action.values()[i], Action::ordinal),
                    DoubleJumpPayload::action,
                    ByteBufCodecs.holderRegistry(Registries.ITEM),
                    DoubleJumpPayload::curioItem,
                    DoubleJumpPayload::new
            );

    @Override
    public @NotNull Type<DoubleJumpPayload> type() { return TYPE; }

    public static void handle(final DoubleJumpPayload payload, final IPayloadContext context) {
        Player player = context.player();
        UUID uuid = player.getUUID();

        if (payload.action == Action.START) {
            if (payload.curioItem.value() instanceof DoubleJumpCurioItem curioItem) {
                ServerDoubleJumpHandler.ACTIVE_JUMPS.put(uuid, new ServerDoubleJumpHandler.DoubleJumpState(curioItem, 0));
                curioItem.onWearerStartDoubleJump(player);
            }
        } else if (payload.action == Action.STOP) {
            ServerDoubleJumpHandler.DoubleJumpState state = ServerDoubleJumpHandler.ACTIVE_JUMPS.remove(uuid);
            if (state != null)
                state.curio.onWearerEndDoubleJump(player);
        }
    }

}
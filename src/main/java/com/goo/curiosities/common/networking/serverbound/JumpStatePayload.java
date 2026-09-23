package com.goo.curiosities.common.networking.serverbound;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.FootstepCurioItem;
import com.goo.curiosities.common.item.curio.back.NetheriteJetpack;
import com.goo.curiosities.util.FootstepTracker;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public record JumpStatePayload(
        boolean jumping
) implements CustomPacketPayload {
    public static final Type<JumpStatePayload> TYPE =
            new Type<>(Curiosities.loc("jump_state"));

    public static final StreamCodec<RegistryFriendlyByteBuf, JumpStatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            JumpStatePayload::jumping,
            JumpStatePayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(JumpStatePayload payload, IPayloadContext context) {
        if (context.flow().isServerbound()) {
            Player player = context.player();
            NetheriteJetpack.setActive(player, payload.jumping());
        }
    }
}
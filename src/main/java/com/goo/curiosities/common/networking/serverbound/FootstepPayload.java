package com.goo.curiosities.common.networking.serverbound;

import com.goo.curiosities.util.FootstepTracker;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.FootstepCurioItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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

public record FootstepPayload(
        Vector3f position,
        boolean left
) implements CustomPacketPayload {
    public static final Type<FootstepPayload> TYPE =
            new Type<>(Curiosities.loc("footstep"));

    public static final StreamCodec<RegistryFriendlyByteBuf, FootstepPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            FootstepPayload::position,
            ByteBufCodecs.BOOL,
            FootstepPayload::left,
            FootstepPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FootstepPayload payload, IPayloadContext context) {
        if (context.flow().isServerbound()) {
            Player wearer = context.player();
            Map<Item, Long> itemCounts = CuriosApi.getCuriosInventory(wearer)
                    .map(handler -> handler.findCurios(stack -> stack.getItem() instanceof FootstepCurioItem)
                            .stream()
                            .map(slotResult -> slotResult.stack().getItem())
                            .collect(Collectors.groupingBy(item -> item, Collectors.counting())))
                    .orElse(Collections.emptyMap());

            if (itemCounts.isEmpty()) {
                return;
            }

            itemCounts.forEach((item, count) -> {
                if (item instanceof FootstepCurioItem footstepCurioItem) {
                    Vector3f vector3f = payload.position();
                    FootstepTracker.handleFootstep(
                            new Vec3(vector3f.x(), vector3f.y(), vector3f.z()),
                            wearer,
                            footstepCurioItem,
                            payload.left(),
                            count.intValue(),
                            0);
                }
            });
        }
    }
}
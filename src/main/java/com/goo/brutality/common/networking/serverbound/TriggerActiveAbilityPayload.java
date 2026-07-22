package com.goo.brutality.common.networking.serverbound;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.BrutalityCurioItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

public record TriggerActiveAbilityPayload() implements CustomPacketPayload {

    public static final Type<TriggerActiveAbilityPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Brutality.MOD_ID, "trigger_active_ability_payload"));

    // StreamCodec.unit passes a pre-constructed instance and processes 0 bytes across the wire
    public static final StreamCodec<FriendlyByteBuf, TriggerActiveAbilityPayload> STREAM_CODEC = StreamCodec.unit(new TriggerActiveAbilityPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TriggerActiveAbilityPayload packet, final IPayloadContext context) {
        Player player = context.player();
        CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.findCurios(i -> i.getItem() instanceof BrutalityCurioItem).forEach(
                slotResult -> ((BrutalityCurioItem) slotResult.stack().getItem()).onWearerPressActiveAbility(player, slotResult.stack())
        ));
    }

}
package com.goo.curiosities.common.registry;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.attachments.MomentumComboData;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CuriositiesAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Curiosities.MOD_ID);

    public static final Supplier<AttachmentType<Long>> COMBAT_START_TIME = ATTACHMENT_TYPES.register(
            "combat_start_time",
            () -> AttachmentType.builder(() -> -1L)
                    .serialize(Codec.LONG)
                    .build()
    );

    public static final Supplier<AttachmentType<Long>> SPRINT_START_TIME = ATTACHMENT_TYPES.register(
            "sprint_start_time",
            () -> AttachmentType.builder(() -> -1L)
                    .serialize(Codec.LONG)
                    .sync((holder, targetPlayer) -> holder == targetPlayer, ByteBufCodecs.VAR_LONG)
                    .build()
    );

    public static final Supplier<AttachmentType<MomentumComboData>> MOMENTUM_COMBO =
            ATTACHMENT_TYPES.register("momentum_combo", () ->
                    AttachmentType.builder(MomentumComboData::new)
                            .serialize(MomentumComboData.CODEC)
                            .sync((holder, targetPlayer) -> holder == targetPlayer, MomentumComboData.STREAM_CODEC)
                            .copyOnDeath()
                            .build()
            );
}

package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class BrutalityAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Brutality.MOD_ID);

    public static final Supplier<AttachmentType<Float>> RAGE = ATTACHMENT_TYPES.register(
            "rage",
            () -> AttachmentType.builder(() -> 0F)
                    .serialize(Codec.FLOAT)
                    .sync((holder, targetPlayer) -> holder == targetPlayer, ByteBufCodecs.FLOAT)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<Float>> PENDING_DAMAGE = ATTACHMENT_TYPES.register(
            "pending_damage",
            () -> AttachmentType.builder(() -> 0F)
                    .serialize(Codec.FLOAT)
                    .sync((holder, targetPlayer) -> holder == targetPlayer, ByteBufCodecs.FLOAT)
                    .build()
    );


}

package com.goo.brutality.common.tooltip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Builder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
@Builder
public record ItemDescriptionComponent(
        DescriptionType type,
        int lines,
        String item,
        Integer cooldown,
        String keybindIdentifier,
        Supplier<List<Component>> args
) {
    public static final Codec<ItemDescriptionComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            StringRepresentable.fromEnum(DescriptionType::values).fieldOf("type").forGetter(ItemDescriptionComponent::type),
            Codec.INT.fieldOf("lines").forGetter(ItemDescriptionComponent::lines),
            Codec.STRING.fieldOf("item").forGetter(ItemDescriptionComponent::item), // Added to Codec
            Codec.INT.optionalFieldOf("cooldown_seconds", null).forGetter(ItemDescriptionComponent::cooldown),
            Codec.STRING.optionalFieldOf("keybind_identifier", null).forGetter(ItemDescriptionComponent::keybindIdentifier),
            ComponentSerialization.CODEC.listOf().optionalFieldOf("args", List.of()).forGetter(comp -> comp.args().get())
    ).apply(instance, (type, lines, item, cooldown, keybind, args) ->
            // wrap back into supplier
            new ItemDescriptionComponent(type, lines, item, cooldown, keybind, () -> args)));

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemDescriptionComponent> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(DescriptionType.class), ItemDescriptionComponent::type,
            ByteBufCodecs.VAR_INT, ItemDescriptionComponent::lines,
            ByteBufCodecs.STRING_UTF8, ItemDescriptionComponent::item,
            ByteBufCodecs.VAR_INT.apply(ByteBufCodecs::optional), comp -> Optional.ofNullable(comp.cooldown()),
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs::optional), comp -> Optional.ofNullable(comp.keybindIdentifier()),
            ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list()), comp -> comp.args().get(),
            (type, lines, item, cooldown, keybind, args) ->
                    new ItemDescriptionComponent(type, lines, item, cooldown.orElse(null), keybind.orElse(null), () -> args)
    );


}
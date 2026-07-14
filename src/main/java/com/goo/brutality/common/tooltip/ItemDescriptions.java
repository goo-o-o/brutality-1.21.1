package com.goo.brutality.common.tooltip;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.TooltipUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.locale.Language;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record ItemDescriptions(List<ItemDescriptionComponent> descriptions) implements TooltipProvider {

    /**
     * Main Codec for saving to JSON / Datapacks
     */
    public static final Codec<ItemDescriptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemDescriptionComponent.CODEC.listOf().fieldOf("descriptions").forGetter(ItemDescriptions::descriptions)
    ).apply(instance, ItemDescriptions::new));

    /**
     * StreamCodec for networking between Server and Client
     */
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemDescriptions> STREAM_CODEC = StreamCodec.composite(
            ItemDescriptionComponent.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ItemDescriptions::descriptions,
            ItemDescriptions::new
    );

    public ItemDescriptions(List<ItemDescriptionComponent> descriptions) {
        this.descriptions = ImmutableList.copyOf(descriptions);
    }


    public boolean isEmpty() {
        return this.descriptions.isEmpty();
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        if (descriptions().isEmpty()) {
            return;
        }


        for (int j = 0; j < this.descriptions.size(); j++) {
            ItemDescriptionComponent comp = this.descriptions.get(j);
            String componentLower = comp.type().toString().toLowerCase(Locale.ROOT);

            // add header if it's not lore
            if (comp.type() != DescriptionType.LORE) {
                tooltipAdder.accept(Component.translatable("description." + Brutality.MOD_ID + ".type." + componentLower));
            }

            // track the current index in the master arguments array
            int argIndex = 0;
            Object[] masterArgs = comp.args().get().toArray();

            for (int i = 1; i <= comp.lines(); i++) {
                String translationKey = "item." + Brutality.MOD_ID + "." + comp.item() + "." + componentLower + "." + i;

                // 1. fetch the raw template string from the language registry
                String rawFormat = Language.getInstance().getOrDefault(translationKey);
                // 2. count how many formatting specs ("%s", "%d", etc.) exist in this specific line
                int expectedArgsCount = TooltipUtil.countFormatSpecifiers(rawFormat);

                // 3. slice out just the arguments needed for this line
                Object[] lineArgs = new Object[expectedArgsCount];
                for (int k = 0; k < expectedArgsCount; k++) {
                    if (argIndex < masterArgs.length) {
                        lineArgs[k] = masterArgs[argIndex++];
                    } else {
                        lineArgs[k] = ""; // fallback empty string if ran out of arguments
                    }
                }

                tooltipAdder.accept(Component.translatable(translationKey, lineArgs));
            }

            // add cooldown line
            if (comp.cooldownSeconds() != null) {
                Duration duration = Duration.ofSeconds(comp.cooldownSeconds());
                Component durationComp = Component.literal(TooltipUtil.convertToSingularWords(duration)).withStyle(ChatFormatting.DARK_AQUA);
                tooltipAdder.accept(Component.translatable("tooltip." + Brutality.MOD_ID + ".cooldown", durationComp));
            }

            // add keybind
            if (comp.keybindIdentifier() != null) {
                Component keybindComp = Component.translatable(
                        "key." + Brutality.MOD_ID + ".current_keybind",
                        Component.keybind(comp.keybindIdentifier()).withStyle(ChatFormatting.DARK_GRAY)
                );

                tooltipAdder.accept(keybindComp);
            }

            // append space
            if (j < this.descriptions.size() - 1) {
                tooltipAdder.accept(Component.empty());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDescriptions that = (ItemDescriptions) o;
        return Objects.equals(descriptions, that.descriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptions);
    }

    @Override
    public @NotNull String toString() {
        return "ItemDescriptions[descriptions=" + descriptions + "]";
    }

    public static Builder builder(String item) {
        return new Builder(item);
    }


    public static class Builder {
        private final ImmutableList.Builder<ItemDescriptionComponent> descriptions = ImmutableList.builder();
        private final String item;

        private Builder(String item) {
            this.item = item;
        }

        /**
         * Core master chain method. Pack the immutable properties directly away.
         */
        public Builder add(DescriptionType type, int lines, Integer cooldownSeconds, KeyMapping keyMap, @Nullable Supplier<List<Component>> args) {
            this.descriptions.add(new ItemDescriptionComponent(
                    type, lines, item, cooldownSeconds,
                    keyMap != null ? keyMap.getName() : null,
                    args // wraps an already-built list; only defers if callers build args lazily too
            ));
            return this;
        }

        /**
         * Adds a basic descriptive entry block.
         */
        public Builder add(DescriptionType type, int lines) {
            return this.add(type, lines, null, null, List::of);
        }

        /**
         * Adds one with Component Arguments
         */
        public Builder add(DescriptionType type, int lines, Supplier<List<Component>> args) {
            return this.add(type, lines, null, null, args);
        }
        /**
         * Adds a descriptive entry block paired with an ability cooldown.
         */
        public Builder add(DescriptionType type, int lines, int cooldownSeconds) {
            return this.add(type, lines, cooldownSeconds, null, List::of);
        }

        /**
         * Adds a descriptive entry block paired with a keybind indicator.
         */
        public Builder add(DescriptionType type, int lines, KeyMapping keymap) {
            return this.add(type, lines, null, keymap, List::of);
        }

        /**
         * Adds a descriptive entry block paired with a keybind indiciator and cooldown
         */
        public Builder add(DescriptionType type, int lines, int cooldownSeconds, KeyMapping keymap) {
            return this.add(type, lines, cooldownSeconds, keymap, List::of);
        }

        public Builder add(DescriptionType type, int lines, KeyMapping keymap, Supplier<List<Component>> args) {
            return this.add(type, lines, null, keymap, args);
        }

        /**
         * Compiles the compiled data structures into the final component container.
         */
        public ItemDescriptions build() {
            return new ItemDescriptions(this.descriptions.build());
        }
    }

}
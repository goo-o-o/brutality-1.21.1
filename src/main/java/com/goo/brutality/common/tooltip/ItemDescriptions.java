package com.goo.brutality.common.tooltip;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.TooltipUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Builder;
import lombok.Singular;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Builder
public record ItemDescriptions(@Singular List<ItemDescriptionComponent> descriptions) implements TooltipProvider {

    public static final Codec<ItemDescriptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemDescriptionComponent.CODEC.listOf().fieldOf("descriptions").forGetter(ItemDescriptions::descriptions)
    ).apply(instance, ItemDescriptions::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemDescriptions> STREAM_CODEC = StreamCodec.composite(
            ItemDescriptionComponent.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ItemDescriptions::descriptions,
            ItemDescriptions::new
    );

    public boolean isEmpty() {
        return this.descriptions.isEmpty();
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        if (descriptions().isEmpty()) return;

        for (int j = 0; j < this.descriptions.size(); j++) {
            ItemDescriptionComponent comp = this.descriptions.get(j);
            String componentLower = comp.type().toString().toLowerCase(Locale.ROOT);

            if (comp.type() != DescriptionType.LORE) {
                tooltipAdder.accept(Component.translatable("description." + Brutality.MOD_ID + ".type." + componentLower));
            }

            int argIndex = 0;
            Object[] masterArgs = new Object[]{};
            if (comp.args() != null) {
                masterArgs = comp.args().get().toArray();
            }

            for (int i = 1; i <= comp.lines(); i++) {
                String translationKey = "item." + Brutality.MOD_ID + "." + comp.item() + "." + componentLower + "." + i;
                String rawFormat = Language.getInstance().getOrDefault(translationKey);
                int expectedArgsCount = TooltipUtil.countFormatSpecifiers(rawFormat);

                Object[] lineArgs = new Object[expectedArgsCount];
                for (int k = 0; k < expectedArgsCount; k++) {
                    lineArgs[k] = (argIndex < masterArgs.length) ? masterArgs[argIndex++] : "";
                }

                tooltipAdder.accept(Component.translatable(translationKey, lineArgs));
            }

            if (comp.cooldown() != null) {
                Duration duration = Duration.ofSeconds(comp.cooldown());
                Component durationComp = Component.literal(TooltipUtil.convertToSingularWords(duration)).withStyle(ChatFormatting.DARK_AQUA);
                tooltipAdder.accept(Component.translatable("tooltip." + Brutality.MOD_ID + ".cooldown", durationComp));
            }

            if (comp.keybindIdentifier() != null) {
                Component keybindComp = Component.translatable(
                        "key." + Brutality.MOD_ID + ".current_keybind",
                        Component.keybind(comp.keybindIdentifier()).withStyle(ChatFormatting.DARK_GRAY)
                );
                tooltipAdder.accept(keybindComp);
            }

            if (j < this.descriptions.size() - 1) {
                tooltipAdder.accept(Component.empty());
            }
        }
    }

    public static Context forItem(String itemId) {
        return new Context(itemId);
    }

    public static class Context {
        private final String itemId;
        private final List<ItemDescriptionComponent> list = new ArrayList<>();

        public Context(String itemId) {
            this.itemId = itemId;
        }

        // returns a component builder pre-populated with the item ID and type
        public EntryBuilder add(DescriptionType type) {
            return new EntryBuilder(this, itemId, type);
        }

        public ItemDescriptions build() {
            return new ItemDescriptions(this.list);
        }
    }

    public static class EntryBuilder {
        private final Context parent;
        private final ItemDescriptionComponent.ItemDescriptionComponentBuilder builder;

        public EntryBuilder(Context parent, String itemId, DescriptionType type) {
            this.parent = parent;
            this.builder = ItemDescriptionComponent.builder().item(itemId).type(type);
        }

        public EntryBuilder lines(int lines) {
            builder.lines(lines);
            return this;
        }

        public EntryBuilder cooldown(int seconds) {
            builder.cooldown(seconds);
            return this;
        }

        public EntryBuilder keybind(String keybindComponent) {
            builder.keybindIdentifier(keybindComponent);
            return this;
        }

        public EntryBuilder args(Supplier<List<Component>> args) {
            builder.args(args);
            return this;
        }

        public Context pop() {
            parent.list.add(builder.build());
            return parent;
        }
    }
}
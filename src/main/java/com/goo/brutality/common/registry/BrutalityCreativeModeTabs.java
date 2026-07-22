package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Supplier;

public class BrutalityCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Brutality.MOD_ID);

    public static final Supplier<CreativeModeTab> EQUIPMENT = CREATIVE_MODE_TABS.register("equipment", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Brutality.MOD_ID + ".equipment").withStyle(ChatFormatting.DARK_AQUA))
            .icon(() -> new ItemStack(BrutalityItems.Curio.OMNICHROME_RING.value()))
            .displayItems((params, output) -> {
                params.holders().lookup(Registries.ITEM).ifPresent(registry -> {
                    registry.get(BrutalityTags.Items.RAGE_ITEMS).ifPresent(items -> items.forEach(holder -> {
                        if (holder.value() instanceof ICurioItem) {
                            output.accept(holder.value());
                        }
                    }));
                    registry.get(BrutalityTags.Items.MATH_ITEMS).ifPresent(items -> items.forEach(holder -> {
                        if (holder.value() instanceof ICurioItem) {
                            output.accept(holder.value());
                        }
                    }));

                    registry.filterElements(item -> item instanceof ICurioItem)
                            .listElements()
                            .filter(holder -> holder.key().location().getNamespace().equals(Brutality.MOD_ID))
                            .forEach(holder -> {
                                if (!(holder.is(BrutalityTags.Items.RAGE_ITEMS) || holder.is(BrutalityTags.Items.MATH_ITEMS))) {
                                    output.accept(holder.value());
                                }
                            });
                });
            }).build()
    );
}

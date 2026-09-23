package com.goo.curiosities.common.registry;

import com.goo.curiosities.common.Curiosities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Supplier;

public class CuriositiesCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Curiosities.MOD_ID);

    public static final Supplier<CreativeModeTab> EQUIPMENT = CREATIVE_MODE_TABS.register("equipment", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Curiosities.MOD_ID + ".equipment"))
            .icon(() -> new ItemStack(CuriositiesItems.OMNICHROME_RING.value()))
            .displayItems((params, output) -> {
                params.holders().lookup(Registries.ITEM).ifPresent(registry -> {

                    registry.filterElements(item -> item instanceof ICurioItem)
                            .listElements()
                            .filter(holder -> holder.key().location().getNamespace().equals(Curiosities.MOD_ID))
                            .forEach(holder -> {
                                output.accept(holder.value());
                            });
                });
            }).build()
    );
}

package com.goo.curiosities.common.registry;


import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.tooltip.ItemDescriptions;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CuriositiesDataComponents {
    
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =  DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Curiosities.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemDescriptions>> ITEM_DESCRIPTIONS =
            DATA_COMPONENT_TYPES.register("item_descriptions", () -> DataComponentType.<ItemDescriptions>builder()
                    .persistent(ItemDescriptions.CODEC)       // Attach disk serialization
                    .networkSynchronized(ItemDescriptions.STREAM_CODEC) // Attach server-client sync
                    .build());

}
package com.goo.brutality.common.registry;


import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.tooltip.ItemDescriptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BrutalityDataComponents {
    
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =  DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Brutality.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemDescriptions>> ITEM_DESCRIPTIONS =
            DATA_COMPONENT_TYPES.register("item_descriptions", () -> DataComponentType.<ItemDescriptions>builder()
                    .persistent(ItemDescriptions.CODEC)       // Attach disk serialization
                    .networkSynchronized(ItemDescriptions.STREAM_CODEC) // Attach server-client sync
                    .build());

    public static final Supplier<DataComponentType<Float>> RAGE = // for grudge totem mainly
            DATA_COMPONENT_TYPES.register("rage", () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .networkSynchronized(ByteBufCodecs.FLOAT)
                    .build());
}
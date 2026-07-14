package com.goo.brutality.client.event;

import com.goo.brutality.client.datagen.*;
import com.goo.brutality.common.Brutality;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class DatagenEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // SOUNDS ─────────────────────────────────────────────────────────────────────────────

        BrutalitySoundProvider soundProvider = new BrutalitySoundProvider(
                packOutput,
                Brutality.MOD_ID,
                event.getExistingFileHelper()
        );

        generator.addProvider(event.includeServer(), soundProvider);

        // TAGS ─────────────────────────────────────────────────────────────────────────────

        BrutalityBlockTagProvider blockTagProvider = new BrutalityBlockTagProvider(
                packOutput,
                lookupProvider,
                Brutality.MOD_ID,
                event.getExistingFileHelper()
        );
        generator.addProvider(event.includeServer(), blockTagProvider);

        BrutalityItemTagProvider itemTagProvider = new BrutalityItemTagProvider(
                packOutput,
                lookupProvider,
                blockTagProvider.contentsGetter()
        );

        generator.addProvider(event.includeServer(), itemTagProvider);

        // PARTICLE ─────────────────────────────────────────────────────────────────────────────

        BrutalityParticleDescriptionProvider particleDescriptionProvider = new BrutalityParticleDescriptionProvider(
                packOutput, event.getExistingFileHelper()
        );
        generator.addProvider(event.includeServer(), particleDescriptionProvider);

        // MODELS ─────────────────────────────────────────────────────────────────────────────

        BrutalityItemModelProvider itemModelProvider = new BrutalityItemModelProvider(packOutput, Brutality.MOD_ID, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), itemModelProvider);

        // DAMAGE TYPES ─────────────────────────────────────────────────────────────────────────────

        BrutalityDamageTypeProvider typeProvider = new BrutalityDamageTypeProvider(packOutput, event.getLookupProvider());
        generator.addProvider(event.includeServer(), typeProvider);

        // remember to pass the registry from the damage type provider
        BrutalityDamageTypeTagsProvider typeTagsProvider = new BrutalityDamageTypeTagsProvider(packOutput, typeProvider.getRegistryProvider(), Brutality.MOD_ID, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), typeTagsProvider);

        // CURIO ─────────────────────────────────────────────────────────────────────────────

        BrutalityCurioDataProvider curioDataProvider = new BrutalityCurioDataProvider(Brutality.MOD_ID, packOutput, event.getExistingFileHelper(), lookupProvider);
        generator.addProvider(event.includeServer(), curioDataProvider);
    }
}

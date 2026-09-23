package com.goo.curiosities.client.event;

import com.goo.curiosities.client.datagen.*;
import com.goo.curiosities.common.Curiosities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class DatagenEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeServer(),
                new CuriositiesGlobalLootModifierProvider(packOutput, lookupProvider, Curiosities.MOD_ID)
        );
        // SOUNDS ─────────────────────────────────────────────────────────────────────────────

        CuriositiesRecipeProvider recipeProvider = new CuriositiesRecipeProvider(
                packOutput,
                lookupProvider
        );

        generator.addProvider(event.includeServer(), recipeProvider);

        CuriositiesSoundDefinitionsProvider soundProvider = new CuriositiesSoundDefinitionsProvider(
                packOutput,
                Curiosities.MOD_ID,
                event.getExistingFileHelper()
        );

        generator.addProvider(event.includeServer(), soundProvider);

        // TAGS ─────────────────────────────────────────────────────────────────────────────

        CuriositiesBlockTagProvider blockTagProvider = new CuriositiesBlockTagProvider(
                packOutput,
                lookupProvider,
                Curiosities.MOD_ID,
                event.getExistingFileHelper()
        );
        generator.addProvider(event.includeServer(), blockTagProvider);

        CuriositiesItemTagsProvider itemTagProvider = new CuriositiesItemTagsProvider(
                packOutput,
                lookupProvider,
                blockTagProvider.contentsGetter()
        );

        generator.addProvider(event.includeServer(), itemTagProvider);

        // PARTICLE ─────────────────────────────────────────────────────────────────────────────

        CuriositiesParticleDescriptionProvider particleDescriptionProvider = new CuriositiesParticleDescriptionProvider(
                packOutput, event.getExistingFileHelper()
        );
        generator.addProvider(event.includeServer(), particleDescriptionProvider);

        // MODELS ─────────────────────────────────────────────────────────────────────────────

        CuriositiesItemModelProvider itemModelProvider = new CuriositiesItemModelProvider(packOutput, Curiosities.MOD_ID, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), itemModelProvider);

        CuriositiesCurioDataProvider curioDataProvider = new CuriositiesCurioDataProvider(Curiosities.MOD_ID, packOutput, event.getExistingFileHelper(), lookupProvider);
        generator.addProvider(event.includeServer(), curioDataProvider);
    }
}

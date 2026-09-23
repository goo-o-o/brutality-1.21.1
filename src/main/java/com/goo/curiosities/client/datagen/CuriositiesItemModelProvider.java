package com.goo.curiosities.client.datagen;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CuriositiesItemModelProvider extends ItemModelProvider {
    private final Path outputFolder;
    private CachedOutput cache;

    public CuriositiesItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
        this.outputFolder = output.getOutputFolder();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.cache = cache;
        return super.run(cache);
    }

    @Override
    protected void registerModels() {

        // ── Register Curios ───────────────────────────────────────────────────────
        BuiltInRegistries.ITEM.entrySet().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(Curiosities.MOD_ID))
                .forEach(entry -> {
                    Item item = entry.getValue();
                    String name = entry.getKey().location().getPath();

                    if (item instanceof ICurioItem) {
                        ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(Curiosities.MOD_ID, "textures/item/curio/" + name + ".png");

                        if (existingFileHelper.exists(textureLocation, PackType.CLIENT_RESOURCES)) {
                            curio(item);
                        } else {
                            Curiosities.LOGGER.warn("Missing curio texture asset at: {}. Skipping model generation.", textureLocation);
                        }
                    }
                });


        curio(CuriositiesItems.RESPLENDENT_FEATHER.value(), 2, false, 1);
        curio(CuriositiesItems.NANO_MACHINES.value(), 2, false, 2);
        curio(CuriositiesItems.PLATED_STEELCAPS.value(), 2);
        curio(CuriositiesItems.MOVEMENT_GODS_TRACERS.value(), 1, false, 2);
        curio(CuriositiesItems.BROKEN_CLOCK.value(), 1, true, 1);
        curio(CuriositiesItems.FIERY_ANKLET.value(), 1, false, 1);
    }


    private static Consumer<ItemModelBuilder> createHandScaledTransforms(float guiScale) {
        return builder -> builder.transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, -90, 25)
                .translation(1.13F, 3.2F, 1.13F)
                .scale(guiScale * 0.68F, guiScale * 0.68F, 0.68F)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .translation(0, 3, 1)
                .scale(guiScale * 0.55F, guiScale * 0.55F, 0.55F)
                .end();
    }

    private static Consumer<ItemModelBuilder> createGuiScaledTransforms(float guiScale) {
        return builder -> builder.transforms().transform(ItemDisplayContext.GUI)
                .scale(guiScale, guiScale, 1.0F)
                .end();
    }

    private static Consumer<ItemModelBuilder> createGroundScaledTransforms(float guiScale, boolean depth) {
        return builder -> builder.transforms().transform(ItemDisplayContext.GROUND)
                .translation(0.0F, 2 - (guiScale * 2), 0.0F)
                .scale(guiScale / 2, guiScale / 2, depth ? guiScale : 1.0F)
                .end();
    }

    private static Consumer<ItemModelBuilder> createFixedScaledTransforms(float scale, boolean depth) {
        return builder -> builder.transforms().transform(ItemDisplayContext.FIXED)
                .rotation(0, 180, 0)
                .scale(scale, scale, depth ? scale : 1)
                .end();
    }

    // ── Curios Helpers ────────────────────────────────────────────────────────

    private void curio(Item item) {
        curio(item, 0, false, 1);
    }

    private void curio(Item item, float guiScale) {
        curio(item, 0, false, guiScale);
    }

    private void curio(Item item, int frameTime, boolean interpolate, float guiScale) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        String path = "item/curio";
        ItemModelBuilder guiModel = getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", path + "/" + name)
                .guiLight(BlockModel.GuiLight.FRONT);
        createGuiScaledTransforms(guiScale).accept(guiModel);
        createFixedScaledTransforms(guiScale, false).accept(guiModel);
        createGroundScaledTransforms(guiScale, false).accept(guiModel);
        createHandScaledTransforms(guiScale).accept(guiModel);

        if (frameTime > 0) {
            Path targetPath = outputFolder
                    .resolve("assets")
                    .resolve(this.modid)
                    .resolve("textures")
                    .resolve("item")
                    .resolve("curio")
                    .resolve(name + ".png.mcmeta");

            JsonObject animationJson = new JsonObject();
            JsonObject properties = new JsonObject();
            properties.addProperty("frametime", frameTime);
            properties.addProperty("interpolate", interpolate);
            animationJson.add("animation", properties);

            DataProvider.saveStable(cache, animationJson, targetPath);
        }
    }
}
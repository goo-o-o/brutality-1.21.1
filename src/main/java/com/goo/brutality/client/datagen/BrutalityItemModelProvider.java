package com.goo.brutality.client.datagen;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityItems;
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
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BrutalityItemModelProvider extends ItemModelProvider {
    private final Path outputFolder;
    private CachedOutput cache;
    public BrutalityItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
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
        separateTransformsWeapon(BrutalityItems.Weapon.LightGreatsword.PLINKO_BLADE.value(),
                4, -28,
                1
        );
        separateTransformsWeapon(BrutalityItems.Weapon.LightGreatsword.BLADE_OF_THE_RUINED_KING.value(),
                4, -28,
                2
        );


        // ── Register Curios ───────────────────────────────────────────────────────
        BuiltInRegistries.ITEM.entrySet().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(Brutality.MOD_ID))
                .forEach(entry -> {
                    Item item = entry.getValue();
                    String name = entry.getKey().location().getPath();

                    if (item instanceof ICurioItem) {
                        ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(Brutality.MOD_ID, "textures/item/curio/" + name + ".png");

                        if (existingFileHelper.exists(textureLocation, PackType.CLIENT_RESOURCES)) {
                            curio(item);
                        } else {
                            Brutality.LOGGER.warn("Missing curio texture asset at: {}. Skipping model generation.", textureLocation);
                        }
                    }
                });

        curio(BrutalityItems.Curio.Math.COSINE.value(), 1, false);
        curio(BrutalityItems.Curio.Math.SINE.value(), 1, false);

        curio(BrutalityItems.Curio.Rage.DEMON_BEADS.value(), 1, false);
        curio(BrutalityItems.Curio.Rage.RAMPAGE_CLOCK.value(), 1, false);
        curio(BrutalityItems.Curio.BLOOD_STONE.value(), 2, false);
        curio(BrutalityItems.Curio.Rage.BOILING_BLOOD.value(), 2, false);
        curio(BrutalityItems.Curio.Rage.HEART_OF_DARKNESS.value(), 2, false);
        curio(BrutalityItems.Curio.Rage.WIRELESS_CONTROLLER.value(), 2, false);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * Creates a consumer that applies transform properties to an item model builder.
     * The transformations are adjusted based on the provided grip pixel offset and scale,
     * using predefined settings for different item display contexts.
     *
     * @param handScale       the scale of the texture in comparison to 16x16
     * @param gripPixelOffset the number of pixels offset to adjust the grip alignment. Used to calculate
     *                        translation and scaling values for the item model transformations.
     * @return a {@code Consumer<ItemModelBuilder>} that applies the appropriate transform settings
     * for third-person and first-person perspectives in both hands.
     */
    private static Consumer<ItemModelBuilder> createHandScaledTransforms(float handScale, int gripPixelOffset) {
        final float delta = gripPixelOffset / handScale;
        final float firstPersonYBoost = -handScale; // tune to taste, see note below

        return builder -> {
            applyGrip(builder, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, -90, 55, 0f, 4.0f, 0.5f, 0.85f, handScale, delta, 55, 0f);
            applyGrip(builder, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, 90, -55, 0f, 4.0f, 0.5f, 0.85f, handScale, delta, 55, 0f);
            applyGrip(builder, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, -90, 25, 1.13f, 3.2f, 1.13f, 0.68f, handScale, delta, 25, firstPersonYBoost);
            applyGrip(builder, ItemDisplayContext.FIRST_PERSON_LEFT_HAND, 90, -25, 1.13f, 3.2f, 1.13f, 0.68f, handScale, delta, 25, firstPersonYBoost);
        };
    }


    private static void applyGrip(ItemModelBuilder builder, ItemDisplayContext ctx,
                                  float rotY, float rotZ, float baseTx, float baseTy, float baseTz,
                                  float baseScale, float k, float delta,
                                  float canonicalThetaZ, float extraYBoost) {

        // Always use the POSITIVE (right-hand) angle here, for both hands —
        // the left-hand mirror is already baked into vanilla's rendering.
        double angle = Math.toRadians(45.0 + canonicalThetaZ);
        float compTy = (float) ((1 - k) * delta * Math.sin(angle)) + extraYBoost;
        float compTz = (float) ((1 - k) * delta * Math.cos(angle));

        builder.transforms().transform(ctx)
                .rotation(0, rotY, rotZ)
                .translation(baseTx, baseTy + compTy, baseTz + compTz)
                .scale(baseScale * k, baseScale * k, baseScale * k / 2)
                .end();
    }

    private void separateTransformsWeapon(Item item, float handScale, int gripPixelOffset, float guiScale) {
        separateTransforms(item, "item/weapon", handScale, gripPixelOffset, guiScale);
    }

    private static Consumer<ItemModelBuilder> createGuiScaledTransforms(float guiScale) {
        return builder -> {
            builder.transforms().transform(ItemDisplayContext.GUI)
                    .translation((guiScale - 1) * 8F, (guiScale - 1) * 8F, 0.0F)
                    .scale(guiScale, guiScale, 1.0F)
                    .end();
        };
    }

    private static Consumer<ItemModelBuilder> createGroundScaledTransforms(float guiScale) {
        return builder -> {
            builder.transforms().transform(ItemDisplayContext.GROUND)
                    .translation(0.0F, guiScale * 2F, 0.0F)
                    .scale(guiScale / 2, guiScale / 2, 1.0F)
                    .end();
        };
    }

    private static Consumer<ItemModelBuilder> createFixedScaledTransforms(float scale) {
        return builder -> {
            builder.transforms().transform(ItemDisplayContext.FIXED)
                    .rotation(0.0F, 0.0F, 90.0F)
                    .translation(-scale * 4F, scale * 4F, 0.0F)
                    .scale(scale, scale, scale)
                    .end();
        };
    }

    private void separateTransforms(Item item, String path, float handScale, int gripPixelOffset, float guiScale) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();

        ItemModelBuilder handModel = new ItemModelBuilder(modLoc(name + "_handheld"), existingFileHelper)
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", path + "/" + name + "_handheld");
        createHandScaledTransforms(handScale, gripPixelOffset).accept(handModel);

        var rootBuilder = getBuilder(name)
                .guiLight(BlockModel.GuiLight.FRONT)
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(handModel);

        ItemModelBuilder guiModel = new ItemModelBuilder(modLoc(name + "_gui"), existingFileHelper)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", path + "/" + name + "_gui")
                .guiLight(BlockModel.GuiLight.FRONT);
        createGuiScaledTransforms(guiScale).accept(guiModel);

        ItemModelBuilder groundModel = new ItemModelBuilder(modLoc(name + "_ground"), existingFileHelper)
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", path + "/" + name + "_gui")
                .guiLight(BlockModel.GuiLight.FRONT);
        createGroundScaledTransforms(guiScale).accept(groundModel);

        ItemModelBuilder fixedModel = new ItemModelBuilder(modLoc(name + "_fixed"), existingFileHelper)
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", path + "/" + name + "_gui")
                .guiLight(BlockModel.GuiLight.FRONT);
        createFixedScaledTransforms(guiScale).accept(fixedModel);

        // Bind perspectives explicitly to root separate_transforms loader map
        rootBuilder.perspective(ItemDisplayContext.GUI, guiModel)
                .perspective(ItemDisplayContext.GROUND, groundModel)
                .perspective(ItemDisplayContext.FIXED, fixedModel);

        rootBuilder.end();
    }

    private void curio(Item item) {
        curio(item, 0, false);
    }

    private void curio(Item item, int frameTime, boolean interpolate) {

        String name = BuiltInRegistries.ITEM.getKey(item).getPath();

        withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", "item/curio/" + name)
                .guiLight(BlockModel.GuiLight.FRONT);

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

            // 4. Force Datagen to register and cache the output properly
            DataProvider.saveStable(cache, animationJson, targetPath);
        }
    }
}

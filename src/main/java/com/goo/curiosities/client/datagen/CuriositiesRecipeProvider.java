package com.goo.curiosities.client.datagen;


import com.goo.curiosities.common.registry.CuriositiesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.concurrent.CompletableFuture;

public class CuriositiesRecipeProvider extends RecipeProvider implements IConditionBuilder {
    RecipeOutput recipeOutput;

    public CuriositiesRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        this.recipeOutput = recipeOutput;

        unlockedBy(Items.SHIELD, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.ANTI_CHEAT.value())
                .pattern("R R")
                .pattern("BSB")
                .pattern(" R ")
                .define('S', Items.SHIELD)
                .define('R', Items.REDSTONE_BLOCK)
                .define('B', Items.BLACK_CONCRETE)
        );


        unlockedBy(CuriositiesItems.EMPTY_ANKLET.value(), ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.BASKETBALL_ANKLET.value())
                .pattern("OJO")
                .pattern("BEB")
                .pattern("OJO")
                .define('O', Items.ORANGE_WOOL)
                .define('B', Items.BLACK_WOOL)
                .define('J', DataComponentIngredient.of(false, PotionContents.createItemStack(Items.POTION, Potions.LEAPING))) // invis pot
                .define('E', CuriositiesItems.EMPTY_ANKLET.value())
        );

        unlockedBy(CuriositiesItems.EMPTY_ANKLET.value(), ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.BLAZE_ANKLET.value())
                .pattern("PRP")
                .pattern("PEP")
                .pattern("PRP")
                .define('P', Items.BLAZE_POWDER)
                .define('R', Items.BLAZE_ROD)
                .define('E', CuriositiesItems.EMPTY_ANKLET.value())
        );

        unlockedBy(Items.WHITE_WOOL, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.BOOTS_OF_SWIFTNESS.value())
                .pattern(" WW")
                .pattern("WBF")
                .pattern("SSS")
                .define('W', Items.WHITE_WOOL)
                .define('B', Items.LEATHER_BOOTS)
                .define('F', Items.FEATHER)
                .define('S', Items.SUGAR)
        );

        unlockedBy(Items.CLOCK, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.BROKEN_CLOCK.value())
                .pattern(" T ")
                .pattern("ECE")
                .pattern(" T ")
                .define('T', Items.GHAST_TEAR)
                .define('E', Items.ENDER_EYE)
                .define('C', Items.CLOCK)
        );

        unlockedBy(Items.GRAY_WOOL, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.CENSORSHIP_CLOAK.value())
                .pattern("GLG")
                .pattern("LGL")
                .pattern("GLG")
                .define('G', Items.GRAY_WOOL)
                .define('L', Items.LIGHT_GRAY_WOOL)
        );



        unlockedBy(Items.LEATHER_CHESTPLATE, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.CLOAK_OF_INVISIBILITY.value())
                .pattern(" N ")
                .pattern("PCP")
                .pattern(" P ")
                .define('N', Items.NETHER_STAR)
                .define('P', DataComponentIngredient.of(false, PotionContents.createItemStack(Items.POTION, Potions.INVISIBILITY))) // invis pot
                .define('C', Items.LEATHER_CHESTPLATE)
        );

        unlockedBy(Items.ICE, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.CLOAK_OF_TRUE_ICE.value())
                .pattern("BPB")
                .pattern("PCP")
                .pattern("BPB")
                .define('B', Items.BLUE_ICE)
                .define('P', Items.PACKED_ICE)
                .define('C', Items.LEATHER_CHESTPLATE)
        );


        unlockedBy(Items.CHEST, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.PLUNDER_CHEST.value())
                .pattern(" G ")
                .pattern("HCH")
                .pattern(" M ")
                .define('M', Items.MILK_BUCKET)
                .define('H', Items.HONEYCOMB)
                .define('G', Items.GOLD_BLOCK)
                .define('C', Items.CHEST)
        );

        unlockedBy(Items.RABBIT_FOOT, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.LUCKY_INSOLES.value())
                .pattern(" R ")
                .pattern("L L")
                .pattern("   ")
                .define('R', Items.RABBIT_FOOT)
                .define('L', Items.LIME_WOOL)
        );


        unlockedBy(Items.ELYTRA, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.NETHERITE_JETPACK.value())
                .pattern("A A")
                .pattern("NEN")
                .pattern("G G")
                .define('A', Items.NETHERITE_SCRAP)
                .define('N', Items.NETHERITE_INGOT)
                .define('E', Items.CHAIN)
                .define('G', Items.MAGMA_BLOCK)
        );

        unlockedBy(Items.ELYTRA, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.MICRO_THRUSTERS.value())
                .pattern(" A ")
                .pattern(" E ")
                .pattern(" G ")
                .define('A', Items.IRON_BARS)
                .define('E', Items.IRON_BLOCK)
                .define('G', Items.MAGMA_BLOCK)
        );

        unlockedBy(Items.ELYTRA, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CuriositiesItems.MOVEMENT_GODS_TRACERS.value())
                .pattern("A A")
                .pattern("NEN")
                .pattern("G G")
                .define('A', Items.NETHERITE_SCRAP)
                .define('N', Items.NETHERITE_INGOT)
                .define('E', Items.ELYTRA)
                .define('G', Items.MAGMA_BLOCK)
        );

        /*
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CuriositiesItems.SOME_ITEM.get())
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.GOLD_NUGGET)
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        */
    }

    private void unlockedBy(ItemLike item, ShapedRecipeBuilder builder) {
        builder.unlockedBy(item.asItem().toString(), has(item));
        builder.save(recipeOutput);
    }
}
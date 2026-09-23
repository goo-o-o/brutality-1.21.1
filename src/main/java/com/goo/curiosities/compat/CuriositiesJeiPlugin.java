package com.goo.curiosities.compat;


import com.goo.curiosities.client.datagen.CuriositiesGlobalLootModifierProvider;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class CuriositiesJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Curiosities.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        CuriositiesGlobalLootModifierProvider.LOOT_ENTRIES.forEach(entry -> {
            ResourceLocation loc = entry.lootTable();

            String friendlyName = java.util.Arrays.stream(loc.getPath().split("/"))
                    .reduce((first, second) -> second) // gets the last part: "end_city_treasure"
                    .orElse(loc.getPath())
                    .replace('_', ' ');

            registration.addItemStackInfo(
                    new ItemStack(entry.itemHolder().value()),
                    Component.translatable("jei.curiosities.loot_info", friendlyName)
            );
        });


        registration.addItemStackInfo(
                new ItemStack(CuriositiesItems.AMPHIBIAN_BOOTS.value()),
                Component.translatable("item." + Curiosities.MOD_ID + ".amphibian_boots.info")
        );
        registration.addItemStackInfo(
                new ItemStack(CuriositiesItems.ANKLE_MONITOR.value()),
                Component.translatable("item." + Curiosities.MOD_ID + ".ankle_monitor.info")
        );
        registration.addItemStackInfo(
                new ItemStack(CuriositiesItems.BLIZZARD_IN_A_BOTTLE.value()),
                Component.translatable("item." + Curiosities.MOD_ID + ".blizzard_in_a_bottle.info")
        );
        registration.addItemStackInfo(
                new ItemStack(CuriositiesItems.CLOUD_IN_A_BOTTLE.value()),
                Component.translatable("item." + Curiosities.MOD_ID + ".cloud_in_a_bottle.info")
        );
    }
}
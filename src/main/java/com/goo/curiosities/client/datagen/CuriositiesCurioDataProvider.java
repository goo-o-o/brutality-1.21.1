package com.goo.curiosities.client.datagen;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class CuriositiesCurioDataProvider extends CuriosDataProvider {
    public CuriositiesCurioDataProvider(String modId, PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(modId, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        createSlot(CuriositiesTags.Items.ANKLET.location().getPath())
                .addCosmetic(true)
                .size(1)
                .icon(Curiosities.loc("slot/empty_anklet_slot"))
                .order(510);

        createSlot(CuriositiesTags.Items.HEART.location().getPath())
                .addCosmetic(true)
                .size(1)
                .icon(Curiosities.loc("slot/empty_heart_slot"))
                .order(510);

        createSlot(CuriositiesTags.Items.FEET.location().getPath())
                .addCosmetic(true)
                .size(2)
                .icon(Curiosities.loc("slot/empty_feet_slot"))
                .order(510);

        createEntities("player")
                .addPlayer()
                .addSlots(
                        CuriositiesTags.Items.HEART.location().getPath(),
                        CuriositiesTags.Items.ANKLET.location().getPath(),
                        CuriositiesTags.Items.FEET.location().getPath()
                );
    }
}

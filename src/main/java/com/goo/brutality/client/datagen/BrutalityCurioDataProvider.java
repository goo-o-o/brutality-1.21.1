package com.goo.brutality.client.datagen;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class BrutalityCurioDataProvider extends CuriosDataProvider {
    public BrutalityCurioDataProvider(String modId, PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(modId, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        createSlot(BrutalityTags.Items.ANKLET.location().getPath())
                .addCosmetic(true)
                .size(1)
                .icon(Brutality.loc("slot/empty_anklet_slot"))
                .order(510);

        createSlot(BrutalityTags.Items.HEART.location().getPath())
                .addCosmetic(true)
                .size(1)
                .icon(Brutality.loc("slot/empty_heart_slot"))
                .order(510);

        createSlot(BrutalityTags.Items.FEET.location().getPath())
                .addCosmetic(true)
                .size(2)
                .icon(Brutality.loc("slot/empty_feet_slot"))
                .order(510);

        createEntities("player")
                .addPlayer()
                .addSlots(
                        BrutalityTags.Items.HEART.location().getPath(),
                        BrutalityTags.Items.ANKLET.location().getPath(),
                        BrutalityTags.Items.FEET.location().getPath()
                );
    }
}

package com.goo.brutality.client.datagen;

import com.goo.brutality.common.registry.BrutalityDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BrutalityDamageTypeTagsProvider extends DamageTypeTagsProvider {


    public BrutalityDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(BrutalityDamageTypes.ASURA_FORM);
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(BrutalityDamageTypes.ASURA_FORM);
        this.tag(DamageTypeTags.BYPASSES_COOLDOWN).add(BrutalityDamageTypes.ASURA_FORM);
        this.tag(DamageTypeTags.BYPASSES_EFFECTS).add(BrutalityDamageTypes.ASURA_FORM);
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(BrutalityDamageTypes.ASURA_FORM);
        this.tag(DamageTypeTags.BYPASSES_RESISTANCE).add(BrutalityDamageTypes.ASURA_FORM);
        this.tag(DamageTypeTags.BYPASSES_SHIELD).add(BrutalityDamageTypes.ASURA_FORM);
        this.tag(DamageTypeTags.NO_KNOCKBACK).add(BrutalityDamageTypes.ASURA_FORM);
    }
}
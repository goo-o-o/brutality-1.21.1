package com.goo.brutality.client.datagen;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BrutalityDamageTypeProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, context ->
                    context.register(
                    BrutalityDamageTypes.ASURA_FORM,
                    new DamageType(
                            "asura_burst",
                            DamageScaling.NEVER,
                            0.1F
                    )
            ));

    public BrutalityDamageTypeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", Brutality.MOD_ID));
    }

}
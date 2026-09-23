package com.goo.curiosities.client.datagen;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.Curiosities;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class CuriositiesParticleDescriptionProvider extends ParticleDescriptionProvider {


    /**
     * Creates an instance of the data provider.
     *
     * @param output     the expected root directory the data generator outputs to
     * @param fileHelper the helper used to validate a texture's existence
     */
    public CuriositiesParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(CuriositiesParticles.MOLTEN_FOOTPRINT.get(), Curiosities.loc("generic_dot"));
        sprite(CuriositiesParticles.OMEGA.get(), Curiosities.loc("omega"));
        sprite(CuriositiesParticles.SEISMIC_SHOCKWAVE.get(), Curiosities.loc("seismic_shockwave"));
        sprite(CuriositiesParticles.FIRE_WAVE.get(), Curiosities.loc("generic_wave"));
        spriteSet(CuriositiesParticles.SANDSTORM_SMOKE.get(), Curiosities.loc("big_smoke_white"), 12, false);
        spriteSet(CuriositiesParticles.SANDSTORM_DUST.get(), ResourceLocation.withDefaultNamespace("generic"), 8, false);
        spriteSet(CuriositiesParticles.ONOMATOPOEIA.get(), Curiosities.loc("onomatopoeia"), 9, false);
    }
}
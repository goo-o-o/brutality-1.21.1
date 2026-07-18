package com.goo.brutality.client.datagen;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.Brutality;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class BrutalityParticleDescriptionProvider extends ParticleDescriptionProvider {


    /**
     * Creates an instance of the data provider.
     *
     * @param output     the expected root directory the data generator outputs to
     * @param fileHelper the helper used to validate a texture's existence
     */
    public BrutalityParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(BrutalityParticles.POKER_CHIP_BLACK.get(), Brutality.loc("poker_chip/black"), 10, false);
        spriteSet(BrutalityParticles.POKER_CHIP_BLUE.get(), Brutality.loc("poker_chip/blue"), 10, false);
        spriteSet(BrutalityParticles.POKER_CHIP_GREEN.get(), Brutality.loc("poker_chip/green"), 10, false);
        spriteSet(BrutalityParticles.POKER_CHIP_RED.get(), Brutality.loc("poker_chip/red"), 10, false);
        spriteSet(BrutalityParticles.POKER_CHIP_RED_INVERSE.get(), Brutality.loc("poker_chip/red_inverse"), 10, false);
        spriteSet(BrutalityParticles.POKER_CHIP_YELLOW.get(), Brutality.loc("poker_chip/yellow"), 10, false);
        
        spriteSet(BrutalityParticles.STYGIAN_SOUL.get(), Brutality.loc("stygian/stygian_soul"), 11, false);
        spriteSet(BrutalityParticles.ENRAGED.get(), Brutality.loc("enraged/enraged"), 3, false);
        spriteSet(BrutalityParticles.ASURA.get(), ResourceLocation.withDefaultNamespace("generic"), 7, true);
        spriteSet(BrutalityParticles.TRANQUILITY.get(), Brutality.loc("tranquility/tranquility"), 3, false);
        spriteSet(BrutalityParticles.BLOODSPLOSION.get(), Brutality.loc("bloodsplosion/bloodsplosion"), 7, false);
        spriteSet(BrutalityParticles.HEXING_CIRCLE.get(), Brutality.loc("hexing_circle/hexing_circle"), 3, false);
        spriteSet(BrutalityParticles.MATH.get(), Brutality.loc("math/math"), 31, false);
        sprite(BrutalityParticles.OMEGA.get(), Brutality.loc("math/math_15"));
        spriteSet(BrutalityParticles.ONOMATOPOEIA.get(), Brutality.loc("onomatopoeia/onomatopoeia"), 9, false);
        sprite(BrutalityParticles.BLACK_HOLE.get(), Brutality.loc("circle"));
    }
}
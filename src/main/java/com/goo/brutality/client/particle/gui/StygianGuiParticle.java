package com.goo.brutality.client.particle.gui;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.goo_lib.client.particle.gui.GuiParticle;
import net.minecraft.util.RandomSource;

public class StygianGuiParticle extends GuiParticle {
    public StygianGuiParticle(float x, float y, int z, float scale, RandomSource random) {
        super(BrutalityParticles.STYGIAN_SOUL.get(), x, y, z, 0, 0, Math.max(1, 30 + (random.nextInt(2) - 1)), scale, 1F, 1F, 1F, 1F);
        this.ay = -0.05F;
    }

}

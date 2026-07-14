package com.goo.brutality.client.particle.gui;

import com.goo.goo_lib.client.particle.gui.GuiParticle;
import com.goo.goo_lib.client.particle.gui.GuiParticleSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;

public class BubbleGuiParticle extends GuiParticle {
    public BubbleGuiParticle(float x, float y, int z, float vx, float vy, float scale, RandomSource random) {
        super(ParticleTypes.BUBBLE, x, y, z, vx, vy, random.nextInt(60, 160), scale, 1F, 1F, 1F, 1F);
    }

    @Override
    public boolean tick() {
        if (super.tick()) {
            return true;
        } else {
            GuiParticle pop = new GuiParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0, 0, 4, scale, 1.0F, 1.0F, 1.0F, 1.0F);
            GuiParticleSystem.getInstance().add(pop);
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
            }
            return false;
        }
    }
}

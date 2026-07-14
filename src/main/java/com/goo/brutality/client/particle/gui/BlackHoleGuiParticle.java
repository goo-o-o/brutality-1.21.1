package com.goo.brutality.client.particle.gui;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.client.tooltip.render_pipelines.VoidTouchedPipeline;
import com.goo.goo_lib.client.particle.gui.GuiParticle;
import com.goo.goo_lib.client.particle.gui.GuiParticleSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class BlackHoleGuiParticle extends GuiParticle {
    private final float origX;
    private final float origY;
    private final float origScale;
    private float targetScale;

    public BlackHoleGuiParticle(float x, float y, int z, int lifetime, float vx, float vy, float scale) {
        super(BrutalityParticles.BLACK_HOLE.get(), x, y, z, vx, vy, lifetime, scale, 0F, 0F, 0F, 1F);
        this.origX = x;
        this.origY = y;
        this.origScale = scale;
        this.targetScale = scale;
    }


    @Override
    public boolean tick() {
        boolean superclass = super.tick();

        if (VoidTouchedPipeline.currentWidth > 0 && VoidTouchedPipeline.currentHeight > 0) {
            float targetX = VoidTouchedPipeline.centerX;
            float targetY = VoidTouchedPipeline.centerY;


            float dx = targetX - x;
            float dy = targetY - y;
            float distance = Mth.sqrt(dx * dx + dy * dy);

            // Calculate ratio based on original position distance to center
            float origDx = targetX - origX;
            float origDy = targetY - origY;
            float origDist = Mth.sqrt(origDx * origDx + origDy * origDy);
            float ratio = origDist > 0 ? distance / origDist : 1.0f;
            ratio = Mth.clamp(ratio, 0.2f, 1.0f);

            // Scale based on ratio (smaller when closer)
            targetScale = origScale * ratio;

            if (distance <= 16) {
                GuiParticleSystem.getInstance().remove(this);
            }

            if (distance > 0.25f) {
                float dirX = dx / distance;
                float dirY = dy / distance;

                float acceleration = (float) BrutalityClientConfig.CONFIG.VOIDTOUCHED_MOTE_ACCELERATION.getAsDouble() / (distance * 0.1f + 0.5f);
                acceleration = Math.min(acceleration, 2.0f);

                vx += dirX * acceleration;
                vy += dirY * acceleration;
            }
        } else {
            // lerp back to original size
            targetScale = origScale;
            // remove after 2.5 seconds
            this.alpha -= 0.02f;
            if (this.alpha <= 0) {
                return false;
            }
        }

        this.scale = targetScale;

        return superclass;
    }
}
package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.particle.gui.BlackHoleGuiParticle;
import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.client.particle.gui.GuiParticle;
import com.goo.goo_lib.client.particle.gui.GuiParticleSystem;
import com.goo.goo_lib.util.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.awt.*;

public class VoidTouchedPipeline extends TooltipRenderPipeline {
    public static float centerX, centerY;
    public static int currentWidth, currentHeight;
    private static final int[] VOID_TOUCHED_COLORS = new int[]{
            new Color(250, 252, 255).getRGB(),
            new Color(191, 202, 223).getRGB(),
            new Color(127, 119, 145).getRGB(),
            new Color(71, 80, 107).getRGB(),
            new Color(50, 51, 61).getRGB(),
            new Color(27, 28, 56).getRGB()
    };

    @Override
    protected void modifyColors() {
        this.borderStart = VOID_TOUCHED_COLORS[0];
        this.borderEnd = VOID_TOUCHED_COLORS[2];
        this.bgStart = Colors.BLACK;
        this.bgEnd = Colors.BLACK;
    }


    @Override
    protected void renderOverlayPass() {
        ShaderInstance shader = BrutalityRenderTypes.InternalShaders.BOX_SHADOW.getInstance();
        if (shader != null) {
            float margin = (float) BrutalityClientConfig.CONFIG.VOIDTOUCHED_SHADOW_RADIUS.getAsDouble();

            margin = Mth.sin(RenderSystem.getShaderGameTime() * 1000F) * (margin / 3) + margin; // 33% of margin
            float halfMargin = margin / 2;

            shader.safeGetUniform("Margin").set(margin);

            RenderUtil.fillWithUv(BrutalityRenderTypes.getBoxShadowRenderType(RenderStateShard.LEQUAL_DEPTH_TEST),
                    gui, pos.x - 2 - halfMargin, pos.y - 2 - halfMargin, pos.x + width + 2 + halfMargin, pos.y + height + 2 + halfMargin,
                    399, Colors.BLACK);

            // don't think we should clean up, each time box shadow shader is used we should just modify the margin to what we want
        }


        RenderUtil.fillWithUv(BrutalityRenderTypes.getVoidRenderType(RenderStateShard.NO_DEPTH_TEST),
                gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, FastColor.ARGB32.color(15, 75, 225));
        centerX = pos.x + width / 2F;
        centerY = pos.y + height / 2F;
        currentWidth = width;
        currentHeight = height;
        if (Util.getMillis() % BrutalityClientConfig.CONFIG.VOIDTOUCHED_MOTE_SPAWN_INTERVAL.getAsInt() == 0) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

            // Pick a random side: 0=top, 1=right, 2=bottom, 3=left
            int side = RANDOM.nextInt(4);
            float x, y;
            float multiplier = (float) BrutalityClientConfig.CONFIG.VOIDTOUCHED_MOTE_SPAWN_BOUNDS_MULTIPLIER.getAsDouble();

            int maxDynamicRange = Math.max(76, (int) (150 * multiplier));

            y = switch (side) {
                case 0 -> {
                    x = centerX + Mth.randomBetweenInclusive(RANDOM, -halfWidth, halfWidth);
                    yield centerY - halfHeight - Mth.randomBetweenInclusive(RANDOM, 75, maxDynamicRange);
                }
                case 1 -> {
                    x = centerX + halfWidth + Mth.randomBetweenInclusive(RANDOM, 75, maxDynamicRange);
                    yield centerY + Mth.randomBetweenInclusive(RANDOM, -halfHeight, halfHeight);
                }
                case 2 -> {
                    x = centerX + Mth.randomBetweenInclusive(RANDOM, -halfWidth, halfWidth);
                    yield centerY + halfHeight + Mth.randomBetweenInclusive(RANDOM, 75, maxDynamicRange);
                }
                default -> {
                    x = centerX - halfWidth - Mth.randomBetweenInclusive(RANDOM, 75, maxDynamicRange);
                    yield centerY + Mth.randomBetweenInclusive(RANDOM, -halfHeight, halfHeight);
                }
            };


            float size = Mth.nextFloat(RANDOM, 16F, 32F);

            float dx = centerX - x;
            float dy = centerY - y;
            float scale = 0.01F;


            GuiParticle particle = new BlackHoleGuiParticle(
                    x, y, 400, BrutalityClientConfig.CONFIG.VOIDTOUCHED_MOTE_LIFETIME.getAsInt(), dx * scale, dy * scale, size
            );

            GuiParticleSystem.getInstance().add(particle);
        }
    }

    @Override
    protected void onClose() {
        currentHeight = 0;
        currentWidth = 0;
        centerX = 0;
        centerY = 0;
    }
}

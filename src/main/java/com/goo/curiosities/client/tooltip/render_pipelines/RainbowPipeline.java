package com.goo.curiosities.client.tooltip.render_pipelines;

import com.goo.curiosities.client.tooltip.TooltipRenderPipeline;
import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.goo.goo_lib.util.color.TooltipColorUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;

public class RainbowPipeline extends TooltipRenderPipeline {
    @Override
    protected void renderBackgroundPass() {
        gui.fillGradient(pos.x - 3, pos.y - 3, pos.x + width + 3, pos.y + height + 3, 400, bgStart, bgEnd);
        renderGodlyTooltipBackground(gui, pos.x, pos.y, width, height, 401);
    }

    public static void renderGodlyTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
        int i = x - 3;
        int j = y - 3;
        int k = width + 3 + 3;
        int l = height + 3 + 3;

        float segmentSize = 50.0F;
        float spread = 500.0F;
        float speed = 0.5F;

        renderRainbowFrame(guiGraphics, i, j + 1, k, l, z, segmentSize, spread, speed, Colors.RAINBOW);

        PostEffectRegistry.renderGuiWithEffect(GLRenderTypes.BLOOM_SHADER_LOCATION, guiGraphics,
                () -> renderRainbowFrame(guiGraphics, i, j + 1, k, l, z, segmentSize, spread, speed, Colors.RAINBOW), ShaderPipeline.BlitMode.ADDITIVE);
    }
    private static void renderRainbowFrame(GuiGraphics guiGraphics, int x, int y, int frameWidth, int frameHeight, int z, float segmentSize, float spread, float speed, int... colors) {
        int left = x;
        int right = x + frameWidth - 1;
        int top = y - 1;
        int bottom = y - 1 + frameHeight - 1;
        int vLength = frameHeight - 2;

        // calculate exact perimeter length of the 1px border loop
        float totalPerimeter = (frameWidth * 2) + (vLength * 2);
        // dynamically match spread to perimeter (or use totalPerimeter / N for multiple rainbow cycles)
        float effectiveSpread = totalPerimeter;

        // 1. top edge (left to right)
        float currentDist = 0.0F;
        for (int step = 0; step < frameWidth; step += (int) segmentSize) {
            int xStart = left + step;
            int xEnd = Math.min(left + step + (int) segmentSize, right + 1);

            float distStart = currentDist;
            float distEnd = currentDist + (xEnd - xStart);
            currentDist = distEnd;

            int cStart = TooltipColorUtil.getGradientAt(distStart, effectiveSpread, speed, colors);
            int cEnd = TooltipColorUtil.getGradientAt(distEnd, effectiveSpread, speed, colors);

            fillHorizontalGradient(guiGraphics, xStart, top, xEnd, top + 1, z, cStart, cEnd);
        }

        // 2. right edge (top to bottom)
        for (int step = 0; step < vLength; step += (int) segmentSize) {
            int yStart = top + 1 + step;
            int yEnd = Math.min(top + 1 + step + (int) segmentSize, bottom);

            float distStart = currentDist;
            float distEnd = currentDist + (yEnd - yStart);
            currentDist = distEnd;

            int cStart = TooltipColorUtil.getGradientAt(distStart, effectiveSpread, speed, colors);
            int cEnd = TooltipColorUtil.getGradientAt(distEnd, effectiveSpread, speed, colors);

            guiGraphics.fillGradient(right, yStart, right + 1, yEnd, z, cStart, cEnd);
        }

        // 3. bottom edge (right to left)
        for (int step = 0; step < frameWidth; step += (int) segmentSize) {
            int xStart = right - step;
            int xEnd = Math.max(right - step - (int) segmentSize, left - 1);

            float distStart = currentDist;
            float distEnd = currentDist + (xStart - xEnd);
            currentDist = distEnd;

            int cStart = TooltipColorUtil.getGradientAt(distStart, effectiveSpread, speed, colors);
            int cEnd = TooltipColorUtil.getGradientAt(distEnd, effectiveSpread, speed, colors);

            // layout flip: xEnd is visually left, xStart is visually right
            fillHorizontalGradient(guiGraphics, xEnd + 1, bottom, xStart + 1, bottom + 1, z, cEnd, cStart);
        }

        // 4. left edge (bottom to top)
        for (int step = 0; step < vLength; step += (int) segmentSize) {
            int yStart = bottom - step;
            int yEnd = Math.max(bottom - step - (int) segmentSize, top);

            float distStart = currentDist;
            float distEnd = currentDist + (yStart - yEnd);
            currentDist = distEnd;

            int cStart = TooltipColorUtil.getGradientAt(distStart, effectiveSpread, speed, colors);
            int cEnd = TooltipColorUtil.getGradientAt(distEnd, effectiveSpread, speed, colors);

            // layout flip: yEnd is visually top, yStart is visually bottom
            guiGraphics.fillGradient(left, yEnd + 1, left + 1, yStart + 1, z, cEnd, cStart);
        }
    }

    private static void fillHorizontalGradient(GuiGraphics guiGraphics, int minX, int minY, int maxX, int maxY, int z, int leftColor, int rightColor) {
        RenderType renderType = RenderType.gui();
        VertexConsumer consumer = guiGraphics.bufferSource().getBuffer(renderType);
        Matrix4f matrix = guiGraphics.pose().last().pose();

        // assign leftColor to left vertices (minX) and rightColor to right vertices (maxX)
        consumer.addVertex(matrix, (float) minX, (float) minY, (float) z).setColor(leftColor);
        consumer.addVertex(matrix, (float) minX, (float) maxY, (float) z).setColor(leftColor);
        consumer.addVertex(matrix, (float) maxX, (float) maxY, (float) z).setColor(rightColor);
        consumer.addVertex(matrix, (float) maxX, (float) minY, (float) z).setColor(rightColor);
    }
}
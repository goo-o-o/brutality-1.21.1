package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.registry.PostEffectRegistry;
import com.goo.goo_lib.util.ColorUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;

import java.util.Arrays;
import java.util.List;

public class GodlyPipeline extends TooltipRenderPipeline {
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
        List<Integer> colors = Arrays.asList(Colors.GODLY);

        renderRainbowFrame(guiGraphics, i, j + 1, k, l, z, segmentSize, spread, speed, colors);

        PostEffectRegistry.renderGuiWithEffect(GLRenderTypes.BLOOM_SHADER_LOCATION, guiGraphics,
                postPass -> {
                    if (postPass.getEffect().getName().equals("goo_lib:bloom_upsample"))
                        postPass.getEffect().safeGetUniform("Intensity").set(1.35F);
                },
                () -> renderRainbowFrame(guiGraphics, i, j + 1, k, l, z, segmentSize, spread, speed, colors));
    }

    private static void renderRainbowFrame(GuiGraphics guiGraphics, int x, int y, int frameWidth, int frameHeight, int z, float segmentSize, float spread, float speed, List<Integer> colors) {
        int left = x;
        int right = x + frameWidth - 1;
        int top = y - 1;
        int bottom = y - 1 + frameHeight - 1;
        int vLength = frameHeight - 2;

        // 1. top edge (left to right) - uses horizontal rendering
        float currentDist = 0.0F;
        for (int step = 0; step < frameWidth; step += (int) segmentSize) {
            int xStart = left + step;
            int xEnd = Math.min(left + step + (int) segmentSize, right + 1);

            float distStart = currentDist;
            float distEnd = currentDist + (xEnd - xStart);
            currentDist = distEnd;

            int cStart = ColorUtil.getGradientAt(distStart, spread, speed, colors);
            int cEnd = ColorUtil.getGradientAt(distEnd, spread, speed, colors);

            fillHorizontalGradient(guiGraphics, xStart, top, xEnd, top + 1, z, cStart, cEnd);
        }

        // 2. right edge (top to bottom) - standard fillGradient works perfectly here
        for (int step = 0; step < vLength; step += (int) segmentSize) {
            int yStart = top + 1 + step;
            int yEnd = Math.min(top + 1 + step + (int) segmentSize, bottom);

            float distStart = currentDist;
            float distEnd = currentDist + (yEnd - yStart);
            currentDist = distEnd;

            int cStart = ColorUtil.getGradientAt(distStart, spread, speed, colors);
            int cEnd = ColorUtil.getGradientAt(distEnd, spread, speed, colors);

            guiGraphics.fillGradient(right, yStart, right + 1, yEnd, z, cStart, cEnd);
        }

        // 3. bottom edge (right to left) - uses horizontal rendering
        for (int step = 0; step < frameWidth; step += (int) segmentSize) {
            int xStart = right - step;
            int xEnd = Math.max(right - step - (int) segmentSize, left - 1);

            float distStart = currentDist;
            float distEnd = currentDist + (xStart - xEnd);
            currentDist = distEnd;

            int cStart = ColorUtil.getGradientAt(distStart, spread, speed, colors);
            int cEnd = ColorUtil.getGradientAt(distEnd, spread, speed, colors);

            // layout flip: xEnd is visually left, xStart is visually right
            fillHorizontalGradient(guiGraphics, xEnd + 1, bottom, xStart + 1, bottom + 1, z, cEnd, cStart);
        }

        // 4. left edge (bottom to top) - standard fillGradient works perfectly here
        for (int step = 0; step < vLength; step += (int) segmentSize) {
            int yStart = bottom - step;
            int yEnd = Math.max(bottom - step - (int) segmentSize, top);

            float distStart = currentDist;
            float distEnd = currentDist + (yStart - yEnd);
            currentDist = distEnd;

            int cStart = ColorUtil.getGradientAt(distStart, spread, speed, colors);
            int cEnd = ColorUtil.getGradientAt(distEnd, spread, speed, colors);

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
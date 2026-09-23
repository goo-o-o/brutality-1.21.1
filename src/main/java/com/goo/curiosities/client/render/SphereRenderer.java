package com.goo.curiosities.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class SphereRenderer {

    private static final int STACKS = 32;  // vertical divisions (latitude)
    private static final int SLICES = 64;  // horizontal divisions (longitude)

    private static final float[] TRIANGLES = buildUvSphere(STACKS, SLICES);

    private SphereRenderer() {}

    public static void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            RenderType renderType,
            Vec3 center,
            float radius,
            int color
    ) {
        VertexConsumer consumer = bufferSource.getBuffer(renderType);

        poseStack.pushPose();
        poseStack.translate(center.x, center.y, center.z);
        poseStack.scale(radius, radius, radius);

        Matrix4f matrix = poseStack.last().pose();
        int a = (color >>> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        for (int i = 0; i < TRIANGLES.length; i += 9) {
            emitVertex(consumer, matrix, TRIANGLES, i,     r, g, b, a);
            emitVertex(consumer, matrix, TRIANGLES, i + 3, r, g, b, a);
            emitVertex(consumer, matrix, TRIANGLES, i + 6, r, g, b, a);
        }

        poseStack.popPose();
    }

    private static void emitVertex(
            VertexConsumer consumer,
            Matrix4f matrix,
            float[] verts,
            int offset,
            int r, int g, int b, int a
    ) {
        float nx = verts[offset], ny = verts[offset + 1], nz = verts[offset + 2];
        consumer.addVertex(matrix, nx, ny, nz)
                .setColor(r, g, b, a)
                .setNormal(nx, ny, nz);
    }

    private static float[] buildUvSphere(int stacks, int slices) {
        // Estimate size: 2 triangles per grid cell, 9 floats per triangle
        float[] out = new float[stacks * slices * 2 * 9];
        int idx = 0;

        for (int i = 0; i < stacks; i++) {
            float phi0 = (float) Math.PI * i / stacks;
            float phi1 = (float) Math.PI * (i + 1) / stacks;

            for (int j = 0; j < slices; j++) {
                float theta0 = (float) (2 * Math.PI) * j / slices;
                float theta1 = (float) (2 * Math.PI) * (j + 1) / slices;

                float[] v00 = point(phi0, theta0);
                float[] v01 = point(phi0, theta1);
                float[] v10 = point(phi1, theta0);
                float[] v11 = point(phi1, theta1);

                // Triangle 1: v00, v10, v11
                idx = writeVertex(out, idx, v00);
                idx = writeVertex(out, idx, v10);
                idx = writeVertex(out, idx, v11);

                // Triangle 2: v00, v11, v01
                idx = writeVertex(out, idx, v00);
                idx = writeVertex(out, idx, v11);
                idx = writeVertex(out, idx, v01);
            }
        }

        return out;
    }

    private static int writeVertex(float[] out, int idx, float[] v) {
        out[idx++] = v[0];
        out[idx++] = v[1];
        out[idx++] = v[2];
        return idx;
    }

    private static float[] point(float phi, float theta) {
        float sinPhi = (float) Math.sin(phi);
        return new float[]{
                sinPhi * (float) Math.cos(theta),
                (float) Math.cos(phi),
                sinPhi * (float) Math.sin(theta)
        };
    }
}
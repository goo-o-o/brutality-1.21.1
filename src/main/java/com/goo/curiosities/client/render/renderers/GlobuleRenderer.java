package com.goo.curiosities.client.render.renderers;

import com.goo.curiosities.common.entity.GlobuleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class GlobuleRenderer extends EntityRenderer<GlobuleEntity> {

    private static final float ANIMATION_TICKS = 10.0F;
    private static final float FADE_OUT_TICKS = 10.0F;

    public GlobuleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(GlobuleEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        float alpha = 1.0F;

        if (entity.isInGround()) {
            poseStack.translate(0.0D, 0.02D, 0.0D);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));

            float randomRotation = (entity.getId() * 31) % 360;
            poseStack.mulPose(Axis.ZP.rotationDegrees(randomRotation));

            // entrance scale-up progress
            float currentProgress = entity.getClientGroundTicks() + partialTick;
            float progress = Mth.clamp(currentProgress / ANIMATION_TICKS, 0.0F, 1.0F);
            float scaleProgress = 1.0F - (1.0F - progress) * (1.0F - progress);
            float baseScale = Mth.lerp(scaleProgress, 0.1F, entity.getRadius());

            // exit shrink & fade-out progress (last 10 ticks)
            float remainingTicks = entity.getRemainingPuddleTicks() - partialTick;
            if (remainingTicks <= FADE_OUT_TICKS) {
                float fadeProgress = Mth.clamp(remainingTicks / FADE_OUT_TICKS, 0.0F, 1.0F);
                alpha = fadeProgress;
                baseScale *= fadeProgress; // shrinks to 0 alongside fade
            }

            poseStack.scale(baseScale, baseScale, baseScale);
        } else {
            Quaternionf cameraRotation = this.entityRenderDispatcher.cameraOrientation();
            poseStack.mulPose(cameraRotation);

            Vec3 motion = entity.getDeltaMovement();
            if (motion.lengthSqr() > 1.0E-7D) {
                Quaternionf invCamera = new Quaternionf(cameraRotation).conjugate();
                Vector3f localMotion = new Vector3f((float) motion.x, (float) motion.y, (float) motion.z).rotate(invCamera);

                float rollAngle = (float) Mth.atan2(localMotion.x, localMotion.y);
                poseStack.mulPose(Axis.ZP.rotation(-rollAngle));
            }
        }

        // use entityTranslucent to allow alpha transparency blending
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        PoseStack.Pose last = poseStack.last();

        int a = (int) (alpha * 255.0F);

        addVertex(consumer, last, -0.5F, -0.5F, 0.0F, 0.0F, 0.0F, packedLight, a);
        addVertex(consumer, last,  0.5F, -0.5F, 0.0F, 1.0F, 0.0F, packedLight, a);
        addVertex(consumer, last,  0.5F,  0.5F, 0.0F, 1.0F, 1.0F, packedLight, a);
        addVertex(consumer, last, -0.5F,  0.5F, 0.0F, 0.0F, 1.0F, packedLight, a);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GlobuleEntity entity) {
        return entity.getTexture();
    }

    private static void addVertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float z, float u, float v, int packedLight, int alpha) {
        consumer.addVertex(pose, x, y, z)
                .setColor(255, 255, 255, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);
    }
}
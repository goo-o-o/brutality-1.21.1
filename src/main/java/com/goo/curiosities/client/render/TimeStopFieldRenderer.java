package com.goo.curiosities.client.render;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.common.entity.TimeStopField;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.ArrayList;
import java.util.List;

public class TimeStopFieldRenderer {
    public static void render(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        RenderTarget mainTarget = mc.getMainRenderTarget();
        RenderTarget maskTarget = PostEffectRegistry.getTempTarget(
                CuriositiesRenderTypes.TIME_STOP_SPHERE_SHADER_LOCATION,
                ShaderPipeline.PipelineStage.SCREEN,
                "sphere_mask"
        );

        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(true);
        Frustum frustum = event.getFrustum();
        Vec3 camPos = event.getCamera().getPosition();

        List<TimeStopField> fieldsToRender = new ArrayList<>();
        boolean isCameraInsideAnyField = false;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof TimeStopField field) {
                float r = field.getRadius(partialTick);
                Vec3 fieldPos = field.position();

                // check camera distance against sphere radius
                if (camPos.distanceToSqr(fieldPos) <= r * r) {
                    isCameraInsideAnyField = true;
                }

                AABB bounds = new AABB(
                        fieldPos.x - r, fieldPos.y - r, fieldPos.z - r,
                        fieldPos.x + r, fieldPos.y + r, fieldPos.z + r
                );

                if (frustum.isVisible(bounds)) {
                    fieldsToRender.add(field);
                }
            }
        }

        PostChain postChain = PostEffectRegistry.getPostChain(
                CuriositiesRenderTypes.TIME_STOP_SPHERE_SHADER_LOCATION,
                ShaderPipeline.PipelineStage.SCREEN
        );

        if (postChain != null) {
            for (PostPass pass : postChain.passes) {
                var shaderInstance = pass.getEffect();
                var uniform = shaderInstance.getUniform("IsCameraInside");
                if (uniform != null) {
                    uniform.set(isCameraInsideAnyField ? 1.0f : 0.0f);
                }
            }
        }


        if (fieldsToRender.isEmpty() && !isCameraInsideAnyField) {
            if (maskTarget != null) {
                maskTarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                maskTarget.clear(Minecraft.ON_OSX);
            }
            mainTarget.bindWrite(false);
            return;
        } else {

            // enable post effect pass
            PostEffectRegistry.renderEffectForNextTick(
                    CuriositiesRenderTypes.TIME_STOP_SPHERE_SHADER_LOCATION,
                    ShaderPipeline.PipelineStage.SCREEN
            );
        }



        if (maskTarget != null) {
            maskTarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            maskTarget.clear(Minecraft.ON_OSX);
            maskTarget.copyDepthFrom(mainTarget);
            maskTarget.bindWrite(false);
        }

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource bufferSource = CuriositiesRenderTypes.SPHERE_MASK_BUFFER;
        RenderType maskType = CuriositiesRenderTypes.getTimeStopRenderType();

        poseStack.pushPose();
        poseStack.translate(-camPos.x, -camPos.y, -camPos.z);

        for (TimeStopField field : fieldsToRender) {
            float radius = field.getRadius(partialTick);
            SphereRenderer.render(poseStack, bufferSource, maskType, field.position(), radius, 0xFFFFFFFF);
        }

        poseStack.popPose();
        bufferSource.endBatch(maskType);
        mainTarget.bindWrite(false);
    }
}
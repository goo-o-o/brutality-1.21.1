package com.goo.curiosities.client.render;

import com.goo.curiosities.common.entity.AbstractFieldEntity;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GenericFieldRenderer {

    public static <T extends AbstractFieldEntity> void renderField(
            RenderLevelStageEvent event,
            Class<T> fieldClass,
            ResourceLocation shaderLocation,
            MultiBufferSource.BufferSource bufferSource,
            @Nullable RenderType renderType
    ) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        RenderTarget mainTarget = mc.getMainRenderTarget();
        RenderTarget maskTarget = PostEffectRegistry.getTempTarget(
                shaderLocation,
                ShaderPipeline.PipelineStage.SCREEN,
                "sphere_mask"
        );

        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(true);
        Frustum frustum = event.getFrustum();
        Vec3 camPos = event.getCamera().getPosition();

        List<T> fieldsToRender = new ArrayList<>();
        boolean isCameraInsideAnyField = false;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (fieldClass.isInstance(entity)) {
                T field = fieldClass.cast(entity);
                float r = field.getRadius(partialTick);
                Vec3 fieldPos = field.position();

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
                shaderLocation,
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
        }

        PostEffectRegistry.renderEffectForNextTick(
                shaderLocation,
                ShaderPipeline.PipelineStage.SCREEN
        );

        if (maskTarget != null) {
            maskTarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            maskTarget.clear(Minecraft.ON_OSX);
            maskTarget.copyDepthFrom(mainTarget);
            maskTarget.bindWrite(false);
        }

        PoseStack poseStack = event.getPoseStack();

        // default to positionColor if no custom render type supplied
        RenderType targetType = (renderType != null) 
                ? renderType 
                : RenderType.debugFilledBox();

        poseStack.pushPose();
        poseStack.translate(-camPos.x, -camPos.y, -camPos.z);

        for (T field : fieldsToRender) {
            float radius = field.getRadius(partialTick);
            SphereRenderer.render(poseStack, bufferSource, targetType, field.position(), radius, 0xFFFFFFFF);
        }

        poseStack.popPose();
        bufferSource.endBatch(targetType);
        mainTarget.bindWrite(false);
    }
}
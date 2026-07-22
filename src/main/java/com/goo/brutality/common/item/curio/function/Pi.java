package com.goo.brutality.common.item.curio.function;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.BrutalityFunctionCurioItem;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.registry.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.goo.goo_lib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Pi extends BrutalityFunctionCurioItem {
    private static final ResourceLocation PI_SYMBOL = Brutality.loc("textures/misc/pi_symbol.png");

    public Pi(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public static void render(LivingEntity livingEntity, PoseStack poseStack, float partialTick, MultiBufferSource multiBufferSource) {
        if (CurioUtil.isWearingCurio(livingEntity, BrutalityItems.Curio.Math.PI.value())) {
            poseStack.pushPose();

            float length = 3.14F;
            RenderType renderType = RenderType.entityTranslucent(PI_SYMBOL);

            poseStack.mulPose(Axis.YP.rotationDegrees(-180 - livingEntity.getViewYRot(partialTick)));
            poseStack.translate(0, 0.01, length / 2F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90));

            RenderUtil.drawDoubleSidedQuad(
                    multiBufferSource.getBuffer(renderType),
                    poseStack.last().pose(),
                    length, length,
                    0F, 1F, 0F, 1F, LightTexture.FULL_BRIGHT,
                    255, 255, 255, 255
            );

            poseStack.translate(0, 0, 0.01);
            PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLUR_SHADER_LOCATION, ShaderPipeline.PipelineStage.ENTITY);
            VertexConsumer blurConsumer = multiBufferSource.getBuffer(GLRenderTypes.getBlurRenderType(PI_SYMBOL));

            RenderUtil.drawDoubleSidedQuad(
                    blurConsumer,
                    poseStack.last().pose(),
                    length, length,
                    0F, 1F, 0F, 1F, LightTexture.FULL_BRIGHT,
                    255, 255, 255, 255
            );

            PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLOOM_SHADER_LOCATION, ShaderPipeline.PipelineStage.ENTITY);
            VertexConsumer bloomConsumer = multiBufferSource.getBuffer(GLRenderTypes.getBloomRenderType(PI_SYMBOL));

            RenderUtil.drawDoubleSidedQuad(
                    bloomConsumer,
                    poseStack.last().pose(),
                    length, length,
                    0F, 1F, 0F, 1F, LightTexture.FULL_BRIGHT,
                    255, 255, 255, 255
            );

            poseStack.popPose();
        }
    }
}

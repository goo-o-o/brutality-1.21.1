package com.goo.curiosities.client.render.layer.entity;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.client.render.ClientGlitchState;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;


/**
 * Where all mob effect render effects for entities are rendered
 */
public class MobEffectLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public MobEffectLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    private static final ResourceLocation ICE_BLOCK_TEXTURE = ResourceLocation.withDefaultNamespace("block/ice");

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.hasEffect(CuriositiesEffects.IMPERVIOUS)) {
            impervious(bufferSource, entity, poseStack, packedLight);
        }
        if (entity.hasEffect(CuriositiesEffects.GLITCHED)) {
            glitched(bufferSource, entity, poseStack);
        }

        frozen(bufferSource, entity, poseStack, packedLight, partialTick);
    }

    private void glitched(MultiBufferSource bufferSource, T entity, PoseStack poseStack) {
        ClientGlitchState.GlitchData data = ClientGlitchState.getGlitchData(entity);
        ResourceLocation texture = this.getTextureLocation(entity);
        PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLUR_SHADER_LOCATION, ShaderPipeline.PipelineStage.WORLD);

        int color;

        for (ClientGlitchState.CloneData clone : data.clones) {
            poseStack.pushPose();
            poseStack.translate(clone.offsetX, clone.offsetY, clone.offsetZ);

            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(texture));
            color = FastColor.ARGB32.colorFromFloat(clone.a, clone.r, clone.g, clone.b);

            this.getParentModel().renderToBuffer(
                    poseStack,
                    consumer,
                    LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY,
                    color
            );

            poseStack.popPose();
        }

        for (ClientGlitchState.CloneData clone : data.clones) {
            poseStack.pushPose();
            poseStack.translate(clone.offsetX, clone.offsetY, clone.offsetZ);

            VertexConsumer bloomConsumer = bufferSource.getBuffer(GLRenderTypes.getBlurRenderType(texture));
            color = FastColor.ARGB32.colorFromFloat(clone.a, clone.r, clone.g, clone.b);

            this.getParentModel().renderToBuffer(
                    poseStack,
                    bloomConsumer,
                    LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY,
                    color
            );

            poseStack.popPose();
        }
    }

    private void impervious(MultiBufferSource bufferSource, T entity, PoseStack poseStack, int packedLight) {
        VertexConsumer baseConsumer = bufferSource.getBuffer(
                CuriositiesRenderTypes.getNanoMachinesRenderType(
                        getTextureLocation(entity),
                        RenderStateShard.LEQUAL_DEPTH_TEST));

        this.getParentModel().renderToBuffer(
                poseStack,
                baseConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                Colors.WHITE
        );
    }


    private void frozen(MultiBufferSource bufferSource, LivingEntity entity, PoseStack poseStack, int packedLight, float partialTick) {
        MobEffectInstance effectInstance = entity.getEffect(CuriositiesEffects.FROZEN);
        if (effectInstance == null) {
            return;
        }

        float remainingTicks = (float) effectInstance.getDuration() - partialTick;
        float alphaFactor = 1.0F;
        if (remainingTicks < 4) {
            alphaFactor = Mth.clamp(remainingTicks / 5, 0.0F, 1.0F);
        }

        if (alphaFactor <= 0.0F) return; // skip if invisible

        TextureAtlas blockAtlas = Minecraft.getInstance().getModelManager().getAtlas(TextureAtlas.LOCATION_BLOCKS);
        TextureAtlasSprite iceSprite = blockAtlas.getSprite(ICE_BLOCK_TEXTURE);

        RenderType renderType = RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS);
        VertexConsumer baseConsumer = bufferSource.getBuffer(renderType);
        VertexConsumer finalConsumer = new SpriteCoordinateExpander(baseConsumer, iceSprite);


        int targetAlphaInt = (int) (alphaFactor * 175);
        int iceColorTint = FastColor.ARGB32.color(targetAlphaInt, 255, 255, 255);

        this.getParentModel().renderToBuffer(
                poseStack,
                finalConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                iceColorTint
        );
    }

}
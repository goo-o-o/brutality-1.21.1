package com.goo.brutality.client.render.layer.entity;

import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.Colors;
import com.goo.brutality.util.CurioUtil;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.registry.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.goo.goo_lib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;

/**
 * Where all mob effect render effects for entities are rendered
 */
public class PotionEffectLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public PotionEffectLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.hasEffect(BrutalityEffects.ENRAGED)) {
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
            int color = CurioUtil.isWearingCurio(entity, BrutalityItems.Curio.Rage.GAMMA_SERUM.value()) ? Colors.ERIN : Colors.RED;
            this.getParentModel().renderToBuffer(
                    poseStack, consumer, packedLight,
                    OverlayTexture.NO_OVERLAY,
                    color
            );
        }

        if (entity.hasEffect(BrutalityEffects.ASURA_FORM)) {
            poseStack.pushPose();


            if (this.getParentModel() instanceof net.minecraft.client.model.HumanoidModel<?> humanoidModel) {
                poseStack.pushPose();
                humanoidModel.head.translateAndRotate(poseStack);
                VertexConsumer colorConsumer = bufferSource.getBuffer(RenderType.gui());
                Matrix4f matrix = poseStack.last().pose();

                float eyeY = -4.0F / 16.0F;
                float eyeZ = -4.1F / 16.0F; // slightly in front of the face to prevent z-fighting
                float quadSize = 2F / 16.0F;

                // left eye (starts at X=-3, spans to X=-1)
                float leftX = -3.0F / 16.0F;
                renderColorQuad(colorConsumer, matrix, leftX, eyeY, eyeZ, quadSize, LightTexture.FULL_BRIGHT, 255, 0, 0, 255);

                // right eye (starts at X=1, spans to X=3)
                float rightX = 1.0F / 16.0F;
                renderColorQuad(colorConsumer, matrix, rightX, eyeY, eyeZ, quadSize, LightTexture.FULL_BRIGHT, 255, 0, 0, 255);

                poseStack.popPose();

                humanoidModel.body.translateAndRotate(poseStack);
            }

            float distanceBehind = entity.getBbWidth() * 0.25F;
            poseStack.translate(-0.1, entity.getBbHeight() / 3, distanceBehind);

            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            MobEffectTextureManager iconManager = Minecraft.getInstance().getMobEffectTextures();

            TextureAtlasSprite sprite = iconManager.get(BrutalityEffects.ASURA_FORM);
            float u0 = sprite.getU0();
            float u1 = sprite.getU1();
            float v0 = sprite.getV0();
            float v1 = sprite.getV1();


            VertexConsumer normalConsumer = bufferSource.getBuffer(RenderType.entityCutout(sprite.atlasLocation()));
            RenderUtil.drawDoubleSidedQuad(normalConsumer, poseStack.last().pose(), 1F, 1F, u0, u1, v0, v1, LightTexture.FULL_BRIGHT, 255, 255, 255, 255);
            poseStack.translate(0, -0.5, 0.5F);

            PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLOOM_SHADER_LOCATION, ShaderPipeline.PipelineStage.ENTITY);
            VertexConsumer bloomConsumer = bufferSource.getBuffer(GLRenderTypes.getBloomRenderType(sprite.atlasLocation()));
            RenderUtil.drawDoubleSidedQuad(bloomConsumer, poseStack.last().pose(), 4F, 4F, u0, u1, v0, v1, LightTexture.FULL_BRIGHT, 255, 255, 255, 255);

            PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLUR_SHADER_LOCATION, ShaderPipeline.PipelineStage.ENTITY);
            VertexConsumer blurConsumer = bufferSource.getBuffer(GLRenderTypes.getBlurRenderType(sprite.atlasLocation()));
            RenderUtil.drawDoubleSidedQuad(blurConsumer, poseStack.last().pose(), 2F, 2F, u0, u1, v0, v1, LightTexture.FULL_BRIGHT, 255, 255, 255, 255);

            poseStack.popPose();
        }

    }

    private void renderColorQuad(VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, float size, int light, int r, int g, int b, int a) {
        float nx = 0.0F;
        float ny = 0.0F;
        float nz = -1.0F;

        consumer.addVertex(matrix, x, y, z).setColor(r, g, b, a).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
        consumer.addVertex(matrix, x, y + size, z).setColor(r, g, b, a).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
        consumer.addVertex(matrix, x + size, y + size, z).setColor(r, g, b, a).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
        consumer.addVertex(matrix, x + size, y, z).setColor(r, g, b, a).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
    }
}
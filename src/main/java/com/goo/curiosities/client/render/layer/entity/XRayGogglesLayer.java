package com.goo.curiosities.client.render.layer.entity;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.Colors;
import com.goo.curiosities.util.CurioUtil;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;

public class XRayGogglesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public XRayGogglesLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;
        if (entity == mc.player) return;
        CuriosApi.getCuriosInventory(mc.player).ifPresent(handler -> {
            float distance = entity.distanceTo(mc.player);
            int radius;
            int color = Colors.WHITE;
            float distanceRatio = 1;
            float maxAlpha = 175;
            VertexConsumer seeThroughConsumer = null;
            if (handler.isEquipped(CuriositiesItems.OMNISCIENT_GLASSES.value())) {
                radius = 15;
                distanceRatio = distance / radius;
                int targetAlphaInt = (int) (100 * (1F - distanceRatio));
                color = FastColor.ARGB32.color(targetAlphaInt, color);
                PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLUR_SHADER_LOCATION, ShaderPipeline.PipelineStage.WORLD);
                seeThroughConsumer = bufferSource.getBuffer(GLRenderTypes.getBlurRenderType(getTextureLocation(entity), RenderStateShard.NO_DEPTH_TEST, RenderStateShard.NO_CULL));
            } else if (handler.isEquipped(CuriositiesItems.XRAY_GOGGLES.value())) {
                radius = 10;
                distanceRatio = distance / radius;
                int targetAlphaInt = (int) (maxAlpha * (1F - distanceRatio));
                color = FastColor.ARGB32.color(targetAlphaInt, Colors.MATRIX[2]);
                seeThroughConsumer = bufferSource.getBuffer(CuriositiesRenderTypes.getEncryptedRenderType(getTextureLocation(entity), RenderStateShard.NO_DEPTH_TEST));
                CuriositiesRenderTypes.InternalShaders.ENCRYPTED_TEXTURE.getInstance().safeGetUniform("EntityID").set(entity.getId());
            }
            if (distanceRatio >= 1) return; // return if too far away
            if (seeThroughConsumer == null) return;

            this.getParentModel().renderToBuffer(
                    poseStack,
                    seeThroughConsumer,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    color
            );

        });


    }
}
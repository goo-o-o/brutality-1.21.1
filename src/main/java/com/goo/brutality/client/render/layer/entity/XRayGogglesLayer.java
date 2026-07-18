package com.goo.brutality.client.render.layer.entity;

import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.common.BrutalityServerConfig;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.Colors;
import com.goo.brutality.util.CurioUtil;
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
        if (!CurioUtil.isWearingCurio(mc.player, BrutalityItems.Curio.XRAY_GOGGLES.value())) return;

        int radius = BrutalityServerConfig.CONFIG.XRAY_GOGGLES_RADIUS.getAsInt();
        float distance = entity.distanceTo(mc.player);

        float distanceRatio = distance / radius; // for later
        if (distanceRatio >= 1) return; // return if too far away

        float maxAlpha = 100;

        int targetAlphaInt = (int) (maxAlpha * (1F - distanceRatio));


        int color = FastColor.ARGB32.color(targetAlphaInt, Colors.ERIN);

//        PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLUR_SHADER_LOCATION, ShaderPipeline.PipelineStage.ENTITY);
//        VertexConsumer seeThroughConsumer = bufferSource.getBuffer(GLRenderTypes.getBlurRenderType(getTextureLocation(entity), RenderStateShard.NO_DEPTH_TEST));
        BrutalityRenderTypes.InternalShaders.ENCRYPTED_TEXTURE.getInstance().safeGetUniform("EntityID").set(entity.getId());
        VertexConsumer seeThroughConsumer = bufferSource.getBuffer(BrutalityRenderTypes.getEncryptedRenderType(getTextureLocation(entity), RenderStateShard.NO_DEPTH_TEST));

        this.getParentModel().renderToBuffer(
                poseStack,
                seeThroughConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                color
        );
    }
}
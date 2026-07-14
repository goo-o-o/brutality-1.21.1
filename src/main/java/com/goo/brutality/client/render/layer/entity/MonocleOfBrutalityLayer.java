package com.goo.brutality.client.render.layer.entity;

import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.registry.PostEffectRegistry;
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
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class MonocleOfBrutalityLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public MonocleOfBrutalityLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;
        if (entity == mc.player) return;
        if (!mc.player.hasEffect(BrutalityEffects.ENRAGED)) return;
        if (!CurioUtil.isWearingCurio(mc.player, BrutalityItems.Curio.Rage.MONOCLE_OF_BRUTALITY.value())) return;
        MobEffectInstance effectInstance = mc.player.getEffect(BrutalityEffects.ENRAGED);
        assert effectInstance != null;
        int amplifier = effectInstance.getAmplifier() + 1;
        int radius = amplifier * 5 + 10;
        float distance = entity.distanceTo(mc.player);

        float distanceRatio = distance / radius; // for later
        if (distanceRatio >= 1) return; // return if too far away
        float distanceFade = 1.0F - distanceRatio;
         distanceFade = distanceFade * distanceFade;

        float remainingTicks = (float) effectInstance.getDuration() - partialTick;
        float alphaFactor = 1.0F;
        if (remainingTicks < 10) {
            alphaFactor = Mth.clamp(remainingTicks / 5, 0.0F, 1.0F);
        }

        if (alphaFactor <= 0.0F) return; // invisible

        float renderTime = ageInTicks + partialTick;

        float sineWave = Mth.sin(renderTime * 0.15F);

        float fluctuation = 1.0F + (sineWave * 0.2F);

        float maxAlpha = 255;

        int targetAlphaInt = (int) (maxAlpha * alphaFactor * distanceFade * fluctuation);

        targetAlphaInt = Mth.clamp(targetAlphaInt, 0, 255);

        int pureRedTint = FastColor.ARGB32.color(targetAlphaInt, 255,0, 0);

        PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLUR_SHADER_LOCATION, ShaderPipeline.PipelineStage.ENTITY);
        VertexConsumer seeThroughConsumer = bufferSource.getBuffer(GLRenderTypes.getBlurRenderType(getTextureLocation(entity), RenderStateShard.NO_DEPTH_TEST));
//        VertexConsumer seeThroughConsumer = bufferSource.getBuffer(BrutalityRenderTypes.getEncryptedRenderType(RenderStateShard.LEQUAL_DEPTH_TEST));

        this.getParentModel().renderToBuffer(
                poseStack,
                seeThroughConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                pureRedTint
        );
    }
}
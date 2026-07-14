package com.goo.brutality.client.render.layer.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.ClientUtil;

public class AutoFullbrightGeoLayer<T extends GeoAnimatable> extends AutoGlowingGeoLayer<T> {
    public AutoFullbrightGeoLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    protected @Nullable RenderType getRenderType(T animatable, @Nullable MultiBufferSource bufferSource) {
        if (!(animatable instanceof Entity entity))
            return AutoFullbrightTexture.getRenderType(getTextureResource(animatable));

        boolean invisible = entity.isInvisible();
        ResourceLocation texture = AutoFullbrightTexture.getEmissiveResource(getTextureResource(animatable));

        if (invisible && !entity.isInvisibleTo(ClientUtil.getClientPlayer()))
            return RenderType.itemEntityTranslucentCull(texture);

        if (Minecraft.getInstance().shouldEntityAppearGlowing(entity)) {
            if (invisible)
                return RenderType.outline(texture);

            return AutoFullbrightTexture.getOutlineRenderType(getTextureResource(animatable));
        }

        return invisible ? null : AutoFullbrightTexture.getRenderType(getTextureResource(animatable));
    }

    @SuppressWarnings("removal")
    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        renderType = getRenderType(animatable);

        if (renderType != null) {
            getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, renderType,
                    bufferSource.getBuffer(renderType), partialTick, LightTexture.FULL_BRIGHT, packedOverlay,
                    getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt());
        }
    }
}

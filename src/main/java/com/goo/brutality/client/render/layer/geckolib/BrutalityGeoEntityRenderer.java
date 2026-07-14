package com.goo.brutality.client.render.layer.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BrutalityGeoEntityRenderer<T extends Entity & GeoAnimatable> extends GeoEntityRenderer<T> {

    public BrutalityGeoEntityRenderer(EntityRendererProvider.Context context, EntityType<? extends T> entityType) {
        super(context, entityType);
    }

    public BrutalityGeoEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);

        // calculate half height to find the perfect vertical center pivot point
        float halfHeight = animatable.getBbHeight() * 0.5F;

        // 1. translate the pivot up to the physical center of the model
        poseStack.translate(0.0F, halfHeight, 0.0F);

        // 2. apply the smooth interpolated rotations
        float lerpedYaw = Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot());
        float lerpedPitch = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());

        poseStack.mulPose(Axis.YP.rotationDegrees(lerpedYaw - 90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(lerpedPitch)); // using XP assuming you wanted pitch; change to ZP if your model format requires it

        // 3. translate back down by the exact same offset to restore positions
        poseStack.translate(0.0F, -halfHeight, 0.0F);
    }
}

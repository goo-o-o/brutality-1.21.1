package com.goo.curiosities.mixin;

import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.util.Colors;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

    @WrapOperation(
            method = "renderNameTag",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"
            )
    )
    private int modifyNameTagRendering(
            Font instance,
            Component pDisplayName,
            float pX,
            float pY,
            int pColor,
            boolean pDropShadow,
            org.joml.Matrix4f pPose,
            net.minecraft.client.renderer.MultiBufferSource pBufferSource,
            Font.DisplayMode pDisplayMode,
            int pBackgroundColor,
            int pPackedLight,
            Operation<Integer> original,
            @Local(argsOnly = true) T entity // MixinExtras captures the entity parameter here
    ) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(CuriositiesEffects.REDACTED)) {
            if (pDisplayMode == Font.DisplayMode.SEE_THROUGH) {
                return original.call(
                        instance,
                        pDisplayName, // removes text
                        pX, pY, pColor, pDropShadow, pPose, pBufferSource, pDisplayMode,
                        Colors.BLACK, // solid background alpha
                        pPackedLight
                );
            }
            return 0;
        }

        // vanilla
        return original.call(instance, pDisplayName, pX, pY, pColor, pDropShadow, pPose, pBufferSource, pDisplayMode, pBackgroundColor, pPackedLight);
    }

}
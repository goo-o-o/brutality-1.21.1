package com.goo.curiosities.mixin;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @ModifyVariable(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private MultiBufferSource redirectEntityBuffer(MultiBufferSource original, T entity) {
        if (!entity.hasEffect(CuriositiesEffects.CENSORED)) {
            return original;
        }
        PostEffectRegistry.renderEffectForNextTick(
                GLRenderTypes.PIXELATE_SHADER_LOCATION,
                ShaderPipeline.PipelineStage.WORLD
        );
        // wrap buffer so every draw call (body, armor, items, elytra) routes into PIXELATE_OUTPUT
        return renderType -> original.getBuffer(CuriositiesRenderTypes.redirectPixelate(renderType));
    }

}
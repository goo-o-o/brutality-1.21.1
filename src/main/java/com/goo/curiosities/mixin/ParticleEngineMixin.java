package com.goo.curiosities.mixin;

import com.goo.curiosities.common.entity.TimeStopField;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {

    @Shadow
    protected ClientLevel level;

    @WrapMethod(method = "tickParticle")
    private void freezeInsideSphere(Particle particle, Operation<Void> original) {
        if (TimeStopField.shouldBeAffected(level, particle.getPos())) {
            return;  // skip particle.tick()
        }
        original.call(particle);
    }

    @WrapOperation(
            method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;Ljava/util/function/Predicate;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"
            )
    )
    private void curiosities$freezePartialTick(Particle particle, VertexConsumer buffer, Camera camera, float partialTick, Operation<Void> original) {
        float effectiveTick = TimeStopField.shouldBeAffected(level, particle.getPos()) ? 1.0F : partialTick;
        original.call(particle, buffer, camera, effectiveTick);
    }
}
package com.goo.curiosities.mixin;

import com.goo.curiosities.common.item.MobEffectTickDownModifyingCurioItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;tickDownDuration()I"
            )
    )
    private int modifyEffectTickDown(MobEffectInstance instance, Operation<Integer> original, LivingEntity entity, Runnable onExpirationRunnable) {
        return MobEffectTickDownModifyingCurioItem.modifyDuration(instance, entity, original);
    }
}
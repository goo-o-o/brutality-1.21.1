package com.goo.curiosities.mixin;

import com.goo.curiosities.common.item.curio.charm.OmnidirectionalMovementGear;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow
    public Input input;

    @Redirect(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/Input;hasForwardImpulse()Z"
            )
    )
    private boolean checkAnyHorizontalImpulseToMaintainSprint(Input instance) {
        LocalPlayer localPlayer = (((LocalPlayer) (Object) this));
        return OmnidirectionalMovementGear.handleOmnidirectionalImpulseToMaintainSprint(localPlayer, instance);
    }

    @Inject(method = "hasEnoughImpulseToStartSprinting", at = @At("HEAD"), cancellable = true)
    private void modifyHasEnoughImpulseToStartSprinting(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        // handle underwater condition first if needed
        if (player.isUnderWater()) return;

        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            if (handler.isEquipped(stack -> stack.is(CuriositiesItems.OMNIDIRECTIONAL_MOVEMENT_GEAR.value())
                    || stack.is(CuriositiesItems.MOVEMENT_GODS_TRACERS.value()))) {
                boolean hasImpulse = Math.abs(player.input.forwardImpulse) > 0.00001F || Math.abs(player.input.leftImpulse) > 0.00001F;
                cir.setReturnValue(hasImpulse);
            } else if (handler.isEquipped(CuriositiesItems.VECTOR_STABILIZER.value())) {
                boolean hasImpulse = player.input.forwardImpulse > 0.00001F;
                cir.setReturnValue(hasImpulse);
            }
        });
    }

    @Redirect(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    private boolean bypassInitialItemUseSprintCheck(LocalPlayer instance) {
        if (CurioUtil.isWearingCurio(instance, CuriositiesItems.VECTOR_STABILIZER.value(), CuriositiesItems.MOVEMENT_GODS_TRACERS.value()))
            return false; // logic is inverted so we return false

        return instance.isUsingItem();
    }

    @Redirect(
            method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", ordinal = 1)
    )
    private boolean bypassContinuousItemUseSprintCheck(LocalPlayer instance) {
        if (CurioUtil.isWearingCurio(instance, CuriositiesItems.VECTOR_STABILIZER.value(), CuriositiesItems.MOVEMENT_GODS_TRACERS.value()))
            return false; // logic is inverted so we return false
        return instance.isUsingItem();
    }

    @Redirect(
            method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", ordinal = 0)
    )
    private boolean bypassItemUseSlowdown(LocalPlayer instance) {

        Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(instance);
        if (optional.isPresent()) {
            if (optional.get().isEquipped(CuriositiesItems.MOVEMENT_GODS_TRACERS.value())) return false;
            if (optional.get().isEquipped(CuriositiesItems.KINETIC_COMPENSATOR.value())) return false;
            if (optional.get().isEquipped(CuriositiesItems.VECTOR_STABILIZER.value())) {
                if (instance.isUsingItem() && !instance.isPassenger()) {
                    this.input.leftImpulse *= 0.2F;
                    this.input.forwardImpulse *= 0.2F; // mimic slowdown
                }
                return false;
            }
        }

        return instance.isUsingItem();
    }



    @WrapOperation(
            method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;canStartSwimming()Z")
    )
    private boolean addCustomSwimCondition(LocalPlayer instance, Operation<Boolean> original) {
        boolean vanillaCanSwim = original.call(instance);

        if (!vanillaCanSwim) {
            return false;
        }

        // remember to invert this condition
        boolean shouldNotCancel = CurioUtil.isWearingCurio(instance, CuriositiesItems.MINIATURE_ANCHOR.value());

        return !shouldNotCancel;
    }
}
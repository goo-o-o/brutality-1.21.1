package com.goo.brutality.mixin;

import com.goo.brutality.common.mob_effect.DespairEffect;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightTexture.class)
public class LightTextureMixin {

//    @ModifyConstant(
//            method = "updateLightTexture",
//            constant = @Constant(floatValue = 0.0F, ordinal = 1)
//    )
//    private float modifyLightTexture(float originalZero) {
//        Minecraft mc = Minecraft.getInstance();
//        if (mc.player == null) return originalZero;
//
//        originalZero = DespairEffect.modifyLightTexture(mc.player, originalZero);
//
//        return originalZero;
//    }

    @Inject(
            method = "calculateDarknessScale",
            at = @At("TAIL"),
            cancellable = true
    )
    private void modifyDarknessScale(LivingEntity entity, float gamma, float partialTick, CallbackInfoReturnable<Float> cir) {
        if (entity == null) return;
        DespairEffect.modifyDarkness(entity, cir);
    }
}

package com.goo.curiosities.mixin;

import com.goo.curiosities.common.entity.TimeStopField;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    /**
     * Forces partial tick to be 1.0 when entity is frozen, preventing lerp glitching and rubberbanding
     */
    @ModifyVariable(
        method = "renderEntity",
        at = @At("HEAD"),
        argsOnly = true,
        ordinal = 0  // the float partialTick argument
    )
    private float forcePartialTickForFrozen(float partialTick, Entity entity, double camX, double camY, double camZ) {
        if (TimeStopField.shouldBeAffected(entity)) {
            return 1.0F;
        }
        return partialTick;
    }
}
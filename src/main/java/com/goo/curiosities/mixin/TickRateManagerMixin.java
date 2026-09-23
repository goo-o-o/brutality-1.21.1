package com.goo.curiosities.mixin;

import com.goo.curiosities.common.entity.TimeStopField;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TickRateManager.class)
public class TickRateManagerMixin {

    @ModifyReturnValue(method = "isEntityFrozen", at = @At("RETURN"))
    private boolean freezeInsideTimeStopField(boolean original, Entity entity) {
        if (original) return true;                       // already frozen globally
        return TimeStopField.shouldBeAffected(entity);
    }
}
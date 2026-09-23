package com.goo.curiosities.mixin;

import com.goo.curiosities.common.entity.TimeStopField;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Level.class)
public class LevelMixin {
    @ModifyReturnValue(method = "shouldTickBlocksAt(Lnet/minecraft/core/BlockPos;)Z", at = @At("RETURN"))
    private boolean curiosities$freezeInsideTimeStopField(boolean original, BlockPos pos) {
        if (!original) return false; // already false, no reason to fight it

        Level self = (Level) (Object) this;
        return !TimeStopField.shouldBeAffected(self, pos.getCenter());
    }

}

package com.goo.curiosities.mixin;

import com.goo.curiosities.client.render.OreCache;
import com.goo.curiosities.common.entity.TimeStopField;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(
            method = "setServerVerifiedBlockState",
            at = @At("HEAD")
    )
    private void onBlockStateChanged(BlockPos pos, BlockState state, int flags, CallbackInfo ci) {
        OreCache.handleBlockUpdate(pos, state);
    }

    @ModifyReturnValue(method = "shouldTickDeath", at = @At("RETURN"))
    private boolean freezeInsideTimeStopField(boolean original, Entity entity) {
        if (original) return true;                       // already frozen globally
        return TimeStopField.shouldBeAffected(entity);
    }


    @Inject(
            method = "doAnimateTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void skipAnimateTickInsideField(
            int posX, int posY, int posZ, int range, RandomSource random,
            @Nullable Block block, BlockPos.MutableBlockPos blockPos,
            CallbackInfo ci) {

        if (TimeStopField.shouldBeAffected((Level) (Object) this, blockPos.getCenter())) {
            ci.cancel();
        }
    }
}
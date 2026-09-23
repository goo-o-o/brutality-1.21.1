package com.goo.curiosities.mixin;

import com.goo.curiosities.common.item.curio.feet.PortableTrampoline;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(
        method = "updateEntityAfterFallOn",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onUpdateEntityAfterFallOn(BlockGetter level, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity && !livingEntity.isSuppressingBounce()) {
            Block block = ((Block) (Object) this);

            if (PortableTrampoline.proc(livingEntity, block)) {
                ci.cancel();
            }
        }
    }
}
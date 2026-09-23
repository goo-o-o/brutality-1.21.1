package com.goo.curiosities.mixin;

import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    protected Vec3 stuckSpeedMultiplier;

    @ModifyReturnValue(method = "getTicksFrozen", at = @At("RETURN"))
    private int alwaysZeroTicksFrozen(int original) {
        Entity entity = ((Entity) (Object) this);
        if (entity instanceof LivingEntity livingEntity)
            if (CurioUtil.isWearingCurio(livingEntity, CuriositiesItems.CLOAK_OF_TRUE_ICE.value()))
                return 0;
        return original;
    }

    @Inject(method = "ignoreExplosion", at = @At("RETURN"), cancellable = true)
    private void bypassExplosion(Explosion explosion, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = ((Entity) (Object) this);
        if (entity instanceof LivingEntity livingEntity) {
            if (explosion.getDirectSourceEntity() instanceof AbstractWindCharge)
                if (CurioUtil.isWearingCurio(livingEntity, CuriositiesItems.WAY_OF_THE_WIND.value()))
                    cir.setReturnValue(true);

        }
    }

    @Inject(method = "makeStuckInBlock", at = @At("TAIL"))
    private void bypassBlockSlowdown(BlockState pState, Vec3 pMotionMultiplier, CallbackInfo ci) {
        Entity entity = ((Entity) (Object) this);
        if (entity instanceof LivingEntity livingEntity) {
            CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
                if (handler.isEquipped(CuriositiesItems.GLOBETROTTERS_BADGE.value())) {
                    this.stuckSpeedMultiplier = Vec3.ZERO;
                    return;
                }

                if (handler.isEquipped(CuriositiesItems.SCOUTS_BADGE.value())) {
                    if (pState.is(Blocks.COBWEB) || pState.is(Blocks.SWEET_BERRY_BUSH)) {
                        double x = Math.min(1.0, this.stuckSpeedMultiplier.x * 2);
                        double y = Math.min(1.0, this.stuckSpeedMultiplier.y * 2);
                        double z = Math.min(1.0, this.stuckSpeedMultiplier.z * 2);
                        this.stuckSpeedMultiplier = new Vec3(x, y, z);
                    }
                }
            });
        }

    }


    @Redirect(method = "getBlockJumpFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getJumpFactor()F"))
    private float modifyJumpFactor(Block instance) {
        if ((((Entity) (Object) this)) instanceof LivingEntity livingEntity) {
            Optional<ICuriosItemHandler> handlerOpt = CuriosApi.getCuriosInventory(livingEntity);
            if (handlerOpt.isPresent()) {
                if (handlerOpt.get().isEquipped(CuriositiesItems.CONSTRUCTION_BOOTS.value())) return 1;
            }
        }
        return instance.getJumpFactor();
    }

    @Redirect(method = "getBlockSpeedFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getSpeedFactor()F"))
    private float modifySpeedFactor(Block instance) {
        if ((((Entity) (Object) this)) instanceof LivingEntity livingEntity) {
            Optional<ICuriosItemHandler> handlerOpt = CuriosApi.getCuriosInventory(livingEntity);
            if (handlerOpt.isPresent()) {
                if (handlerOpt.get().isEquipped(CuriositiesItems.CONSTRUCTION_BOOTS.value())) return 1;
            }
        }
        return instance.getSpeedFactor();
    }

}

package com.goo.curiosities.mixin;

import com.goo.curiosities.common.item.FoodModifyingCurioItem;
import com.goo.curiosities.common.item.curio.charm.OmnidirectionalMovementGear;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {



    @Redirect(
            method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"
            )
    )
    private void redirectConsume(ItemStack stack, int amount, LivingEntity entity) {
        if (entity instanceof Player player && FoodModifyingCurioItem.applyShouldConserveFood(player, stack)) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.containerMenu.broadcastChanges();
            }
            return;
        }

        // default vanilla behavior
        stack.consume(amount, entity);
    }


    @WrapOperation(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;jumpInFluid(Lnet/neoforged/neoforge/fluids/FluidType;)V"
            )
    )
    private void modifyJumpInFluid(LivingEntity instance, FluidType fluidType, Operation<Void> original) {
        boolean shouldCancel = CurioUtil.isWearingCurio(instance, CuriositiesItems.MINIATURE_ANCHOR.value());

        if (!shouldCancel) original.call(instance, fluidType);
    }

    @Inject(method = "handleDamageEvent", at = @At("HEAD"))
    private void onHandleDamageEvent(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity victim = (LivingEntity) (Object) this;
        if (victim.level().isClientSide()) {
            Entity attacker = damageSource.getEntity();

            if (attacker instanceof LivingEntity livingAttacker) {
                if (livingAttacker instanceof Player player) {
                    victim.setLastHurtByPlayer(player);
                }
                victim.setLastHurtByMob(livingAttacker);
                livingAttacker.setLastHurtMob(victim);
            }
        }
    }

    @WrapOperation(
            method = "handleRelativeFrictionAndCalculateMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getFrictionInfluencedSpeed(F)F"
            )
    )
    private float redirectAirSpeed(LivingEntity entity, float friction, Operation<Float> original) {
        // if airborne, return ground movement speed instead of default flyingSpeed (0.02F)
        if (!entity.onGround() && !entity.isInWater() && !entity.isPassenger()) {
            if (CurioUtil.isWearingCurio(entity, CuriositiesItems.MOVEMENT_GODS_TRACERS.value(), CuriositiesItems.MICRO_THRUSTERS.value()))
                return entity.getSpeed();
        }
        return original.call(entity, friction);
    }

    @WrapOperation(
            method = "jumpFromGround",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;addDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"
            )
    )
    private void overrideSprintJumpBoost(LivingEntity entity, Vec3 addition, Operation<Void> original) {
        Vec3 omniBoost = OmnidirectionalMovementGear.getOmnidirectionalJumpBoost(entity);

        if (omniBoost != null) {
            // apply omnidirectional jump boost vector instead of standard forward sprint vector
            original.call(entity, new Vec3(omniBoost.x, 0.0D, omniBoost.z));
        } else {
            // proceed with vanilla sprint jump addition
            original.call(entity, addition);
        }
    }

    @WrapOperation(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
                    ordinal = 0
            )
    )
    private boolean shouldTakeElytraDamage(LivingEntity entity, DamageSource source, float amount, Operation<Boolean> original) {
        if (CurioUtil.isWearingCurio(entity, CuriositiesItems.HEAD_CUSHION.value())) {
            return false;
        }
        return original.call(entity, source, amount);
    }

    @WrapOperation(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getFallDamageSound(I)Lnet/minecraft/sounds/SoundEvent;"
            )
    )
    private SoundEvent modifyElytraHitWallSound(LivingEntity instance, int height, Operation<SoundEvent> original) {
        if (CurioUtil.isWearingCurio(instance, CuriositiesItems.HEAD_CUSHION.value())) {
            return SoundEvents.WOOL_FALL;
        }

        return original.call(instance, height);
    }


    @ModifyReturnValue(
            method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z",
            at = @At("RETURN")
    )
    private boolean modifyCanAttack(boolean original, LivingEntity target) {
        LivingEntity livingEntity = (((LivingEntity) (Object) this));
        if (livingEntity instanceof IronGolem) {
            // only effective on golems
            if (CurioUtil.isWearingCurio(target, CuriositiesItems.VILLAGER_DICTIONARY.value())) {
                return false;
            }
        }
        return original;
    }




}

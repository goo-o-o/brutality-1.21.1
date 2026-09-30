package com.goo.curiosities.mixin;

import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {

    @WrapOperation(
        method = "useAmmo",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;hasInfiniteMaterials()Z"
        )
    )
    private static boolean overrideInfiniteMaterials(
        LivingEntity shooter,
        Operation<Boolean> original

    ) {
        if (CurioUtil.isWearingCurio(shooter, CuriositiesItems.INFINIQUIVER.value())) {
            return true;
        }

        return original.call(shooter);
    }

}
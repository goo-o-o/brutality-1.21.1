package com.goo.curiosities.mixin;

import com.goo.curiosities.common.item.FoodModifyingCurioItem;
import com.goo.goo_lib.util.EffectMarkupParser;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {


    @Inject(
            method = "getUseDuration(Lnet/minecraft/world/entity/LivingEntity;)I",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyFoodUseDuration(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (!(entity instanceof Player player)) return;

        ItemStack stack = (ItemStack) (Object) this;
        FoodProperties baseFood = stack.getFoodProperties(player);

        if (baseFood == null) return;

        FoodProperties modifiedFood = FoodModifyingCurioItem.applyModifyFoodProperties(player, baseFood, stack);

        if (modifiedFood != baseFood) {
            cir.setReturnValue(Math.max(1, modifiedFood.eatDurationTicks()));
        }
    }
}
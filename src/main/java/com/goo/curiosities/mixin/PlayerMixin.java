package com.goo.curiosities.mixin;

import com.goo.curiosities.common.item.FoodModifyingCurioItem;
import com.goo.curiosities.common.item.curio.hand.SuspiciouslyLargeHandle;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @ModifyExpressionValue(
            method = "isSleepingLongEnough",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Player;sleepCounter:I",
                    opcode = Opcodes.GETFIELD)
    )
    private int shadows$shorterSleepThreshold(int original) {
        Player player = (((Player) (Object) this));
        if (player.isSleeping() && player.getSleepTimer() >= 20) {
            if (CurioUtil.isWearingCurio(player, CuriositiesItems.COLD_PILLOW.value()))
                return 100; // return 100 early so game thinks we are in deep sleep
        }
        return original;
    }

    @ModifyVariable(
            method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
            at = @At("HEAD"),
            argsOnly = true
    )
    private FoodProperties curiosities$modifyPlayerFoodProperties(FoodProperties original, Level level, ItemStack food) {
        if (original == null) return null;

        Player player = (Player) (Object) this;
        return FoodModifyingCurioItem.applyModifyFoodProperties(player, original, food);
    }

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void modifyDisplayName(CallbackInfoReturnable<Component> cir) {
        if ((((Player) (Object) this)).hasEffect(CuriositiesEffects.REDACTED)) {
            cir.setReturnValue(Component.literal("████████████").withStyle(ChatFormatting.BLACK));
            cir.cancel();
        }
    }

    @ModifyReturnValue(
            method = "getCurrentItemAttackStrengthDelay",
            at = @At("RETURN")
    )
    private float modifyAttackStrengthDelay(float original) {
        Player player = (Player) (Object) this;

        if (CurioUtil.isWearingCurio(player, CuriositiesItems.SUSPICIOUSLY_LARGE_HANDLE.value())) {
            return (float) (1.0 / SuspiciouslyLargeHandle.BASE_ATTACK_SPEED * 20.0);
        }

        return original;
    }

}
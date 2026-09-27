package com.goo.curiosities.mixin;

import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArrowItem.class)
public class ArrowItemMixin {

    @ModifyReturnValue(method = "createArrow", at = @At("RETURN"))
    private AbstractArrow modifyCreatedArrow(
            AbstractArrow originalArrow,
            Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon
    ) {
        if (originalArrow instanceof Arrow arrow && shooter != null) {
            if (CurioUtil.isWearingCurio(shooter, CuriositiesItems.QUIVER_OF_ABSURDITY.value())) {

                BuiltInRegistries.MOB_EFFECT
                        .getRandom(level.getRandom())
                        .ifPresent(effectHolder -> {




                            int amplifier = level.getRandom().nextInt(2);

                            MobEffectInstance effectInstance = new MobEffectInstance(
                                    effectHolder,
                                    60,
                                    amplifier
                            );

                            arrow.addEffect(effectInstance);
                            if (shooter instanceof Player player) {
                                player.displayClientMessage(effectHolder.value().getDisplayName().copy().withColor(effectHolder.value().getColor()), true);
                            }
                        });
            }
        }

        return originalArrow;
    }
}
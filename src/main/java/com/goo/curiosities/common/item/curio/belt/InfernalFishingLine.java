package com.goo.curiosities.common.item.curio.belt;

import com.goo.curiosities.common.item.IHookedInEntityFishingCurioItem;
import com.goo.curiosities.common.item.TemperatureDependentFishingLuckModifyingCurio;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InfernalFishingLine extends TemperatureDependentFishingLuckModifyingCurio implements IHookedInEntityFishingCurioItem {
    public InfernalFishingLine(Properties properties) {
        super(properties);
    }

    @Override
    public float getFishingLuckBonus(ItemStack stack, Player player, @Nullable FishingHook hook) {
        float temperature = player.level().getBiome(player.blockPosition()).value().getTemperature(player.blockPosition());
        float currentTemp = player.level().dimensionType().ultraWarm() ? 2.0F : temperature;

        float bonus = Math.max(currentTemp * 2.0F, 0.0F);

        // doubled while fishing in lava
        if (hook != null && hook.isInLava()) {
            bonus *= 2.0F;
        }

        return bonus;
    }

    @Override
    public void onHookedTick(FishingHook hook, Player player, ItemStack stack) {
        if (hook.tickCount % 20 == 0 && !hook.level().isClientSide()) {
            if (hook.getHookedIn() != null) {
                Entity hooked = hook.getHookedIn();
                hooked.igniteForSeconds(2);
            }
        }
    }
}

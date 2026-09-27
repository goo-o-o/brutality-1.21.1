package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.AdditionalFishingLootCurio;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class EchoingFishingHook extends AdditionalFishingLootCurio {
    public EchoingFishingHook(Properties properties) {
        super(properties);
    }

    @Override
    public float getAdditionalChance(Player player, FishingHook hook, ItemStack rodStack) {
        return 0.5F;
    }

    @Override
    public int getAdditionalRolls(Player player, FishingHook hook, ItemStack rodStack) {
        return 1;
    }
}

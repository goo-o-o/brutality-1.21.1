package com.goo.curiosities.common.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

/**
 * Interface to apply effects to entities hooked by a Fishing Hook in curio form
 */
public interface IHookedInEntityFishingCurioItem {

    void onHookedTick(FishingHook hook, Player player, ItemStack stack);
}

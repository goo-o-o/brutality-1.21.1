package com.goo.curiosities.util;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Predicate;

public class CurioUtil {

    public static boolean ensureUnique(LivingEntity livingEntity, Class<? extends CuriositiesCurioItem> clazz) {
        return !CurioUtil.isWearingCurio(livingEntity, s -> clazz.isInstance(s.getItem()));
    }

    /**
     * Checks if the given living entity is wearing any of the specified curios.
     *
     * @param livingEntity the entity to check for equipped curios
     * @param curios       an array of items representing the curios to check
     * @return true if the entity is wearing any of the specified curios, false otherwise
     */
    public static boolean isWearingCurio(LivingEntity livingEntity, Item... curios) {
        return CuriosApi.getCuriosInventory(livingEntity).map(handler -> {
                    for (Item curio : curios) {
                        if (handler.isEquipped(curio)) return true;
                    }
                    return false;
                }
        ).orElse(false);
    }

    /**
     * Checks if the given LivingEntity is wearing all specified curios.
     *
     * @param livingEntity The entity to check for equipped curios.
     * @param curios       The array of curios to verify as equipped.
     * @return {@code true} if the entity has all the specified curios equipped, {@code false} otherwise.
     */
    public static boolean isWearingAllCurios(LivingEntity livingEntity, Item... curios) {
        return CuriosApi.getCuriosInventory(livingEntity).map(handler -> {
                    for (Item curio : curios) {
                        if (!handler.isEquipped(curio)) return false;
                    }
                    return true;
                }
        ).orElse(false);
    }

    /**
     * Checks if the specified living entity is wearing a curio that matches the given filter.
     *
     * @param livingEntity the living entity whose curio inventory is being checked
     * @param filter       a predicate to test each curio item against, returning true for a match
     * @return true if the living entity is wearing a curio that matches the filter, false otherwise
     */
    public static boolean isWearingCurio(LivingEntity livingEntity, Predicate<ItemStack> filter) {
        return CuriosApi.getCuriosInventory(livingEntity).map(handler -> handler.isEquipped(filter)
        ).orElse(false);
    }

    public static void validateCooldown(LivingEntity entity, Item item, float cooldownSeconds, Runnable runnable) {
        if (entity instanceof Player player)
            validateCooldown(player, item, (int) cooldownSeconds * 20, runnable);
        else
            runnable.run();
    }

    public static void validateCooldown(Player player, Item item, float cooldownSeconds, Runnable runnable) {
        validateCooldown(player, item, (int) cooldownSeconds * 20, runnable);
    }

    public static void validateCooldown(Player player, Item item, int cooldownTicks, Runnable runnable) {
        if (!player.getCooldowns().isOnCooldown(item)) {
            runnable.run();
            if (cooldownTicks > 0) {
                player.getCooldowns().addCooldown(item, cooldownTicks);
            }
        }
    }
}

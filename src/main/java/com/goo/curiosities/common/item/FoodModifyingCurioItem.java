package com.goo.curiosities.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public abstract class FoodModifyingCurioItem extends CuriositiesCurioItem {

    public FoodModifyingCurioItem(Properties properties) {
        super(properties);
    }

    /**
     * Override this in subclasses to return a modified FoodProperties instance.
     */
    public FoodProperties modifyFoodProperties(LivingEntity livingEntity, FoodProperties current, ItemStack curio, ItemStack food) {
        return current;
    }

    /**
     * Return true to conserve food, any curio that returns this as false immediately breaks the loop early
     */
    public boolean shouldConserveFood(LivingEntity livingEntity, ItemStack curio, ItemStack food) {
        if (livingEntity.level().isClientSide()) return false;

        // 25% chance to CONSERVE food (skip consumption)
        return livingEntity.getRandom().nextFloat() <= 0.25F;
    }

    public static FoodProperties applyModifyFoodProperties(LivingEntity livingEntity, FoodProperties original, ItemStack food) {
        AtomicReference<FoodProperties> result = new AtomicReference<>(original);

        CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
            handler.findCurios(stack -> stack.getItem() instanceof FoodModifyingCurioItem).forEach(slotResult -> {
                FoodProperties updated = ((FoodModifyingCurioItem) slotResult.stack().getItem())
                        .modifyFoodProperties(livingEntity, result.get(), slotResult.stack(), food);
                result.set(updated);
            });
        });

        return result.get();
    }

    public static boolean applyShouldConserveFood(LivingEntity livingEntity, ItemStack food) {
        AtomicBoolean shouldConserve = new AtomicBoolean(false);

        CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
            for (SlotResult slotResult : handler.findCurios(stack -> stack.getItem() instanceof FoodModifyingCurioItem)) {
                FoodModifyingCurioItem curioItem = (FoodModifyingCurioItem) slotResult.stack().getItem();
                if (curioItem.shouldConserveFood(livingEntity, slotResult.stack(), food)) {
                    shouldConserve.set(true);
                    break;
                }
            }
        });

        return shouldConserve.get();
    }
}
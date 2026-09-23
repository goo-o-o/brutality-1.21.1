package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.FoodModifyingCurioItem;
import com.goo.curiosities.common.item.curio.hand.GoldenFork;
import com.goo.curiosities.common.item.curio.necklace.PristineNapkin;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Predicate;

public class GourmandsDiningSet extends FoodModifyingCurioItem {
    // if this is equipped, don't allow any components to be equipped
    public static Predicate<LivingEntity> COMPONENT_PREDICATE = e -> !CurioUtil.isWearingCurio(e, stack -> stack.getItem() instanceof GourmandsDiningSet);

    public GourmandsDiningSet(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        CuriosApi.getCuriosInventory(slotContext.entity()).ifPresent(handler -> {
            handler.findCurios(s ->
                    s.getItem() instanceof GoldenFork ||
                    s.getItem() instanceof EmptyPlate ||
                    s.getItem() instanceof PristineNapkin)
                    .forEach(
                    slotResult -> {
                        handler.setEquippedCurio(slotResult.slotContext().identifier(), slotResult.slotContext().index(), ItemStack.EMPTY);
                        if (slotContext.entity() instanceof Player player) {
                            if (!player.getInventory().add(slotResult.stack())) {
                                player.drop(slotResult.stack(), false);
                            }
                        }
                    }
            );
        });
    }

    @Override
    public boolean shouldConserveFood(LivingEntity livingEntity, ItemStack curio, ItemStack food) {
        if (livingEntity.level().isClientSide()) return true;
        if (livingEntity.getRandom().nextFloat() <= 0.5F) {
            if (livingEntity instanceof ServerPlayer serverPlayer) serverPlayer.containerMenu.broadcastChanges();
            return true;
        }
        return false;
    }

    @Override
    public FoodProperties modifyFoodProperties(LivingEntity livingEntity, FoodProperties current, ItemStack curio, ItemStack food) {
        return new FoodProperties(
                (int) (current.nutrition() * 1.75F),
                current.saturation() * 1.75F,
                current.canAlwaysEat(),
                current.eatSeconds() * 0.5F,
                current.usingConvertsTo(),
                current.effects()
        );

    }
}

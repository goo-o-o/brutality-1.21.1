package com.goo.curiosities.common.event;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.attachments.MomentumComboData;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Iterator;

@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class BlockEvents {
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        Entity breaker = event.getBreaker();
        if (breaker instanceof LivingEntity livingEntity) {
            if (CurioUtil.isWearingCurio(livingEntity, CuriositiesItems.HAND_OF_CREATION.value())) {
                Iterator<ItemEntity> iterator = event.getDrops().iterator();
                while (iterator.hasNext()) {
                    ItemEntity itemEntity = iterator.next();
                    ItemStack stack = itemEntity.getItem();
                    if (breaker instanceof Player player) {
                        // add to inventory
                        if (player.getInventory().add(stack)) {
                            // if managed to add, remove from world
                            iterator.remove();
                        } else {
                            // not enough space but stack is modified to have correct count, just teleport
                            itemEntity.setItem(stack);
                            itemEntity.moveTo(player.getX(), player.getY(), player.getZ());
                        }
                    } else {
                        // non-players just teleport
                        itemEntity.moveTo(breaker.getX(), breaker.getY(), breaker.getZ());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        MomentumComboData.addCombo(event);
    }
}

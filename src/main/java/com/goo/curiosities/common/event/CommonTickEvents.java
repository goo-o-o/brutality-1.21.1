package com.goo.curiosities.common.event;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.util.FootstepTracker;
import com.goo.curiosities.util.SprintTracker;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Encompasses all Tick Events that are ran on both sides
 */
@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class CommonTickEvents {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            onLivingTick(livingEntity);
        }
    }

    public static void onLivingTick(LivingEntity livingEntity) {
        SprintTracker.tick(livingEntity);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        FootstepTracker.processEntityFootsteps(event.getEntity());
    }
}

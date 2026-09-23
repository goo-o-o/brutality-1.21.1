package com.goo.curiosities.common.event;

import com.goo.curiosities.client.render.ClientGlitchState;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.entity.goals.RegalEmeraldFollowGoal;
import com.goo.curiosities.util.FootstepTracker;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffectEngine;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

/**
 * Use for events that are for non-living entities, or more specifically, all entities, living-exclusive events should go in {@link net.minecraft.world.entity.LivingEntity}
 */
@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class EntityEvents {
    @SubscribeEvent
    public static void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            PassiveMobEffectEngine.clearEntity(livingEntity);
            if (livingEntity.level().isClientSide()) {
                ClientGlitchState.clear(livingEntity);
            }
        }

        if (event.getEntity() instanceof Player player) {
            if (player.level().isClientSide()) {
                FootstepTracker.remove(player);
            }
        }
    }
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            villager.goalSelector.addGoal(0, new RegalEmeraldFollowGoal(villager));
        }
    }

}

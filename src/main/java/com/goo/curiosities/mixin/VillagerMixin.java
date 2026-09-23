package com.goo.curiosities.mixin;

import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @WrapOperation(
            method = "onReputationEventFrom",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V"
            )
    )
    private void preventNegativeGossip(
            GossipContainer container,
            UUID targetId,
            GossipType gossipType,
            int value,
            Operation<Void> original,
            ReputationEventType type,
            Entity target
    ) {
        if (target instanceof Player player) {
            if ((gossipType == GossipType.MINOR_NEGATIVE
//                    || gossipType == GossipType.MAJOR_NEGATIVE
                    // just minor negatives
            )) {
                if (CurioUtil.isWearingCurio(player, CuriositiesItems.VILLAGER_DICTIONARY.value())) {
                    return; // dont do any reputation handling
                }
            }
        }
        original.call(container, targetId, gossipType, value);
    }

}
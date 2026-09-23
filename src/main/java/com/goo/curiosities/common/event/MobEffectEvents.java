package com.goo.curiosities.common.event;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.common.registry.CuriositiesItems;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class MobEffectEvents {

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        CuriositiesCurioItem.Hooks.applyOnWearerMobEffectAdded(entity, event);
        MobEffectInstance instance = event.getEffectInstance();

        if (instance.is(CuriositiesEffects.GLITCHED)) {
            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.getChunkSource().broadcastAndSend(
                        entity,
                        new ClientboundUpdateMobEffectPacket(entity.getId(), instance, true)
                );
            }
        }
    }

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        CuriositiesCurioItem.Hooks.applyOnWearerMobEffectApplicable(entity, event);

    }


    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance == null) return;

        CuriositiesCurioItem.Hooks.applyOnWearerMobEffectExpired(event.getEntity(), event);

        if (effectInstance.is(MobEffects.RAID_OMEN)) {
            if (event.getEntity() instanceof Player player) {
                ItemStack stack = CuriositiesItems.ANKLE_MONITOR.value().getDefaultInstance();
                if (!player.getInventory().add(stack)) {
                    player.drop(stack, false);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance == null) return;

        CuriositiesCurioItem.Hooks.applyOnWearerMobEffectRemoved(event.getEntity(), event);

    }

}
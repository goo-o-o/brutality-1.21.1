package com.goo.brutality.common.event;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.BrutalityServerConfig;
import com.goo.brutality.common.items.BrutalityCurioItem;
import com.goo.brutality.common.items.curio.charm.GrudgeTotem;
import com.goo.brutality.common.rage.RageHandler;
import com.goo.brutality.common.registry.BrutalityAttachments;
import com.goo.brutality.common.registry.BrutalityDamageTypes;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalityTags;
import com.goo.goo_lib.common.event.custom.FluidCollisionEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;

/**
 * Encompasses events that handle {@link LivingEntity}
 */
@EventBusSubscriber(modid = Brutality.MOD_ID)
public class LivingEvents {

    @SubscribeEvent
    public static void onLivingSwapItems(LivingSwapItemsEvent.Hands event) {
        // bypass logic for getItemBySlot so that our TwoHandedItems work
        if (BrutalityServerConfig.CONFIG.TWO_HANDED_DISABLES_OFFHAND.isTrue()) {
            if (event.getItemSwappedToOffHand().is(BrutalityTags.Items.TWO_HANDED)) {
                event.setCanceled(true);
            } else {
                if (event.getEntity() instanceof Player player) {
                    if (player.getInventory().offhand.getFirst().is(BrutalityTags.Items.TWO_HANDED)) {
                        event.setItemSwappedToMainHand(player.getInventory().offhand.getFirst());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFluidCollision(FluidCollisionEvent event) {
        // TODO: Add items
//        if (CurioUtils.isWearingCurio(event.getEntity(), BrutalityItems.WATER_WALKERS.get()) && event.fluidState().is(FluidTags.WATER)) {
//            event.setCanceled(true);
//        } else if (CurioUtils.isWearingCurio(event.getEntity(), BrutalityItems.LAVA_WALKERS.get()) && event.fluidState().is(FluidTags.LAVA)) {
//            event.setCanceled(true);
//        }
    }


    @SubscribeEvent
public static void onLivingKnockback(LivingKnockBackEvent event) {
        LivingEntity victim = event.getEntity();
        LivingEntity attacker = victim.getLastHurtByMob();

        if (attacker != null) {
            BrutalityCurioItem.Hooks.applyOnWearerKnockback(attacker, victim, event);
        }
        BrutalityCurioItem.Hooks.applyOnWearerKnockedBack(victim, attacker, event);
    }


    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        BrutalityCurioItem.Hooks.applyOnWearerFall(event);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        GrudgeTotem.tryProc(victim, event);
        DamageSource damageSource = event.getSource();
        if (victim.hasEffect(BrutalityEffects.ASURA_FORM)) {
            event.setCanceled(true);
        }

        Entity cause = damageSource.getEntity() != null ? damageSource.getEntity() : damageSource.getDirectEntity() != null ? damageSource.getDirectEntity() : null;
        BrutalityCurioItem.Hooks.applyOnWearerDeath(victim, cause, damageSource, event);
        if (cause instanceof LivingEntity killer) {
            BrutalityCurioItem.Hooks.applyOnWearerKill(killer, victim, damageSource, event);
        }

    }


    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        float amountHealed = event.getAmount();
        LivingEntity healer = event.getEntity();
        if (healer.hasEffect(BrutalityEffects.ASURA_FORM)) {
            healer.setData(BrutalityAttachments.PENDING_DAMAGE, healer.getData(BrutalityAttachments.PENDING_DAMAGE) - amountHealed);
            event.setAmount(0);
        }
        BrutalityCurioItem.Hooks.applyOnWearerHeal(event);
    }

    @SubscribeEvent
    public static void onLivingHurtPost(LivingDamageEvent.Post event) {
        LivingEntity victim = event.getEntity();
        DamageSource damageSource = event.getSource();

        if (damageSource.getEntity() instanceof LivingEntity attacker) {
            RageHandler.processDamageDealtAndTaken(attacker, event.getNewDamage());
        }

        RageHandler.processDamageDealtAndTaken(victim, event.getNewDamage());
    }

    @SubscribeEvent
    public static void onLivingHurtPre(LivingDamageEvent.Pre event) {
        LivingEntity victim = event.getEntity();
        DamageSource damageSource = event.getSource();


        if (damageSource.getEntity() instanceof LivingEntity attacker) {
            if (damageSource.is(DamageTypes.PLAYER_ATTACK) || damageSource.is(DamageTypes.MOB_ATTACK) || damageSource.is(DamageTypes.MOB_ATTACK_NO_AGGRO)) {
                BrutalityCurioItem.Hooks.applyOnWearerMeleeHit(attacker, victim, damageSource, event.getContainer());
            }
            BrutalityCurioItem.Hooks.applyOnWearerHit(attacker, victim, damageSource, event.getContainer());
        }
        BrutalityCurioItem.Hooks.applyOnWearerHurt(victim, damageSource, event.getContainer());


        if (victim.hasEffect(BrutalityEffects.ASURA_FORM)) {
            if (!damageSource.is(BrutalityDamageTypes.ASURA_FORM)) { // prevent double dipping
                float finalDamageTaken = event.getNewDamage();
                victim.setData(BrutalityAttachments.PENDING_DAMAGE, victim.getData(BrutalityAttachments.PENDING_DAMAGE) + finalDamageTaken);
                event.setNewDamage(0);
            }
        }
    }
}

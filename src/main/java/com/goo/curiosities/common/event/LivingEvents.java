package com.goo.curiosities.common.event;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.AnkletCurioItem;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.goo.goo_lib.common.event.custom.FluidCollisionEvent;
import com.goo.goo_lib.common.event.custom.LivingDodgeEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

/**
 * Encompasses events that handle {@link LivingEntity}
 */
@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class LivingEvents {

    @SubscribeEvent
    public static void onLivingFluidCollision(FluidCollisionEvent event) {
        if (CurioUtil.isWearingCurio(event.getEntity(), CuriositiesItems.WATER_WALKERS.value()) && event.fluidState().is(FluidTags.WATER)) {
            event.setCanceled(true);
        } else if (CurioUtil.isWearingCurio(event.getEntity(), CuriositiesItems.LAVA_WALKERS.value()) && event.fluidState().is(FluidTags.LAVA)) {
            event.setCanceled(true);
        }
    }


    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        LivingEntity victim = event.getEntity();
        LivingEntity attacker = victim.getLastHurtByMob();

        if (attacker != null) {
            CuriositiesCurioItem.Hooks.applyOnWearerKnockback(attacker, victim, event);
        }
        CuriositiesCurioItem.Hooks.applyOnWearerKnockedBack(victim, attacker, event);
    }


    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        CuriositiesCurioItem.Hooks.applyOnWearerFall(event);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        DamageSource damageSource = event.getSource();

        Entity cause = damageSource.getEntity() != null ? damageSource.getEntity() : damageSource.getDirectEntity() != null ? damageSource.getDirectEntity() : null;
        CuriositiesCurioItem.Hooks.applyOnWearerDeath(victim, cause, damageSource, event);
        if (cause instanceof LivingEntity killer) {
            CuriositiesCurioItem.Hooks.applyOnWearerKill(killer, victim, damageSource, event);
        }

    }


    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        CuriositiesCurioItem.Hooks.applyOnWearerHeal(event);
    }

    @SubscribeEvent
    public static void onLivingHurtPre(LivingDamageEvent.Pre event) {
        LivingEntity victim = event.getEntity();
        DamageSource damageSource = event.getSource();

        if (damageSource.getEntity() instanceof LivingEntity attacker) {
            if (damageSource.is(DamageTypes.PLAYER_ATTACK) || damageSource.is(DamageTypes.MOB_ATTACK) || damageSource.is(DamageTypes.MOB_ATTACK_NO_AGGRO)) {
                CuriositiesCurioItem.Hooks.applyOnWearerMeleeHit(attacker, victim, damageSource, event);
            }
            CuriositiesCurioItem.Hooks.applyOnWearerHit(attacker, victim, damageSource, event);
        }
        CuriositiesCurioItem.Hooks.applyOnWearerHurt(victim, damageSource, event);

    }

    @SubscribeEvent
    public static void onLivingIncomingHurt(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(CuriositiesEffects.IMPERVIOUS)) {
            if (!(event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.INDIRECT_MAGIC))) {
                event.setCanceled(true);
            }
        }
        Entity sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(CuriositiesEffects.GLITCHED)) {
                if (livingEntity.getRandom().nextFloat() <= 0.5) event.setCanceled(true);
            }
        }

        CuriositiesCurioItem.Hooks.applyOnWearerIncomingHurt(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onLivingDodge(LivingDodgeEvent.Post event) {
        AnkletCurioItem.applyOnWearerDodge(event);
    }

    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        // check if curio was replaced
        ItemStack from = event.getFrom();
        if (from.getItem() instanceof CuriositiesCurioItem curio) {
            // check if the item is still equipped
            boolean stillEquipped = CuriosApi.getCuriosInventory(entity)
                    .map(handler -> !handler.isEquipped(curio))
                    .orElse(false);

            // if no more curio of that type, remove
            if (!stillEquipped) {
                curio.purgePassives(entity);
            }
        }
    }


}

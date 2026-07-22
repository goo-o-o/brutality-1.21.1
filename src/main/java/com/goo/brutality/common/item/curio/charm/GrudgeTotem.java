package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityDataComponents;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.common.network.clientbound.DisplayItemActivationPayload;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class GrudgeTotem extends BrutalityRageCurioItem {
    private static final int MAX_VALUE = 100;

    public GrudgeTotem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Colors.GRENADIER;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getRage(stack) < MAX_VALUE;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        float ratio = getRage(stack) / (float) MAX_VALUE;

        float clampedRatio = Mth.clamp(ratio, 0.0F, 1.0F);

        return Math.round(clampedRatio * 13.0F);
    }

    /**
     * For any items that want to track Rage
     */
    public static float getRage(ItemStack stack) {
        return stack.getOrDefault(BrutalityDataComponents.RAGE, 0F);
    }

    public static void tryProc(LivingEntity livingEntity, LivingDeathEvent event) {
        if (livingEntity.hasEffect(BrutalityEffects.ENRAGED)) {
            Optional<ICuriosItemHandler> handlerOptional = CuriosApi.getCuriosInventory(livingEntity);
            if (handlerOptional.isPresent()) {
                Optional<SlotResult> curio = handlerOptional.get().findFirstCurio(BrutalityItems.Curio.Rage.GRUDGE_TOTEM.value());
                if (curio.isPresent()) {
                    ItemStack stack = curio.get().stack();
                    if (getRage(stack) >= MAX_VALUE) {
                        if (livingEntity instanceof ServerPlayer serverplayer) {
                            serverplayer.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING), 1);
                            CriteriaTriggers.USED_TOTEM.trigger(serverplayer, stack);
                            serverplayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);

                            PacketDistributor.sendToPlayer(serverplayer, new DisplayItemActivationPayload(stack));
                        }
                        livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.TOTEM_USE, livingEntity.getSoundSource());
                        livingEntity.setHealth(1.0F);
                        livingEntity.removeEffectsCuredBy(EffectCures.PROTECTED_BY_TOTEM);
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                        event.setCanceled(true);
                        setRage(stack, 0);

                    }
                }
            }
        }
    }

    public static void setRage(ItemStack stack, float rage) {
        stack.set(BrutalityDataComponents.RAGE, Math.min(MAX_VALUE, rage));
    }

    public static void modifyRage(ItemStack stack, float delta) {
        setRage(stack, getRage(stack) + delta);
    }

    @Override
    public void onPostGainRage(LivingEntity livingEntity, ItemStack curio, float amount) {
        modifyRage(curio, amount);
    }

}

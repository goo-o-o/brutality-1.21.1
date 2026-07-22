package com.goo.brutality.common.rage;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.registry.BrutalityKeyNames;
import com.goo.brutality.common.BrutalityServerConfig;
import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.networking.serverbound.TriggerRagePayload;
import com.goo.brutality.common.registry.*;
import com.goo.brutality.util.CurioUtil;
import com.goo.brutality.util.EntityUtil;
import com.goo.goo_lib.util.Easing;
import com.goo.goo_lib.util.screenshake.ScreenShakeUtil;
import com.goo.goo_lib.util.screenshake.ShakeInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

/**
 * Utility class for handling Rage-related mechanics and interactions in the game.
 * <p>
 * This includes applications of Rage gain, Rage value modifications, and triggering
 * critical Rage effects when thresholds are met.
 * </p>
 */
public class RageHandler {

    public static class RageContainer {
        private float rageGained;

        public RageContainer(float rageGained) {
            this.rageGained = rageGained;
        }

        public void multiplyRage(float multiplier) {
            rageGained *= multiplier;
        }

        public void setRageGained(float rageGained) {
            this.rageGained = rageGained;
        }

        public float getRageGained() {
            return rageGained;
        }
    }

    /**
     * Rage decay mechanic
     */
    public static void tickDown(Player player) {
        if (player.tickCount % 20 == 0 && !player.level().isClientSide()) {
            if (!CurioUtil.isWearingCurio(player, BrutalityItems.Curio.Rage.HEART_OF_DARKNESS.value())) {
                int ticksSinceLastHurt = player.tickCount - player.getLastHurtByMobTimestamp();
                int ticksSinceLastAttack = player.tickCount - player.getLastHurtMobTimestamp();

                boolean isOutOfCombat = (ticksSinceLastHurt < 0 || ticksSinceLastHurt > 100) && (ticksSinceLastAttack < 0 || ticksSinceLastAttack > 100);

                if (isOutOfCombat) {
                    List<LivingEntity> nearbyMobs = player.level().getNearbyEntities(
                            LivingEntity.class,
                            TargetingConditions.forCombat()
                                    .range(20)
                                    .selector(e -> !EntityUtil.isAlly(player, e) && (e instanceof Targeting targeting && targeting.getTarget() == player)),
                            player,
                            player.getBoundingBox().inflate(20)
                    );
                    isOutOfCombat = nearbyMobs.isEmpty();
                }

                if (isOutOfCombat && player.hasData(BrutalityAttachments.RAGE)) {
                    float rage = player.getData(BrutalityAttachments.RAGE);
                    if (rage > 0) {
                        player.setData(BrutalityAttachments.RAGE, rage - 1);
                    }
                }
            }
        }
    }

    /**
     * Processes damage taken by a player and applies rage gain mechanics
     * if the required conditions are met, such as specific equipment or effects.
     */
    public static void processDamageDealtAndTaken(LivingEntity livingEntity, float damage) {
        if (!(livingEntity instanceof Player player)) return;
        if (player.hasEffect(BrutalityEffects.ENRAGED)) return;
        if (player.hasEffect(BrutalityEffects.TRANQUILITY)) return;

        // TODO: might add a PlayerRageEvent in future
        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            List<SlotResult> rageSlots = handler.findCurios(s -> s.is(BrutalityTags.Items.RAGE_ITEMS));
            if (rageSlots.isEmpty()) return;

            RageContainer container = new RageContainer(damage);
            container.multiplyRage((float) player.getAttributeValue(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO));
            container.multiplyRage(BrutalityServerConfig.CONFIG.RAGE_GAIN_MULTIPLIER.get().floatValue());

            for (SlotResult rageSlot : rageSlots) {
                ItemStack stack = rageSlot.stack();
                BrutalityRageCurioItem rageCurioItem = ((BrutalityRageCurioItem) stack.getItem());
                rageCurioItem.onPreGainRage(livingEntity, stack, container);
            }

            float awarded = modifyRageAmount(player, container.getRageGained());

            for (SlotResult slotResult : rageSlots) {
                ItemStack stack = slotResult.stack();
                if (stack.getItem() instanceof BrutalityRageCurioItem rageItem) {
                    rageItem.onPostGainRage(player, stack, awarded);
                }
            }


        });
    }


    /**
     * Called whenever the {@link BrutalityKeyNames.Mappings#TRIGGER_RAGE} keybind is pressed.
     */
    public static void onPressRageKeymapping() {
        // handle logic on server to prevent cheating
        PacketDistributor.sendToServer(new TriggerRagePayload());
    }

    /**
     * Tries to trigger Rage after validating certain conditions
     */
    public static void tryTriggerRage(Player player) {
        if (getCurrentRagePercentage(player) >= 1) {
            if (CurioUtil.isWearingCurio(player, stack -> stack.is(BrutalityTags.Items.RAGE_ITEMS)))
                actuallyTriggerRage(player);
        }
    }


    /**
     * @return The amount of rage actually awarded
     */
    public static float modifyRageAmount(Player player, float amount) {
        float currentRage = player.getData(BrutalityAttachments.RAGE);
        float maxRage = (float) player.getAttributeValue(BrutalityAttributes.MAX_RAGE);

        float newRage = Mth.clamp(currentRage + amount, 0.0F, maxRage);

        player.setData(BrutalityAttachments.RAGE, newRage);

        return newRage - currentRage;
    }

    public static float getCurrentRagePercentage(Player player) {
        return (float) (player.getData(BrutalityAttachments.RAGE) / player.getAttributeValue(BrutalityAttributes.MAX_RAGE));
    }

    /**
     * Triggers the rage effect on the specified player. This method calculates the player's rage levels and duration
     * based on their current {@link BrutalityAttachments#RAGE} value and attributes. Upon activation, it applies
     * an {@link MobEffectInstance} of the {@link BrutalityEffects#ENRAGED} effect to the player.
     * The player's current rage value is reset after activation.
     */
    public static void actuallyTriggerRage(Player player) {
        float rage = player.getData(BrutalityAttachments.RAGE);
        int rageLevel = (int) (rage / 100); // 1 level every 100 rage

        double duration = player.getAttributeValue(BrutalityAttributes.ENRAGED_DURATION);
        rageLevel += (int) player.getAttributeValue(BrutalityAttributes.ENRAGED_LEVEL);

        player.addEffect(new MobEffectInstance(BrutalityEffects.ENRAGED, (int) duration * 20, rageLevel), player);
        player.setData(BrutalityAttachments.RAGE, 0F);

        // in case the event cancels or modifies it
        if (player.hasEffect(BrutalityEffects.ENRAGED)) {
            MobEffectInstance enragedInstance = player.getEffect(BrutalityEffects.ENRAGED);
            assert enragedInstance != null;
            double intensity = BrutalityClientConfig.CONFIG.ENRAGED_SCREEN_SHAKE_INTENSITY.getAsDouble();
            if (intensity > 0) {
                float bounds = (float) (Math.min(100, enragedInstance.getAmplifier() * 10) * intensity);
                float maxPitch = 2.0F;
                float maxYaw = 2.0F;
                float maxRoll = 1.5F;

                ScreenShakeUtil.addShake(ShakeInstance.builder()
                        .identifier("rage")
                        .durationTicks(enragedInstance.getDuration())
                        .fadeInTicks((int) (enragedInstance.getDuration() * 0.1F))
                        .fadeOutTicks((int) (enragedInstance.getDuration() * 0.1F))
                        .fadeInCurve(Easing.EASE_IN_SINE)
                        .fadeOutCurve(Easing.EASE_IN_SINE)
                        .speed((float) ((enragedInstance.getAmplifier() + 1) * 0.5F * intensity))
                        .bounds(bounds, bounds)
                        .rotation((float) (maxPitch * intensity), (float) (maxYaw * intensity), (float) (maxRoll * intensity))
                        .build());
            }

        }

        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.findCurios(s -> s.getItem() instanceof BrutalityRageCurioItem).forEach(slotResult -> {
                            ItemStack stack = slotResult.stack();
                            BrutalityRageCurioItem rageCurioItem = ((BrutalityRageCurioItem) stack.getItem());
                            rageCurioItem.onTriggerRage(player, stack);
                        }
                ));


    }


}

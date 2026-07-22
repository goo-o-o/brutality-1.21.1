package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.goo_lib.common.network.clientbound.ScreenShakePayload;
import com.goo.goo_lib.util.screenshake.ShakeInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class RampageClock extends BrutalityRageCurioItem {
    public RampageClock(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerKill(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDeathEvent event) {
        if (attacker.hasEffect(BrutalityEffects.ENRAGED)) {
            MobEffectInstance enragedInstance = attacker.getEffect(BrutalityEffects.ENRAGED);
            if (enragedInstance != null) {
                int newDuration = enragedInstance.getDuration() + 20;
                attacker.addEffect(new MobEffectInstance(BrutalityEffects.ENRAGED, newDuration, enragedInstance.getAmplifier()), attacker);
                double intensity = BrutalityClientConfig.CONFIG.ENRAGED_SCREEN_SHAKE_INTENSITY.getAsDouble();
                if (intensity > 0) {
                    float bounds = (float) (Math.min(100, enragedInstance.getAmplifier() * 10) * intensity);
                    float maxPitch = 2.0F;
                    float maxYaw = 2.0F;
                    float maxRoll = 1.5F;
                    if (attacker instanceof ServerPlayer serverPlayer) {
                        PacketDistributor.sendToPlayer(serverPlayer, new ScreenShakePayload(ShakeInstance.builder()
                                .identifier("rage")
                                .durationTicks(newDuration)
                                .speed((float) ((enragedInstance.getAmplifier() + 1) * 0.5F * intensity))
                                .bounds(bounds, bounds)
                                .rotation((float) (maxPitch * intensity), (float) (maxYaw * intensity), (float) (maxRoll * intensity))
                                .build()));
                    }
                }
            }
        }
    }
}

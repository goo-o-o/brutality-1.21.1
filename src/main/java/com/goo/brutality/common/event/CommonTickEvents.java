package com.goo.brutality.common.event;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * Encompasses all Tick Events that are ran on both sides
 */
@EventBusSubscriber(modid = Brutality.MOD_ID)
public class CommonTickEvents {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            onLivingTick(livingEntity);
        }
    }

    public static void onLivingTick(LivingEntity livingEntity) {
        if (livingEntity.hasEffect(BrutalityEffects.ASURA_FORM)) {
            for (int i = 0; i < Mth.randomBetweenInclusive(livingEntity.getRandom(), 2, 4); i++) {
                int angle = Mth.randomBetweenInclusive(livingEntity.getRandom(), 1, 360);
                float angleRad = angle * Mth.DEG_TO_RAD;

                float distance = livingEntity.getBbWidth();

                double dirX = Mth.cos(angleRad);
                double dirZ = Mth.sin(angleRad);

                double spawnX = livingEntity.getX() + (dirX * distance);
                double spawnY = livingEntity.getY();
                double spawnZ = livingEntity.getZ() + (dirZ * distance);

                double speed = 0.2;
                double velocityX = dirX * speed;
                double velocityY = 1;
                double velocityZ = dirZ * speed;

                livingEntity.level().addParticle(
                        BrutalityParticles.ASURA.get(),
                        spawnX, spawnY, spawnZ,
                        velocityX, velocityY, velocityZ
                );
            }
        }
    }
}

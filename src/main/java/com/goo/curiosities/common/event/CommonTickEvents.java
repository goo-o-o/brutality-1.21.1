package com.goo.curiosities.common.event;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.curio.feet.FlameWalkers;
import com.goo.curiosities.util.CurioUtil;
import com.goo.curiosities.util.FootstepTracker;
import com.goo.curiosities.util.SprintTracker;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
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
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            onLivingTick(livingEntity);
        }
    }

    public static void onLivingTick(LivingEntity livingEntity) {
        SprintTracker.tick(livingEntity);

        if (livingEntity.level().isClientSide()) {
            if (CurioUtil.isWearingCurio(livingEntity, stack -> stack.getItem() instanceof FlameWalkers)) {
                // spawn particles near feet level (y to y + 0.2)
                double x = livingEntity.getRandomX(0.6D);
                double y = livingEntity.getY() + (livingEntity.getRandom().nextDouble() * 0.2D);
                double z = livingEntity.getZ() + ((livingEntity.getRandom().nextDouble() - 0.5D) * 0.6D);

                // pass non-zero initial speed so EmberParticle's internal velocity physics activate
                double xd = (livingEntity.getRandom().nextDouble() - 0.5D) * 0.04D;
                double yd = 0.05D + (livingEntity.getRandom().nextDouble() * 0.03D);
                double zd = (livingEntity.getRandom().nextDouble() - 0.5D) * 0.04D;

                FlatParticleOption particleOption = new FlatParticleOption(
                        CuriositiesParticles.EMBER.get(),
                        Mth.nextFloat(livingEntity.getRandom(), 0.05F, 0.1F),
                        0,
                        0,
                        0
                );


                livingEntity.level().addParticle(
                        particleOption,
                        x, y, z,
                        xd, yd, zd
                );
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        FootstepTracker.processEntityFootsteps(event.getEntity());
    }
}

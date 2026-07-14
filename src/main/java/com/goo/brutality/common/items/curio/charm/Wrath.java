package com.goo.brutality.common.items.curio.charm;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Wrath extends BrutalityRageCurioItem {
    public Wrath(Properties properties) {
        super(properties);
    }

    @Override
    public void onTriggerRage(LivingEntity livingEntity, ItemStack curio) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            float maxRage = (float) livingEntity.getAttributeValue(BrutalityAttributes.MAX_RAGE);
            float size = maxRage / 25;
            FlatParticleOption flatParticleOption = new FlatParticleOption(BrutalityParticles.HEXING_CIRCLE.get(), size, 90, 0, 0);
            serverLevel.sendParticles(flatParticleOption, livingEntity.getX(), livingEntity.getY(0.01), livingEntity.getZ(),
                    1, 0, 0, 0, 0);


        }
    }
}

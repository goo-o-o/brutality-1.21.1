package com.goo.brutality.common.items.curio.belt;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.explosion.BloodExplosionDamageCalculator;
import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.common.registry.BrutalitySounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BloodboilBomb extends BrutalityRageCurioItem {
    public BloodboilBomb(Properties properties) {
        super(properties);
    }

    @Override
    public void onTriggerRage(LivingEntity livingEntity, ItemStack curio) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            float maxRage = (float) livingEntity.getAttributeValue(BrutalityAttributes.MAX_RAGE);
            float enragedLevel = (float) livingEntity.getAttributeValue(BrutalityAttributes.ENRAGED_LEVEL);

            float radius = maxRage * 0.01F + enragedLevel * 0.5F;

            serverLevel.explode(livingEntity, livingEntity.damageSources().explosion(livingEntity, livingEntity),
                    new BloodExplosionDamageCalculator(livingEntity), livingEntity.getX(), livingEntity.getY(0.5), livingEntity.getZ(),
                    radius, false, Level.ExplosionInteraction.BLOCK,
                    BrutalityParticles.BLOODSPLOSION.get(), BrutalityParticles.BLOODSPLOSION_EMITTER.get(),
                    BrutalitySounds.BLOOD_SPLATTER);
        }
    }
}

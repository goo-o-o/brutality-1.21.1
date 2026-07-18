package com.goo.brutality.common.items.curio.charm;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.entities.CylinderCollider;
import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.common.registry.BrutalityEntities;
import com.goo.brutality.util.EntityUtil;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Wrath extends BrutalityRageCurioItem {
    public static final int DURATION = 80;

    public Wrath(Properties properties) {
        super(properties);
    }

    @Override
    public void onTriggerRage(LivingEntity livingEntity, ItemStack curio) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            float maxRage = (float) livingEntity.getAttributeValue(BrutalityAttributes.MAX_RAGE);
            float enragedLevel = (float) livingEntity.getAttributeValue(BrutalityAttributes.ENRAGED_LEVEL);
            float radius = maxRage * 0.01F + enragedLevel;

            Double groundY = EntityUtil.getClosestGroundY(livingEntity, 10);

            if (groundY != null) {
                FlatParticleOption flatParticleOption = new FlatParticleOption(BrutalityParticles.HEXING_CIRCLE.get(), radius, 90, 0, 0);
                serverLevel.sendParticles(flatParticleOption, livingEntity.getX(), groundY + 0.01, livingEntity.getZ(),
                        1, 0, 0, 0, 0);

                CylinderCollider cylinderCollider = new CylinderCollider(BrutalityEntities.CYLINDER_COLLIDER.value(),
                        serverLevel, livingEntity, DURATION, 10, radius, 2) {
                    @Override
                    public void onEntityTicked(LivingEntity target) {
                        if (getOwner() instanceof LivingEntity livingEntity && !EntityUtil.isAlly(livingEntity, target)) {
                            target.hurt(target.damageSources().indirectMagic(getOwner(), getOwner()), 1);
                        }
                    }
                };
                cylinderCollider.setPos(livingEntity.getX(), groundY, livingEntity.getZ());
                     serverLevel.addFreshEntity(cylinderCollider);

            }
        }
    }
}

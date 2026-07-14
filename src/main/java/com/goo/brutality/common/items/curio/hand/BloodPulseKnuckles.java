package com.goo.brutality.common.items.curio.hand;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.explosion.BloodExplosionDamageCalculator;
import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalitySounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class BloodPulseKnuckles extends BrutalityRageCurioItem {
    public BloodPulseKnuckles(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerMeleeHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource damageSource, DamageContainer container) {
        if (attacker.hasEffect(BrutalityEffects.ENRAGED) && attacker.level() instanceof ServerLevel serverLevel) {
            serverLevel.explode(attacker, attacker.damageSources().explosion(attacker, attacker),
                    new BloodExplosionDamageCalculator(attacker), victim.getX(), victim.getY(0.5), victim.getZ(),
                    2.01F, false, Level.ExplosionInteraction.BLOCK,
                    BrutalityParticles.BLOODSPLOSION.get(), BrutalityParticles.BLOODSPLOSION_EMITTER.get(),
                    BrutalitySounds.BLOOD_SPLATTER);
        }
    }
}

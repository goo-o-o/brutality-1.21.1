package com.goo.curiosities.common.mob_effect;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.goo_lib.common.mob_effect.ClientSyncableEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;


public class FrozenEffect extends ClientSyncableEffect {
    public FrozenEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                Curiosities.loc("effect.frozen.debuff.speed"),
                -0.20D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                Curiosities.loc("effect.frozen.debuff.attack_speed"),
                -0.20D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
        livingEntity.playSound(SoundEvents.GLASS_BREAK, 1F, livingEntity.getRandom().nextFloat() * 0.4F + 0.8F);
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }


    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        RandomSource random = livingEntity.getRandom();
        for (int i = 0; i < random.nextIntBetweenInclusive(3, 5); i++) {
            livingEntity.level().addParticle(ParticleTypes.SNOWFLAKE,
                    livingEntity.getRandomX(0.5),
                    livingEntity.getY(0.5) + random.nextFloat() - 0.5F,
                    livingEntity.getRandomZ(0.5),
                    random.nextFloat() * 0.1 - 0.05,
                    random.nextFloat() * 0.1 - 0.05,
                    random.nextFloat() * 0.1 - 0.05
            );
        }

        int frozenEffectTicks = Objects.requireNonNull(livingEntity.getEffect(CuriositiesEffects.FROZEN)).getDuration();

        if (livingEntity.getTicksFrozen() < frozenEffectTicks) {
            livingEntity.setTicksFrozen(frozenEffectTicks);
        }

        return true;
    }
}

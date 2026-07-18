package com.goo.brutality.common.mob_effect;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.Styles;
import com.goo.goo_lib.util.screenshake.ScreenShakeUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class EnragedEffect extends ExpirableEffect {
    public EnragedEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                Brutality.loc("effect.enraged.movement_speed"),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                amp -> 0.2 + (0.1 * (amp - 1))
        );
        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                Brutality.loc("effect.enraged.attack_speed"),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                amp -> 0.2 + (0.1 * (amp - 1))
        );
        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                Brutality.loc("effect.enraged.attack_damage"),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                amp -> 0.5 + (0.05 * (amp - 1))
        );
        addAttributeModifier(
                Attributes.ARMOR,
                Brutality.loc("effect.enraged.armor"),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                amp -> Math.min(-1.0 + (0.1 * (amp - 1)), 0)
        );
        withSoundOnAdded(SoundEvents.ENDER_DRAGON_GROWL);// TODO: Add 1% chance of skeleton banging shield sound
    }

    @Override
    public @NotNull ParticleOptions createParticleOptions(MobEffectInstance effect) {
        return BrutalityParticles.ENRAGED.get();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return super.getDisplayName().copy().withStyle(Styles.Special.RAGE);
    }

    @Override
    public void onEffectRemoved(LivingEntity livingEntity, MobEffectInstance instance) {
        // this is if it ends early
        ScreenShakeUtil.removeShake("rage");    }


}

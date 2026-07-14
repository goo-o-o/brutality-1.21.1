package com.goo.brutality.common.registry;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.mob_effect.AsuraFormEffect;
import com.goo.brutality.util.Colors;
import com.goo.brutality.util.Styles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BrutalityEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Brutality.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> ENRAGED = MOB_EFFECTS.register("enraged", () ->
            new MobEffect(MobEffectCategory.NEUTRAL, Colors.RUSSIAN_RED) {
                @Override
                public @NotNull ParticleOptions createParticleOptions(MobEffectInstance effect) {
                    return BrutalityParticles.ENRAGED.get();
                }

                @Override
                public @NotNull Component getDisplayName() {
                    return super.getDisplayName().copy().withStyle(Styles.RAGE_STYLE);
                }
            }.addAttributeModifier(
                    Attributes.MOVEMENT_SPEED,
                    Brutality.loc("effect.enraged.movement_speed"),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                    amp -> 0.2 + (0.1 * (amp - 1))
            ).addAttributeModifier(
                    Attributes.ATTACK_SPEED,
                    Brutality.loc("effect.enraged.attack_speed"),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                    amp -> 0.2 + (0.1 * (amp - 1))
            ).addAttributeModifier(
                    Attributes.ATTACK_DAMAGE,
                    Brutality.loc("effect.enraged.attack_damage"),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                    amp -> 0.5 + (0.05 * (amp - 1))
            ).addAttributeModifier(
                    Attributes.ARMOR,
                    Brutality.loc("effect.enraged.armor"),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                    amp -> Math.min(-1.0 + (0.1 * (amp - 1)), 0)
            ).withSoundOnAdded(SoundEvents.ENDER_DRAGON_GROWL) // TODO: Add 1% chance of skeleton banging shield sound
    );

    public static final DeferredHolder<MobEffect, MobEffect> RUINED = MOB_EFFECTS.register("ruined", () ->
            new MobEffect(MobEffectCategory.BENEFICIAL, Colors.ELECTRIC_BLUE) {
                @Override
                public @NotNull Component getDisplayName() {
                    return super.getDisplayName().copy().withStyle(Styles.OCEANIC_STYLE);
                }
            }.addAttributeModifier(
                    Attributes.ATTACK_DAMAGE,
                    Brutality.loc("effect.ruined.attack_damage"),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                    amp -> 0.5 + (0.05 * (amp - 1))
            ).withSoundOnAdded(SoundEvents.SOUL_ESCAPE.value())
    );

    public static final DeferredHolder<MobEffect, MobEffect> TRANQUILITY = MOB_EFFECTS.register("tranquility", () ->
            new MobEffect(MobEffectCategory.NEUTRAL, new Color(255, 255, 255).getRGB()) {
                @Override
                public @NotNull ParticleOptions createParticleOptions(MobEffectInstance effect) {
                    return BrutalityParticles.TRANQUILITY.get();
                }
                @Override
                public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
                    super.onEffectAdded(livingEntity, amplifier);
                    livingEntity.removeEffect(BrutalityEffects.ENRAGED);
                }
            }.addAttributeModifier(
                    BrutalityAttributes.STEALTH,
                    Brutality.loc("effect.tranquility.stealth"),
                    AttributeModifier.Operation.ADD_VALUE,
                    amp -> 0.05 * amp
            ).addAttributeModifier(
                    Attributes.ARMOR,
                    Brutality.loc("effect.tranquility.armor"),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                    amp -> 0.05 * amp
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> ASURA_FORM = MOB_EFFECTS.register("asura_form", () ->
            new AsuraFormEffect(MobEffectCategory.NEUTRAL, Colors.DEEP_CARMINE_PINK)
    );

}

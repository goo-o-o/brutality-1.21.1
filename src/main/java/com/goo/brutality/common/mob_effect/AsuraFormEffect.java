package com.goo.brutality.common.mob_effect;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.*;
import com.goo.brutality.util.Styles;
import com.goo.goo_lib.common.registry.GLAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class AsuraFormEffect extends ExpirableEffect {
    public AsuraFormEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(
                BrutalityAttributes.DAMAGE_TAKEN,
                Brutality.loc("effect.asura_form.damage_taken"),
                2,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                Brutality.loc("effect.asura_form.attack_damage"),
                0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                Brutality.loc("effect.asura_form.attack_speed"),
                0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.ATTACK_KNOCKBACK,
                Brutality.loc("effect.asura_form.attack_knockback"),
                1.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                Brutality.loc("effect.asura_form.movement_speed"),
                0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.SCALE,
                Brutality.loc("effect.asura_form.scale"),
                0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        addAttributeModifier(
                Attributes.KNOCKBACK_RESISTANCE,
                Brutality.loc("effect.asura_form.knockback_resistance"),
                1,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                Attributes.STEP_HEIGHT,
                Brutality.loc("effect.asura_form.step_height"),
                1,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                Attributes.ENTITY_INTERACTION_RANGE,
                Brutality.loc("effect.asura_form.entity_interaction_range"),
                2,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                Attributes.EXPLOSION_KNOCKBACK_RESISTANCE,
                Brutality.loc("effect.asura_form.explosion_knockback_resistance"),
                1,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                GLAttributes.CLIMBING_SPEED_MODIFIER,
                Brutality.loc("effect.asura_form.climbing_speed_modifier"),
                1,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                GLAttributes.CRITICAL_DAMAGE,
                Brutality.loc("effect.asura_form.critical_damage"),
                1,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                BrutalityAttributes.STEALTH,
                Brutality.loc("effect.asura_form.stealth"),
                -1,
                AttributeModifier.Operation.ADD_VALUE
        );
        addAttributeModifier(
                BrutalityAttributes.DODGE_CHANCE,
                Brutality.loc("effect.asura_form.dodge_chance"),
                -1,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public @NotNull Component getDisplayName() {
        return super.getDisplayName().copy().withStyle(Styles.RAGE_STYLE);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), BrutalitySounds.ASURA_FORM.get(), livingEntity.getSoundSource(), 3F, 1);
    }

    @Override
    public void onEffectExpired(LivingEntity livingEntity, MobEffectInstance instance) {
        applyPendingDamage(livingEntity);
    }

    @Override
    public void onEffectRemoved(LivingEntity livingEntity, MobEffectInstance instance) {
        applyPendingDamage(livingEntity);
    }

    protected static void applyPendingDamage(LivingEntity livingEntity) {
        float pendingDamage = livingEntity.getData(BrutalityAttachments.PENDING_DAMAGE);
        if (pendingDamage > 0) {
            livingEntity.hurt(BrutalityDamageSources.asuraForm(livingEntity), pendingDamage);
            livingEntity.setData(BrutalityAttachments.PENDING_DAMAGE, 0F);
        }
    }
}

package com.goo.brutality.common.item.light_greatsword;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.entity.MistWraith;
import com.goo.brutality.common.registry.BrutalityEntities;
import com.goo.brutality.util.Colors;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class BladeOfTheRuinedKing extends SwordItem {
    public static MutableComponent MIST_WRAITH = Component.translatable("item." + Brutality.MOD_ID + ".blade_of_the_ruined_king.mist_wraith").withColor(Colors.ELECTRIC_BLUE);

    public BladeOfTheRuinedKing(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);

        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0), attacker);
        attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0), attacker);

        if (target.isDeadOrDying() && !attacker.level().isClientSide()) {
            MistWraith wraith = new MistWraith(BrutalityEntities.MIST_WRAITH.value(), attacker.level(), attacker, target);
            attacker.level().addFreshEntity(wraith);
        }

    }


    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        if (target instanceof LivingEntity livingEntity) {
            return (float) (livingEntity.getHealth() * 0.1);
        }
        return super.getAttackDamageBonus(target, damage, damageSource);
    }

}

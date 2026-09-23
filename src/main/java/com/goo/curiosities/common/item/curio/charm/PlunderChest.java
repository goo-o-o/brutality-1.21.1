package com.goo.curiosities.common.item.curio.charm;


import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class PlunderChest extends CuriositiesCurioItem {

    public PlunderChest(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerMeleeHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource damageSource, LivingDamageEvent.Pre event) {

        if (victim instanceof LivingEntity livingVictim)
            CurioUtil.validateCooldown(attacker, curio.getItem(), 5F, () -> {
                List<MobEffectInstance> stealable = livingVictim.getActiveEffects().stream()
                        .filter(e -> e.getEffect().value().isBeneficial() && !e.isInfiniteDuration())
                        .toList();

                if (!stealable.isEmpty()) {
                    MobEffectInstance target = stealable.get(attacker.getRandom().nextInt(stealable.size()));
                    livingVictim.removeEffect(target.getEffect());
                    // clone
                    attacker.addEffect(new MobEffectInstance(target));

                    if (attacker.getRandom().nextBoolean())
                        attacker.playSound(CuriositiesSounds.TREASURE_CHEST_LOCK.value(), 1.5F, 1.0F);
                    else
                        attacker.playSound(SoundEvents.CHEST_LOCKED, 1.5F, 1.0F);
                }
            });
    }
}

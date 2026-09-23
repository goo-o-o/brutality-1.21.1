package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class Espresso extends CuriositiesCurioItem {
    public Espresso(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(player, curio.getItem(), 180F, () -> {
            player.playSound(SoundEvents.GENERIC_DRINK);
            player.addEffect(new MobEffectInstance(CuriositiesEffects.CAFFEINATED, 120 * 20), player);
        });
    }
}

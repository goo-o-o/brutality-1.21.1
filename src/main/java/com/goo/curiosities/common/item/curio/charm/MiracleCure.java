package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MiracleCure extends CuriositiesCurioItem {
    public MiracleCure(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(
                player,
                curio.getItem(),
                20 * 60,
                () -> {
                    player.playSound(CuriositiesSounds.POP.value());

                    // collect negative effects to a separate list first
                    List<Holder<MobEffect>> effectsToRemove = new ArrayList<>();
                    for (MobEffectInstance effectInstance : player.getActiveEffects()) {
                        Holder<MobEffect> effect = effectInstance.getEffect();
                        if (!effect.value().isBeneficial()) {
                            effectsToRemove.add(effect);
                        }
                    }

                    // safely remove them afterwards
                    for (Holder<MobEffect> effect : effectsToRemove) {
                        player.removeEffect(effect);
                    }
                });
    }
}

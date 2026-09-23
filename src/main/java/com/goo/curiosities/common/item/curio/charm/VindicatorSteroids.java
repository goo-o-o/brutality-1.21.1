package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class VindicatorSteroids extends CuriositiesCurioItem {
    public VindicatorSteroids(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(
                player,
                curio.getItem(),
                20 * 45,
                () -> {
                    player.playSound(CuriositiesSounds.POP.value());
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200), player);
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200), player);
                });
    }
}

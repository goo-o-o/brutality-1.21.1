package com.goo.curiosities.common.item.curio.body;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NanoMachines extends CuriositiesCurioItem {
    public NanoMachines(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(player, curio.getItem(), 90 * 20, () ->
                player.addEffect(new MobEffectInstance(CuriositiesEffects.IMPERVIOUS, 60), player));
    }
}

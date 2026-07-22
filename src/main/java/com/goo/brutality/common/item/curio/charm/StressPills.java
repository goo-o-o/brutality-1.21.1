package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.rage.RageHandler;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.util.CurioUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StressPills extends BrutalityRageCurioItem {
    public static final int BASE_COOLDOWN_TICKS = 40 * 20;

    public StressPills(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(player, this, BASE_COOLDOWN_TICKS, () -> {
            double maxRage = player.getAttributeValue(BrutalityAttributes.MAX_RAGE);
            double amt = maxRage * 0.5;
            RageHandler.modifyRageAmount(player, (float) amt);
        });
    }
}

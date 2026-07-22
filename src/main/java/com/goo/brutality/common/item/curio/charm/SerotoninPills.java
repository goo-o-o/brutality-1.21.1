package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttachments;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.util.CurioUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SerotoninPills extends BrutalityRageCurioItem {
    public static int BASE_COOLDOWN_TICKS = 40 * 20;

    public SerotoninPills(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(player, this, BASE_COOLDOWN_TICKS, () -> {
            player.setData(BrutalityAttachments.RAGE, 0F);
            player.removeEffect(BrutalityEffects.ENRAGED);
            player.addEffect(new MobEffectInstance(BrutalityEffects.TRANQUILITY, 10 * 20, 4), player);
        });
    }
}

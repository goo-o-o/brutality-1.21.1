package com.goo.brutality.common.item.curio.heart;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.rage.RageHandler;
import com.goo.brutality.common.registry.BrutalityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class HeartOfDarkness extends BrutalityRageCurioItem {
    public HeartOfDarkness(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity.tickCount % 20 == 0 && entity instanceof Player player) {
            double totalRage = player.getAttributeValue(BrutalityAttributes.MAX_RAGE);
            RageHandler.modifyRageAmount(player, (float) (totalRage * 0.02));
        }
    }
}

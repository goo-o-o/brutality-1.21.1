package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.rage.RageHandler;
import com.goo.brutality.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class SpiteShard extends BrutalityRageCurioItem {
    public SpiteShard(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (!(entity instanceof Player player)) return;
        if (entity.tickCount % 20 == 0 && !entity.level().isClientSide()) {
            List<LivingEntity> foes = EntityUtil.getFoes(player, 5);


            if (!foes.isEmpty()) {
                RageHandler.modifyRageAmount(player, foes.size() / 2F);
            }
        }
    }
}

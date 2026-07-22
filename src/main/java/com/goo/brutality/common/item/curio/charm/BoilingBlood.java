package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.rage.RageHandler;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class BoilingBlood extends BrutalityRageCurioItem {
    public BoilingBlood(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, DamageContainer container) {
        if (source.is(DamageTypeTags.IS_FIRE) && wearer instanceof Player player) {
            RageHandler.modifyRageAmount(player, 2);
        }
    }


}

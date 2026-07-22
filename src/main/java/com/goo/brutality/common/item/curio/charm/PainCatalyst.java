package com.goo.brutality.common.item.curio.charm;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.rage.RageHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class PainCatalyst extends BrutalityRageCurioItem {
    public PainCatalyst(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, DamageContainer container) {
        float damage = container.getNewDamage();
        if (wearer instanceof Player player)
            RageHandler.modifyRageAmount(player, damage);
    }
}

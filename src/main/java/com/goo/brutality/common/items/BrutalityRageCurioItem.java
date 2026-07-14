package com.goo.brutality.common.items;

import com.goo.brutality.common.rage.RageHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BrutalityRageCurioItem extends BrutalityCurioItem {
    public BrutalityRageCurioItem(Properties properties) {
        super(properties);
    }

    public void onTriggerRage(LivingEntity livingEntity, ItemStack curio) {

    }

    /**
     * Modifiable entry point to Rage Gain, ran before Rage is given to the player
     */
    public void onPreGainRage(LivingEntity livingEntity, ItemStack curio, RageHandler.RageContainer container) {

    }

    /**
     * Called after Rage is given to the entity
     */
    public void onPostGainRage(LivingEntity livingEntity, ItemStack curio, float amount) {

    }
}

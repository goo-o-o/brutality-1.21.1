package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class WayOfTheWind extends CuriositiesCurioItem {

    public WayOfTheWind(Properties properties) {
        super(properties);
    }


    @Override
    public void onWearerIncomingHurt(LivingEntity wearer, ItemStack curio, LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypes.WIND_CHARGE))
            event.setCanceled(true);
    }
}

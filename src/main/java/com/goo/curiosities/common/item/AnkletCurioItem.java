package com.goo.curiosities.common.item;

import com.goo.goo_lib.common.event.custom.LivingDodgeEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import top.theillusivec4.curios.api.CuriosApi;

public class AnkletCurioItem extends CuriositiesCurioItem {
    public AnkletCurioItem(Properties properties) {
        super(properties);
    }

    /**
     * Internal: do not use
     */
    public static void applyOnWearerDodge(LivingDodgeEvent.Post event) {
        CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(handler ->
                handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                        ((AnkletCurioItem) result.stack().getItem()).onWearerDodge(event.getEntity(), event.getSource(), event.getFinalRoll(), event.getContainer(), result.stack())));
    }

    // might make this public
    protected void onWearerDodge(LivingEntity entity, DamageSource source, double roll, DamageContainer container, ItemStack stack) {

    }

}

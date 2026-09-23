package com.goo.curiosities.common.item.curio.belt;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class InnerTube extends CuriositiesCurioItem {
    public InnerTube(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity.isInWater() && !entity.isDescending()) {
            entity.addDeltaMovement(new Vec3(0, 0.15, 0));
            entity.setDeltaMovement(slotContext.entity().getDeltaMovement().multiply(1, 1.2, 1));
        }
    }
}

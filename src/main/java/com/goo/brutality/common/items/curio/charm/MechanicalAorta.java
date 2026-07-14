package com.goo.brutality.common.items.curio.charm;

import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class MechanicalAorta extends BrutalityRageCurioItem {
    public MechanicalAorta(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity.tickCount % 20 == 0 && entity.hasEffect(BrutalityEffects.ENRAGED))
            entity.hurt(entity.damageSources().magic(), 1);
    }
}

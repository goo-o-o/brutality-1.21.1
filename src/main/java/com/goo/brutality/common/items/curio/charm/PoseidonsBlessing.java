package com.goo.brutality.common.items.curio.charm;

import com.goo.brutality.common.items.BrutalityCurioItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class PoseidonsBlessing extends BrutalityCurioItem {

    public PoseidonsBlessing(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 40 == 0) {
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60));
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 60));
        }
    }
}

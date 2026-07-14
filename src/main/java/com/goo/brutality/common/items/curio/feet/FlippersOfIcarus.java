package com.goo.brutality.common.items.curio.feet;

import com.goo.brutality.common.BrutalityServerConfig;
import com.goo.brutality.common.items.BrutalityCurioItem;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class FlippersOfIcarus extends BrutalityCurioItem {
    public FlippersOfIcarus(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 60 == 0 &&
                slotContext.entity().getEyeY() >= BrutalityServerConfig.CONFIG.FLIPPERS_OF_ICARUS_BURN_HEIGHT.getAsInt() &&
                slotContext.entity().level().canSeeSky(slotContext.entity().blockPosition())) {
            slotContext.entity().igniteForSeconds(4);
        }
    }
}

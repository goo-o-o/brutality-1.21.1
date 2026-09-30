package com.goo.curiosities.common.item;

import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class QuiverCurioItem extends CuriositiesCurioItem{
    public QuiverCurioItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), QuiverCurioItem.class);
    }
}

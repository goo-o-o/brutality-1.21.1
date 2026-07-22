package com.goo.brutality.common.item;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.goo.brutality.util.TooltipUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BrutalityFunctionCurioItem extends BrutalityCurioItem{
    public BrutalityFunctionCurioItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(Component.translatable("tooltip." + Brutality.MOD_ID + ".math_curio_requirement",
                TooltipUtil.item(BrutalityItems.Curio.Math.SCIENTIFIC_CALCULATOR)).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return slotContext.entity().tickCount < 20 || CurioUtil.isWearingCurio(slotContext.entity(), BrutalityItems.Curio.Math.SCIENTIFIC_CALCULATOR.value());
    }
}

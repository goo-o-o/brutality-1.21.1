package com.goo.curiosities.common.item.curio.necklace;

import com.goo.curiosities.common.item.FoodModifyingCurioItem;
import com.goo.curiosities.common.item.curio.charm.GourmandsDiningSet;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class PristineNapkin extends FoodModifyingCurioItem {
    public PristineNapkin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), this.getClass()) && GourmandsDiningSet.COMPONENT_PREDICATE.test(slotContext.entity());
    }


    @Override
    public FoodProperties modifyFoodProperties(LivingEntity livingEntity, FoodProperties current, ItemStack curio, ItemStack food) {
        return new FoodProperties(
                (int) (current.nutrition() * 1.5F),
                current.saturation() * 1.5F,
                current.canAlwaysEat(),
                current.eatSeconds(),
                current.usingConvertsTo(),
                current.effects()
        );

    }
}

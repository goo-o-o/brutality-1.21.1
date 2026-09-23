package com.goo.curiosities.common.item.curio.body;

import com.goo.curiosities.common.item.FoodModifyingCurioItem;
import com.goo.curiosities.common.item.curio.charm.GourmandsDiningSet;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class IronGut extends FoodModifyingCurioItem {
    public IronGut(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), this.getClass());
    }

    @Override
    public FoodProperties modifyFoodProperties(LivingEntity livingEntity, FoodProperties current, ItemStack curio, ItemStack food) {
        List<FoodProperties.PossibleEffect> possibleEffects = current.effects();
        // all non-harmful effects
        List<FoodProperties.PossibleEffect> cleansedEffects = possibleEffects.stream().filter(effect -> effect.effect().getEffect().value().isBeneficial()).toList();
        if (cleansedEffects.size() < possibleEffects.size() || food.is(Tags.Items.FOODS_RAW_MEAT) || food.is(Tags.Items.FOODS_RAW_FISH) || food.is(Tags.Items.FOODS_FOOD_POISONING)) {
            // at least 1 harmful was removed

            return new FoodProperties(
                    current.nutrition() * 2,
                    current.saturation() * 2,
                    current.canAlwaysEat(),
                    current.eatSeconds(),
                    current.usingConvertsTo(),
                    cleansedEffects
            );
        }

        return current;
    }
}

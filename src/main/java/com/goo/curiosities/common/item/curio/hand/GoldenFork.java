package com.goo.curiosities.common.item.curio.hand;

import com.goo.curiosities.common.item.FoodModifyingCurioItem;
import com.goo.curiosities.common.item.curio.charm.GourmandsDiningSet;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class GoldenFork extends FoodModifyingCurioItem {
    public GoldenFork(Properties properties) {
        super(properties);
    }

    // sprite recolored from https://www.planetminecraft.com/texture-pack/brush-to-fork-22w07a-spapshot/, thank you!

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), this.getClass()) && GourmandsDiningSet.COMPONENT_PREDICATE.test(slotContext.entity());
    }

    @Override
    public boolean shouldConserveFood(LivingEntity livingEntity, ItemStack curio, ItemStack food) {
        if (livingEntity.level().isClientSide()) return true;
        if (livingEntity.getRandom().nextFloat() <= 0.25F) {
            if (livingEntity instanceof ServerPlayer serverPlayer) serverPlayer.containerMenu.broadcastChanges();
            return true;
        }
        return false;
    }
}

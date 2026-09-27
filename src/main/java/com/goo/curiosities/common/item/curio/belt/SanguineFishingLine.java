package com.goo.curiosities.common.item.curio.belt;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.item.IHookedInEntityFishingCurioItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class SanguineFishingLine extends CuriositiesCurioItem implements IHookedInEntityFishingCurioItem {
    public SanguineFishingLine(Properties properties) {
        super(properties);
    }

    @Override
    public void onHookedTick(FishingHook hook, Player player, ItemStack stack) {
        if (hook.tickCount % 20 == 0 && !hook.level().isClientSide()) {
            if (hook.getHookedIn() != null) {
                Entity hooked = hook.getHookedIn();

                if (hooked.hurt(hooked.damageSources().indirectMagic(player, hook), 1)) {
                    player.heal(1);
                }
            }
        }
    }
}

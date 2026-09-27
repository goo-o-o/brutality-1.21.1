package com.goo.curiosities.common.item.curio.belt;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.item.IHookedInEntityFishingCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class FrostfireFishingLine extends CuriositiesCurioItem implements IHookedInEntityFishingCurioItem {
    public FrostfireFishingLine(Properties properties) {
        super(properties);
    }

    @Override
    public void onHookedTick(FishingHook hook, Player player, ItemStack stack) {
        if (hook.tickCount % 20 == 0 && !hook.level().isClientSide()) {
            if (hook.getHookedIn() != null) {
                Entity hooked = hook.getHookedIn();
                hooked.igniteForSeconds(2);
            }
            if (hook.getHookedIn() instanceof LivingEntity hooked) {
                hooked.addEffect(new MobEffectInstance(CuriositiesEffects.FROZEN, 40, 0), player);
            }
        }
    }
}

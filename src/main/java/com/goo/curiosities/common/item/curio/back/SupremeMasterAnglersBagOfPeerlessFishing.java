package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.AdditionalFishingLootCurio;
import com.goo.curiosities.common.item.IFishingBobberCurioItem;
import com.goo.curiosities.common.item.IHookedInEntityFishingCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class SupremeMasterAnglersBagOfPeerlessFishing extends AdditionalFishingLootCurio implements IHookedInEntityFishingCurioItem, IFishingBobberCurioItem {
    public SupremeMasterAnglersBagOfPeerlessFishing(Properties properties) {
        super(properties);
    }

    @Override
    public void onHookedTick(FishingHook hook, Player player, ItemStack stack) {
        if (hook.tickCount % 20 == 0 && !hook.level().isClientSide()) {
            if (hook.getHookedIn() != null) {
                Entity hooked = hook.getHookedIn();
                hooked.igniteForSeconds(2);
                if (hooked.hurt(hooked.damageSources().indirectMagic(player, hook), 1)) {
                    player.heal(1);
                }
            }
            if (hook.getHookedIn() instanceof LivingEntity hooked) {
                hooked.addEffect(new MobEffectInstance(CuriositiesEffects.FROZEN, 40, 0), player);
            }
        }
    }

    @Override
    public float getAdditionalChance(Player player, FishingHook hook, ItemStack rodStack) {
        return 0.75F;
    }

    @Override
    public int getAdditionalRolls(Player player, FishingHook hook, ItemStack rodStack) {
        return 2;
    }
}

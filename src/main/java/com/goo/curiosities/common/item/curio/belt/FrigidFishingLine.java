package com.goo.curiosities.common.item.curio.belt;

import com.goo.curiosities.common.item.IHookedInEntityFishingCurioItem;
import com.goo.curiosities.common.item.TemperatureDependentFishingLuckModifyingCurio;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FrigidFishingLine extends TemperatureDependentFishingLuckModifyingCurio implements IHookedInEntityFishingCurioItem {
    public FrigidFishingLine(Properties properties) {
        super(properties);
    }

    @Override
    public float getFishingLuckBonus(ItemStack stack, Player player, @Nullable FishingHook hook) {
        float temperature = player.level().getBiome(player.blockPosition()).value().getTemperature(player.blockPosition());

        if (temperature < 0.15F) {
            float coldIntensity = (0.15F - temperature) / 0.85F;
            return Math.max(coldIntensity * 4.0F, 0.0F);
        }
        return 0.0F;
    }


    @Override
    public void onHookedTick(FishingHook hook, Player player, ItemStack stack) {
        if (hook.tickCount % 20 == 0 && !hook.level().isClientSide()) {
            if (hook.getHookedIn() instanceof LivingEntity hooked) {
                hooked.addEffect(new MobEffectInstance(CuriositiesEffects.FROZEN, 40, 0), player);
            }
        }
    }
}

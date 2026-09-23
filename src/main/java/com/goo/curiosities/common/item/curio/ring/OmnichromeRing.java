package com.goo.curiosities.common.item.curio.ring;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class OmnichromeRing extends CuriositiesCurioItem {
    public OmnichromeRing(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.unconditional(MobEffects.WATER_BREATHING, 0),
                PassiveMobEffect.unconditional(MobEffects.DOLPHINS_GRACE, 0),
                PassiveMobEffect.unconditional(MobEffects.SLOW_FALLING, 0),
                PassiveMobEffect.unconditional(MobEffects.FIRE_RESISTANCE, 0)
        );
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public void onWearerMobEffectApplicable(LivingEntity wearer, ItemStack curio, MobEffectEvent.Applicable event) {
        if (event.getEffectInstance().is(MobEffects.POISON)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }
}

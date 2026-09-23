package com.goo.curiosities.common.item;

import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import top.theillusivec4.curios.api.SlotContext;

public class MobEffectNullifyingCurioItem extends CuriositiesCurioItem {
    // inoculum - 10% chance to nullify debuffs
    // the vaccine - 25% chance to nullify debuffs
    private final float chance;

    public MobEffectNullifyingCurioItem(Properties properties, float chance) {
        super(properties);
        this.chance = chance;
    }


    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), MobEffectNullifyingCurioItem.class);
    }

    @Override
    public void onWearerMobEffectApplicable(LivingEntity wearer, ItemStack curio, MobEffectEvent.Applicable event) {
        float roll = wearer.getRandom().nextFloat();
        if (event.getEffectInstance().getEffect().value().isBeneficial()) return;
        if (roll <= chance) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }
}

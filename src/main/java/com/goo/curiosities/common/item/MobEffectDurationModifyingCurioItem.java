package com.goo.curiosities.common.item;

import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import top.theillusivec4.curios.api.SlotContext;

public class MobEffectDurationModifyingCurioItem extends CuriositiesCurioItem {

    private final float durationMultiplier;
    private final boolean affectsDebuffs;

    public MobEffectDurationModifyingCurioItem(Properties properties, float durationMultiplier, boolean affectsDebuffs) {
        super(properties);
        this.durationMultiplier = durationMultiplier;
        this.affectsDebuffs = affectsDebuffs;
    }


    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), MobEffectDurationModifyingCurioItem.class);
    }

    @Override
    public void onWearerMobEffectAdded(LivingEntity wearer, ItemStack curio, MobEffectEvent.Added event) {
        boolean isBeneficial = event.getEffectInstance().getEffect().value().isBeneficial();

        if (affectsDebuffs || isBeneficial) {
            event.getEffectInstance().duration *= (int) (isBeneficial ? (1 + durationMultiplier) : (1 - durationMultiplier));
        }
    }
}

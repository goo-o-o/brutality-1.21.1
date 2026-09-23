package com.goo.curiosities.common.item;

import com.goo.curiosities.common.item.curio.charm.GourmandsDiningSet;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import top.theillusivec4.curios.api.SlotContext;

public class DoctorsPrescription extends CuriositiesCurioItem {
    // sprite adapted from rappenem's reforge, mainly colors, go check it out its goated
    public DoctorsPrescription(Properties properties) {
        super(properties);
    }


    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), this.getClass());
    }

    @Override
    public void onWearerMobEffectAdded(LivingEntity wearer, ItemStack curio, MobEffectEvent.Added event) {
        boolean isBeneficial = event.getEffectInstance().getEffect().value().isBeneficial();

        if (isBeneficial) {
            event.getEffectInstance().amplifier += 1;
        }
    }
}

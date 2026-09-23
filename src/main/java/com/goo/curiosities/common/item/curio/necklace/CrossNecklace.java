package com.goo.curiosities.common.item.curio.necklace;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

public class CrossNecklace extends CuriositiesCurioItem {
    public CrossNecklace(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), CrossNecklace.class);
    }
}

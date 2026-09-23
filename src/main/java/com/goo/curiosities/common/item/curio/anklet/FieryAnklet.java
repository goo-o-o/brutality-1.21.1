package com.goo.curiosities.common.item.curio.anklet;

import com.goo.curiosities.common.item.AnkletCurioItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class FieryAnklet extends AnkletCurioItem {
    public FieryAnklet(Properties properties) {
        super(properties);
    }

    @Override
    protected void onWearerDodge(LivingEntity entity, DamageSource source, double roll, DamageContainer container, ItemStack stack) {
        if (source.getEntity() != null) {
            source.getEntity().igniteForSeconds(3);
        }
    }
}

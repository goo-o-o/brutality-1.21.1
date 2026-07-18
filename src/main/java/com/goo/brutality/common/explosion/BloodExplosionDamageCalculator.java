package com.goo.brutality.common.explosion;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.EntityUtil;
import com.goo.brutality.util.Styles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;

public class BloodExplosionDamageCalculator extends ExplosionDamageCalculator {
    public static final MutableComponent BLOOD_EXPLOSION = Component.translatable("tooltip." + Brutality.MOD_ID + ".blood_explosion").withStyle(Styles.Special.STYGIAN);
    protected final Entity source;

    public BloodExplosionDamageCalculator(Entity source) {
        this.source = source;
    }

    @Override
    public float getEntityDamageAmount(Explosion explosion, Entity entity) {
        float amount = super.getEntityDamageAmount(explosion, entity) / 5;
        if (source instanceof LivingEntity livingEntity) {
            livingEntity.heal(amount * 0.1F);
        }
        return amount;
    }

    @Override
    public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && source instanceof LivingEntity livingSource) {
            return !EntityUtil.isAlly(livingEntity, livingSource);
        }
        return true;
    }

    @Override
    public boolean shouldBlockExplode(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, float power) {
        return false;
    }
}

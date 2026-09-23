package com.goo.curiosities.common.entity;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class HoneyGlobuleEntity extends GlobuleEntity {
    private final int dropletIndex;
    private final int puddleIndex;

    public HoneyGlobuleEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
        this.dropletIndex = level.getRandom().nextIntBetweenInclusive(0, 2);
        this.puddleIndex = level.getRandom().nextIntBetweenInclusive(0, 1);
    }

    @Override
    public ResourceLocation getDropletTexture() {
        return Curiosities.loc("textures/entity/honey_globule/droplet_" + dropletIndex + ".png");
    }

    @Override
    public ResourceLocation getPuddleTexture() {
        return Curiosities.loc("textures/entity/honey_globule/puddle_" + puddleIndex + ".png");
    }


    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.isInGround()) {
            this.playSound(
                    CuriositiesSounds.GOOPY_SLIME.get(),
                    1.0F,
                    Mth.nextFloat(this.getRandom(), 0.8F, 1.2F)
            );
        }

        super.onHitBlock(result);

    }

    @Override
    public void onPuddleTick(LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1));
    }

    @Override
    public float getMinRadius() {
        return 1.5F;
    }

    @Override
    public float getMaxRadius() {
        return 3F;
    }

    @Override
    public int getPuddleLifespan() {
        return 200;
    }
}

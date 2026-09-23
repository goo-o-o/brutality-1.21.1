package com.goo.curiosities.common.entity;

import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class GlobuleEntity extends ThrowableProjectile {

    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(GlobuleEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_IN_GROUND = SynchedEntityData.defineId(GlobuleEntity.class, EntityDataSerializers.BOOLEAN);

    @Getter
    private int clientGroundTicks = 0;

    public GlobuleEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
        if (!level.isClientSide()) {
            // set random radius between min and max range on server initialization
            this.setRadius(Mth.nextFloat(this.getRandom(), getMinRadius(), getMaxRadius()));
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_RADIUS, 1.0F);
        builder.define(DATA_IN_GROUND, false);
    }

    public boolean isInGround() {
        return this.entityData.get(DATA_IN_GROUND);
    }

    public void setInGround(boolean inGround) {
        this.entityData.set(DATA_IN_GROUND, inGround);
    }

    public float getRadius() {
        return this.entityData.get(DATA_RADIUS);
    }

    public void setRadius(float radius) {
        this.entityData.set(DATA_RADIUS, radius);
    }

    public float getMinRadius() { return 0.5F; }
    public float getMaxRadius() { return 1.5F; }

    public abstract ResourceLocation getDropletTexture();
    public abstract ResourceLocation getPuddleTexture();

    public int getPuddleLifespan() {
        return 100;
    }

    public int getRemainingPuddleTicks() {
        return Math.max(0, getPuddleLifespan() - this.tickCount);
    }
    
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        this.setDeltaMovement(Vec3.ZERO);
        this.setInGround(true);
        this.hasImpulse = true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            if (this.isInGround()) {
                this.clientGroundTicks++;
            }
        }

        if (this.isInGround()) {
            if (this.tickCount % getPuddleTickInterval() == 0) {
                float radius = getRadius();
                AABB area = this.getBoundingBox().inflate(radius, 0, radius);
                List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, area);

                for (LivingEntity entity : targets) {
                    onPuddleTick(entity);
                }
            }

            if (this.tickCount >= getPuddleLifespan()) {
                this.discard();
            }
        }
    }

    public int getPuddleTickInterval() {
        return 20;
    }

    public abstract void onPuddleTick(LivingEntity livingEntity);

    public ResourceLocation getTexture() {
        return this.isInGround() ? getPuddleTexture() : getDropletTexture();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.02;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("inGround", this.isInGround());
        compound.putFloat("radius", this.getRadius());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setInGround(compound.getBoolean("inGround"));
        this.setRadius(compound.getFloat("radius"));
    }
}
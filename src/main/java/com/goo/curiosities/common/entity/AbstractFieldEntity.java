package com.goo.curiosities.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public abstract class AbstractFieldEntity extends Entity {
    private static final EntityDataAccessor<Float> RADIUS =
        SynchedEntityData.defineId(AbstractFieldEntity.class, EntityDataSerializers.FLOAT);

    private float oRadius = 0.0F;
    protected float startRadius;

    public AbstractFieldEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.startRadius = 5;
    }

    public AbstractFieldEntity(EntityType<?> type, Level level, float startRadius) {
        super(type, level);
        this.noPhysics = true;
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(RADIUS, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        this.oRadius = this.entityData.get(RADIUS);

        float newRadius = calculateRadius();
        setRadius(newRadius);

        this.setBoundingBox(new AABB(
                getX() - newRadius, getY() - newRadius, getZ() - newRadius,
                getX() + newRadius, getY() + newRadius, getZ() + newRadius
        ));

        if (getLifetime() - tickCount <= 0) {
            discard();
        }
    }

    protected abstract float calculateRadius();

    public abstract int getLifetime();

    public float getRadius(float partialTick) {
        return Mth.lerp(partialTick, this.oRadius, this.entityData.get(RADIUS));
    }

    public void setRadius(float radius) {
        this.entityData.set(RADIUS, radius);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Radius")) {
            setRadius(compound.getFloat("Radius"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putFloat("Radius", getRadius(0));
    }
}
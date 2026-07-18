package com.goo.brutality.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CylinderCollider extends Entity implements TraceableEntity {
    private final LivingEntity owner;
    private final int lifetime, interval;
    private final float radius, height;

    public CylinderCollider(EntityType<?> entityType, Level level, LivingEntity owner, int lifetime, int interval, float radius, float height) {
        super(entityType, level);
        this.owner = owner;
        this.lifetime = lifetime;
        this.interval = interval;
        this.radius = radius;
        this.height = height;
        this.noPhysics = true;
    }

    public CylinderCollider(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.owner = null;
        this.lifetime = 20;
        this.interval = 20;
        this.height = 1;
        this.radius = 1;
        this.noPhysics = true;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) return;

        if (this.tickCount >= this.lifetime || this.owner == null || !this.owner.isAlive()) {
            this.discard();
            return;
        }

        if (this.tickCount % this.interval == 0) {
            // broad-phase scan using a flat bounding box extended horizontally by the radius
            AABB searchArea = this.getBoundingBox().inflate(this.radius, 0.0, this.radius).expandTowards(0, height, 0);
            List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, searchArea,
                    entity -> entity != this.owner && entity.isAlive());

            double radiusSq = this.radius * this.radius;

            for (LivingEntity target : targets) {
                // narrow-phase 2D cylinder check (ignores Y difference within the AABB height bounds)
                double dx = target.getX() - this.getX();
                double dz = target.getZ() - this.getZ();

                if ((dx * dx) + (dz * dz) <= radiusSq) {
                    this.onEntityTicked(target);
                }
            }
        }
    }

    public void onEntityTicked(LivingEntity target) {
    }

    @Override
    public boolean shouldShowName() { return false; }
    @Override
    public boolean shouldBeSaved() { return false; }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}
    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}
    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public @Nullable Entity getOwner() {
        return this.owner;
    }
}

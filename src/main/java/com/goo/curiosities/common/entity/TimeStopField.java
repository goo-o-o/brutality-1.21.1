package com.goo.curiosities.common.entity;

import com.goo.curiosities.common.ClientProxy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TimeStopField extends AbstractFieldEntity implements TraceableEntity {

    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
            SynchedEntityData.defineId(TimeStopField.class, EntityDataSerializers.OPTIONAL_UUID);

    @Nullable
    private Entity cachedOwner;

    public TimeStopField(EntityType<?> type, Level level, float startRadius) {
        super(type, level, startRadius);
    }

    public TimeStopField(EntityType<?> type, Level level) {
        super(type, level);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(OWNER_UUID, Optional.empty());
    }

    public void setOwner(@Nullable Entity owner) {
        this.cachedOwner = owner;
        if (owner != null) {
            this.entityData.set(OWNER_UUID, Optional.of(owner.getUUID()));
        } else {
            this.entityData.set(OWNER_UUID, Optional.empty());
        }
    }

    @Override
    @Nullable
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        }

        Optional<UUID> uuidOpt = this.entityData.get(OWNER_UUID);
        if (uuidOpt.isEmpty()) {
            return null;
        }

        UUID uuid = uuidOpt.get();

        if (this.level() instanceof ServerLevel serverLevel) {
            this.cachedOwner = serverLevel.getEntity(uuid);
        } else {
            this.cachedOwner = ClientProxy.getEntity(uuid);
        }

        return this.cachedOwner;
    }

    @Override
    protected float calculateRadius() {
        int fadeOutTicks = 10;
        int ticksLeft = getLifetime() - this.tickCount;

        // handle fade-out phase
        if (ticksLeft <= fadeOutTicks) {
            float radiusDecrementPerTick = startRadius / (float) fadeOutTicks;
            return Math.max(0.0F, ticksLeft * radiusDecrementPerTick);
        }

        // initial radius
        return Math.min((float) this.tickCount, startRadius);
    }


    public int getLifetime() {
        return 200;
    }


    public static boolean shouldBeAffected(Level level, Vec3 vec3) {
        List<TimeStopField> timeStopFields = level.getEntitiesOfClass(TimeStopField.class, new AABB(vec3, vec3).inflate(25));

        for (TimeStopField field : timeStopFields) {
            double r = field.getRadius(0);
            if (vec3.distanceToSqr(field.position()) <= r * r)
                return true;

        }
        return false;
    }

    public static boolean shouldBeAffected(Entity target) {
        Level level = target.level();
        if (target instanceof TimeStopField) return false;
        // 100 block radius should be enough
        List<TimeStopField> timeStopFields = level.getEntitiesOfClass(TimeStopField.class, target.getBoundingBox().inflate(25));

        for (TimeStopField field : timeStopFields) {
            double r = field.getRadius(0);
            if (target.distanceToSqr(field.position()) <= r * r)
                if (field.getOwner() != target)
                    return true;

        }
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("Owner")) {
            this.entityData.set(OWNER_UUID, Optional.of(compound.getUUID("Owner")));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.entityData.get(OWNER_UUID).ifPresent(uuid -> compound.putUUID("Owner", uuid));
    }
}
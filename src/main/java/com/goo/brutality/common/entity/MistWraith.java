package com.goo.brutality.common.entity;

import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.goo_lib.util.MobEffectUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class MistWraith extends Entity implements GeoEntity, TraceableEntity {
    private static final int LIFETIME = 20 * 10;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Nullable
    private UUID owner;
    @Nullable
    private Entity cachedOwner;

    public MistWraith(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;

        push(
                Mth.nextFloat(getRandom(), -0.1F, 0.1F),
                Mth.nextFloat(getRandom(), 0.4F, 0.75F),
                Mth.nextFloat(getRandom(), -0.1F, 0.1F)
        );

        setNoGravity(true);
    }

    public MistWraith(EntityType<?> entityType, Level level, LivingEntity owner, LivingEntity victim) {
        this(entityType, level);
        this.cachedOwner = owner;
        this.owner = owner.getUUID();

        setPos(victim.getRopeHoldPosition(0));

        float initialPitch = Mth.nextInt(level.getRandom(), 0, 360);
        float initialYaw = Mth.nextInt(level.getRandom(), 0, 360);

        this.setXRot(initialPitch);
        this.setYRot(initialYaw);
        this.xRotO = initialPitch;
        this.yRotO = initialYaw;

    }


    @Override
    public void tick() {
        super.tick();

        if (this.tickCount > LIFETIME) {
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, getX(), getY(0.5), getZ(),
                        10, 0.25, 0.25, 0.25, 0.1);
                playSound(SoundEvents.SOUL_ESCAPE.value());
            }
            this.discard();
            return;
        }

        if (!this.level().isClientSide) {
            Entity ownerEntity = getOwner();
            if (ownerEntity != null) {
                Vec3 targetPos = ownerEntity.getRopeHoldPosition(0);
                Vec3 targetDirection = targetPos.subtract(this.position()).normalize();

                double targetSpeed = Math.min(0.4, tickCount * 0.02);
                Vec3 desiredVelocity = targetDirection.scale(targetSpeed);

                Vec3 smoothedVelocity = getDeltaMovement().lerp(desiredVelocity, 0.1D);
                this.setDeltaMovement(smoothedVelocity);
            }


            // update standard server-side tracking parameters
            updateProjectileRotation();
            this.hasImpulse = true;
        } else {
            // on the client, smoothly follow the velocity sent down by the server
            updateProjectileRotation();
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    /**
     * Custom rotation logic mimicking vanilla Projectile#updateRotation
     * ensures client side interpolation history syncs seamlessly
     */
    private void updateProjectileRotation() {
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
        Vec3 motion = this.getDeltaMovement();
        double horizontalDistance = motion.horizontalDistance();

        // only update if moving to prevent zero-vector turning glitches
        if (horizontalDistance > 1.0E-5D) {
            float targetPitch = (float) (Mth.atan2(motion.y, horizontalDistance) * (180.0D / Math.PI));
            float targetYaw = (float) (Mth.atan2(motion.x, motion.z) * (180.0D / Math.PI));

            // smoothly lerp existing rotation states to matching vectors
            this.setXRot(Mth.rotLerp(0.2F, this.xRotO, targetPitch));
            this.setYRot(Mth.rotLerp(0.2F, this.yRotO, targetYaw));

        }

    }

    @Override
    public void playerTouch(Player player) {
        if (!this.level().isClientSide && player == getOwner()) {
            Vec3 targetCenter = player.position().add(0, player.getBbHeight() * 0.5D, 0);

            if (this.position().closerThan(targetCenter, player.getBbWidth())) {

                MobEffectUtil.modifyEffect(player, player,
                        BrutalityEffects.RUINED, new MobEffectUtil.ModValue(60, true), new MobEffectUtil.ModValue(1, false),
                        255,
                        living -> living.addEffect(new MobEffectInstance(BrutalityEffects.RUINED, 60, 0), living),
                        null
                );


                playSound(SoundEvents.SOUL_ESCAPE.value());
                ((ServerLevel) level()).sendParticles(ParticleTypes.SCULK_SOUL, getX(), getY(0.5), getZ(),
                        10, 0.25, 0.25, 0.25, 0.1);
                this.discard();
            }
        }
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.owner = compound.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (this.owner != null) {
            compound.putUUID("Owner", this.owner);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.owner != null && this.level() instanceof ServerLevel serverlevel) {
            this.cachedOwner = serverlevel.getEntity(this.owner);
            return this.cachedOwner;
        } else {
            return null;
        }
    }
}
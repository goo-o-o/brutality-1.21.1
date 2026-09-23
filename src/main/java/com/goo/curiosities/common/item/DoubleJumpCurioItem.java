package com.goo.curiosities.common.item;

import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public abstract class DoubleJumpCurioItem extends CuriositiesCurioItem {
    public DoubleJumpCurioItem(Properties properties) {
        super(properties);
    }

    // 100% acceleration multiplier = 1x speed boost per tick
    protected double getHorizontalAccelerationMultiplier(LivingEntity livingEntity) {
        return 1.0;
    }

    // 100% max horizontal speed multiplier relative to ground speed
    protected double getHorizontalSpeedModifier(LivingEntity livingEntity) {
        return 1.0;
    }

    // initial upward boost
    protected double getBaseInitialBoost(LivingEntity livingEntity) {
        return 0.75;
    }

    protected double getFinalInitialBoost(LivingEntity livingEntity) {
        if (CurioUtil.isWearingCurio(livingEntity, CuriositiesItems.SHINY_RED_BALLOON.value())) {
            return getBaseInitialBoost(livingEntity) * 2.0F;
        }

        return getBaseInitialBoost(livingEntity);
    }

    /**
     * How long the total jump ticks should be, 0 for instant
     *
     * @param livingEntity
     * @return
     */
    public int getTotalDoubleJumpTicks(LivingEntity livingEntity) {
        return 0;
    }

    /**
     * Called on both sides when double jump starts
     *
     * @param livingEntity
     */
    public void onWearerStartDoubleJump(LivingEntity livingEntity) {
        livingEntity.playSound(CuriositiesSounds.DOUBLE_JUMP.value(), 1.0F, 1.0F);
        Vec3 current = livingEntity.getDeltaMovement();

        double horizontalAccelerationMultiplier = getHorizontalAccelerationMultiplier(livingEntity);
        double initialBoost = getBaseInitialBoost(livingEntity);

        double newX = current.x * horizontalAccelerationMultiplier;
        double newZ = current.z * horizontalAccelerationMultiplier;

        livingEntity.setDeltaMovement(newX, current.y > 0 ? current.y + initialBoost : initialBoost, newZ);
    }

    /**
     * Called on both sides when double jump ends
     *
     * @param livingEntity
     */
    public void onWearerEndDoubleJump(LivingEntity livingEntity) {
        livingEntity.resetFallDistance();
    }

    /**
     * Called every tick on both sides, server might be 1 or 2 ticks behind
     *
     * @param livingEntity
     * @param ticks
     */
    public void onWearerDoubleJumpTick(LivingEntity livingEntity, int ticks) {
    }


    /**
     * Calculates the player's 200% max speed cap based on their MOVEMENT_SPEED attribute
     * and clamps the horizontal velocity vector.
     */
    protected Vec3 applyHorizontalCap(LivingEntity entity, double x, double z, double y) {
        // base movement speed attribute (default is 0.1 for players)
        double baseAttributeSpeed = entity.getAttributeValue(Attributes.MOVEMENT_SPEED);

        // vanilla sprinting adds a ~30% multiplier (1.3x) to base movement speed
        double sprintingFactor = entity.isSprinting() ? 1.3 : 1.0;

        // 200% multiplier applied to the player's calculated ground movement speed
        double maxAllowedSpeed = baseAttributeSpeed * sprintingFactor * getHorizontalSpeedModifier(entity);

        double currentHorizontalSpeed = Math.hypot(x, z);
        if (currentHorizontalSpeed > maxAllowedSpeed) {
            double factor = maxAllowedSpeed / currentHorizontalSpeed;
            x *= factor;
            z *= factor;
        }

        return new Vec3(x, y, z);
    }
}

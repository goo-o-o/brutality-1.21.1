package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class OmnidirectionalMovementGear extends CuriositiesCurioItem {
    public OmnidirectionalMovementGear(Properties properties) {
        super(properties);
    }

    private static boolean canUse(LivingEntity entity) {
        return CurioUtil.isWearingCurio(entity, CuriositiesItems.OMNIDIRECTIONAL_MOVEMENT_GEAR.value(), CuriositiesItems.MOVEMENT_GODS_TRACERS.value());
    }

    /**
     * Determines if the player is providing sufficient movement input to maintain an active sprint.
     * <p>
     * This handles the "continuous check" that happens every tick while a player is already sprinting.
     * If the Omnidirectional Gear is equipped, the player can maintain a sprint while moving in any
     * direction (including backwards or sideways). Otherwise, the player must maintain forward
     * momentum to keep sprinting.
     * </p>
     *
     * @param player The local client-side player.
     * @param input  The current movement input (keyboard or controller state).
     * @return {@code true} if the current movement inputs satisfy the requirements to keep sprinting.
     */
    @OnlyIn(Dist.CLIENT)
    public static boolean handleOmnidirectionalImpulseToMaintainSprint(LocalPlayer player, Input input) {
        if (canUse(player)) {
            return input.forwardImpulse != 0.0F || input.leftImpulse != 0.0F;
        }
        return input.forwardImpulse > 1.0E-5F;
    }


    /**
     * Calculates a 3D world-space velocity boost for jumping, based on the entity's
     * internal movement inputs (strafe and forward/back).
     * By default, Minecraft's jump boost only applies to the "Forward" vector.
     * This method intercepts that logic to allow for "Omni-directional" boosts,
     * enabling strafe-jumping and backward-jumping with consistent momentum.
     * * <b>Mathematical Logic:</b>
     * <ol>
     * <li>The method checks for the {@link CuriositiesItems#OMNIDIRECTIONAL_MOVEMENT_GEAR} in a Curios slot.</li>
     * <li>It captures the local input variables {@code xxa} (strafe) and {@code zza} (forward).</li>
     * <li>It transforms these local inputs into world-space X and Z coordinates using the
     * entity's current Y-Rotation (Yaw).</li>
     * <li>The resulting vector is normalized to prevent "diagonal speedup" and scaled
     * by a fixed constant (0.2D).</li>
     * </ol>
     * *
     *
     * @param entity The {@link LivingEntity} performing the jump.
     * @return A {@link Vec3} containing the X and Z velocity additions to be applied;
     * returns {@code null} if no custom gear is equipped or no movement input
     * is detected, signaling that vanilla logic should proceed.
     * * @see net.minecraft.world.entity.LivingEntity#jumpFromGround()
     */
    @Nullable
    public static Vec3 getOmnidirectionalJumpBoost(LivingEntity entity) {
        if (canUse(entity)) {

            // movement inputs
            float strafe = entity.xxa;  // left (+), right (-)
            float forward = entity.zza; // forward (+), back (-)
            double magnitudeSq = strafe * strafe + forward * forward;

            // epsilon check to prevent Division by Zero or boosts from a standstill
            if (magnitudeSq > 1.0E-6D) {
                float yaw = entity.getYRot() * ((float) Math.PI / 180F);
                float sinYaw = Mth.sin(yaw);
                float cosYaw = Mth.cos(yaw);

                // transform local impulse into world-space delta
                double boostX = (strafe * cosYaw - forward * sinYaw);
                double boostZ = (forward * cosYaw + strafe * sinYaw);

                // normalize so diagonal jump is the same as forward jump
                double magnitude = Math.sqrt(magnitudeSq);

                // vanilla scales by 0.2, so we match
                return new Vec3(boostX / magnitude, 0, boostZ / magnitude).scale(0.2D);
            }
        }

        // allow mixin to fall through to other logic
        return null;
    }

}

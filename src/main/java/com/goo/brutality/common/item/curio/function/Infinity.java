package com.goo.brutality.common.item.curio.function;

import com.goo.brutality.common.item.BrutalityFunctionCurioItem;
import com.goo.brutality.util.EntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class Infinity extends BrutalityFunctionCurioItem {
    private static final double EFFECT_RADIUS = 5.0;   // total forcefield boundary

    public Infinity(Properties properties) {
        super(properties);
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity wearer = slotContext.entity();
        Level level = wearer.level();

        AABB area = wearer.getBoundingBox().inflate(EFFECT_RADIUS);
        List<Entity> nearbyEntities = level.getEntities(
                wearer,
                area,
                e -> !e.isSpectator() && e.isAlive()
        );

        for (Entity target : nearbyEntities) {
            if (target.isPassengerOfSameVehicle(wearer)) {
                continue;
            }

            double distance = target.distanceTo(wearer);
            if (distance >= EFFECT_RADIUS || distance <= 0.001) {
                continue;
            }

            Vec3 playerPos = wearer.position();
            Vec3 targetPos = target.position();
            Vec3 awayDir = targetPos.subtract(playerPos).normalize(); // outward vector from player to entity

            // all entites should slow down first of all
            boolean shouldPush = true;

            // living entities only get slowed when they are not allies
            if (target instanceof LivingEntity living) {
                if (EntityUtil.isAlly(wearer, living)) {
                    shouldPush = false;
                }
            }

            if (shouldPush) {
                double pushFactor = (1.0 - (distance / EFFECT_RADIUS)) * 0.5;

                Vec3 currentVel = target.getDeltaMovement();
                Vec3 appliedForce = awayDir.scale(pushFactor);

                target.setDeltaMovement(currentVel.add(appliedForce));
                target.hasImpulse = true;

                if (wearer.tickCount % 10 == 0 && target.horizontalCollision) {
                    // scale damage based on proximity: 0% at outer radius, 100% at center
                    double proximityRatio = 1.0 - (distance / EFFECT_RADIUS); // 0.0 to 1.0

                    float damage = (float) (proximityRatio * 5.0); // 0 to 5 damage

                    target.hurt(target.damageSources().flyIntoWall(), damage);
                }
            }

            // projectiles only get slowed if moving towards player
            if (target instanceof Projectile) {
                Vec3 currentVel = target.getDeltaMovement();
                Vec3 dirToPlayer = awayDir.reverse();

                double approachSpeed = currentVel.dot(dirToPlayer);

                // only apply forcefield if the projectile is actively heading toward the player
                if (approachSpeed > 0) {
                    // closeness ratio: 1.0 at center, 0.0 at outer boundary edge
                    double closeness = 1.0 - (distance / EFFECT_RADIUS);

                    // separate inward velocity from gravity/sideways motion
                    Vec3 inwardVel = dirToPlayer.scale(approachSpeed);
                    Vec3 perpendicularVel = currentVel.subtract(inwardVel);

                    // damp inward approach vector smoothly (drops exponentially closer to player)
                    double slowFactor = Math.max(Math.pow(1.0 - closeness, 2.0), 0.05);
                    Vec3 slowedInwardVel = inwardVel.scale(slowFactor);

                    // apply a smooth, scaling outward push vector as it gets closer
                    double outwardPush = closeness * 0.35;
                    Vec3 pushVel = awayDir.scale(outwardPush);

                    // combine perpendicular motion (gravity/side velocity) + slowed inward + outward push
                    Vec3 finalVel = perpendicularVel.add(slowedInwardVel).add(pushVel);

                    target.setDeltaMovement(finalVel);
                    target.hasImpulse = true;
                }
            }
        }
    }
}

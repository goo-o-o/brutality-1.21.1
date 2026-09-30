package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.EntityUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class MagneticArrowhead extends CuriositiesCurioItem {
    public MagneticArrowhead(Properties properties) {
        super(properties);
    }

    public static void home(Projectile projectile) {
        if (projectile instanceof AbstractArrow abstractArrow) {
            if (abstractArrow.inGround || abstractArrow.isNoPhysics()) return;

            Entity owner = abstractArrow.getOwner();
            if (!(owner instanceof LivingEntity shooter)) return;
            int magneticArrowheads = CuriosApi.getCuriosInventory(shooter).map(handler -> handler.findCurios(CuriositiesItems.MAGNETIC_ARROWHEAD.value()).size()).orElse(0);
            if (magneticArrowheads > 0) {

                List<Entity> potentialTargets = abstractArrow.level().getEntities(
                        abstractArrow.getOwner(),
                        abstractArrow.getBoundingBox().inflate(8),
                        e -> !EntityUtil.isAlly(abstractArrow.getOwner(), e) && e.isAlive() && !(e instanceof Projectile)
                );

                double d0 = -1.0;
                Entity nearest = null;

                for (Entity t1 : potentialTargets) {
                    double d1 = t1.distanceToSqr(abstractArrow.getX(), abstractArrow.getY(), abstractArrow.getZ());
                    if (d0 == -1.0 || d1 < d0) {
                        d0 = d1;
                        nearest = t1;
                    }
                }

                if (nearest != null) {
                    if (abstractArrow.getBoundingBox().inflate(0.2).intersects(nearest.getBoundingBox())) return;

                    Vec3 targetPos = nearest.position().add(0, nearest.getBbHeight() / 2.0, 0);
                    Vec3 idealDir = targetPos.subtract(abstractArrow.position()).normalize();

                    double currentSpeed = abstractArrow.getDeltaMovement().length();
                    if (currentSpeed < 0.1) return;

                    Vec3 newMovement = abstractArrow.getDeltaMovement().add(idealDir.scale(magneticArrowheads)).normalize().scale(currentSpeed);
                    abstractArrow.setDeltaMovement(newMovement);
                    abstractArrow.hasImpulse = true;

                    float targetYRot = (float) (Mth.atan2(newMovement.x, newMovement.z) * (180F / Math.PI));
                    float targetXRot = (float) (Mth.atan2(newMovement.y, newMovement.horizontalDistance()) * (180F / Math.PI));

                    float lerpFactor = 0.25F;

                    float smoothedYRot = Mth.rotLerp(lerpFactor, projectile.getYRot(), targetYRot);
                    float smoothedXRot = Mth.rotLerp(lerpFactor, projectile.getXRot(), targetXRot);

                    projectile.setYRot(smoothedYRot);
                    projectile.setXRot(smoothedXRot);

                    projectile.yRotO = smoothedYRot;
                    projectile.xRotO = smoothedXRot;
                }
            }
        }
    }
}

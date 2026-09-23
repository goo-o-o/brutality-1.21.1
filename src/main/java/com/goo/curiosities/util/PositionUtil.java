package com.goo.curiosities.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PositionUtil {
    public static Vec3 getRandomPos(Entity entity, float delta) {
        return new Vec3(
                entity.getRandomX(delta),
                entity.getRandomY(),
                entity.getRandomZ(delta)
        );
    }

    public static Vec3 getPositionInFront(Entity entity, float distance) {
        Vec3 lookVec = Vec3.directionFromRotation(entity.getViewXRot(0), entity.getViewYRot(0));
        return entity.position().add(lookVec.scale(distance));
    }

    public static Vec3 getHorizontalPositionInFront(Entity entity, float distance) {
        Vec3 lookVec = Vec3.directionFromRotation(0, entity.getViewYRot(0));
        return entity.position().add(lookVec.scale(distance));
    }

    
}

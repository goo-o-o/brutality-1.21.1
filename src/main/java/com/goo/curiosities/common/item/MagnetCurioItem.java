package com.goo.curiosities.common.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class MagnetCurioItem extends CuriositiesCurioItem {
    protected final float range, pullSpeed;

    public MagnetCurioItem(Properties properties, float range, float pullSpeed) {
        super(properties);
        this.range = range;
        this.pullSpeed = pullSpeed;
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        // sneaking disables magnet
        if (entity.level().isClientSide() || entity.isShiftKeyDown()) {
            return;
        }

        Vec3 playerPos = entity.getRopeHoldPosition(0);
        AABB area = entity.getBoundingBox().inflate(range);

        for (ItemEntity item : entity.level().getEntitiesOfClass(ItemEntity.class, area)) {
            // ignore items that cannot be picked up yet or are already picked up
            if (item.getItem().isEmpty() || item.hasPickUpDelay()) {
                continue;
            }

            pullEntity(item, playerPos);
        }

        for (ExperienceOrb orb : entity.level().getEntitiesOfClass(ExperienceOrb.class, area)) {
            pullEntity(orb, playerPos);
        }
    }

    private void pullEntity(net.minecraft.world.entity.Entity target, Vec3 targetPos) {
        Vec3 entityPos = target.position();
        Vec3 dir = targetPos.subtract(entityPos);
        double distance = dir.length();

        if (distance > 0.05D) {
            // scale velocity based on proximity (closer = smoother pull)
            Vec3 normalizedDir = dir.normalize();
            double speed = Math.min(pullSpeed, 1.0D / (distance * 0.5D));

            // smoothly ramp up current velocity towards target direction
            Vec3 currentMotion = target.getDeltaMovement();
            Vec3 targetMotion = normalizedDir.scale(speed);

            target.setDeltaMovement(currentMotion.lerp(targetMotion, 0.2D));
            target.hasImpulse = true;
        }
    }

}
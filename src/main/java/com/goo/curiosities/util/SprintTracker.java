package com.goo.curiosities.util;

import com.goo.curiosities.common.registry.CuriositiesAttachments;
import net.minecraft.world.entity.LivingEntity;

public class SprintTracker {

    public static void setSprintStartTime(LivingEntity livingEntity, long timestamp) {
        livingEntity.setData(CuriositiesAttachments.SPRINT_START_TIME.get(), timestamp);
    }

    public static long getSprintStartTime(LivingEntity livingEntity) {
        return livingEntity.getData(CuriositiesAttachments.SPRINT_START_TIME.get());
    }

    public static void tick(LivingEntity livingEntity) {
        // execute state changes on the server; NeoForge syncs the update to the client
        if (livingEntity.level().isClientSide()) {
            return;
        }

        long sprintStartTime = getSprintStartTime(livingEntity);
        boolean isSprinting = livingEntity.isSprinting();

        if (isSprinting && sprintStartTime == -1L) {
            setSprintStartTime(livingEntity, livingEntity.level().getGameTime());
        } else if (!isSprinting && sprintStartTime != -1L) {
            setSprintStartTime(livingEntity, -1L);
        }
    }
}

package com.goo.curiosities.util;

import com.goo.curiosities.common.registry.CuriositiesAttachments;
import net.minecraft.world.entity.LivingEntity;

public class CombatTracker {

    private static boolean isValidTimestamp(long currentTick, long timestamp, int timeoutTicks) {
        long diff = currentTick - timestamp;
        return diff >= 0 && diff <= timeoutTicks;
    }


    public static boolean isInCombat(LivingEntity entity, int timeoutTicks) {
        return isInCombat(entity, timeoutTicks, false);
    }

    /**
     * Returns if entity is in combat
     * @param timeoutTicks how long the last hurt timestamp must be in order to count as out of combat
     * @param allDamage whether it should just be mob damage or all damage
     * @return
     */
    public static boolean isInCombat(LivingEntity entity, int timeoutTicks, boolean allDamage) {
        long lastHurtTimestamp = allDamage ? entity.lastDamageStamp : entity.getLastHurtByMobTimestamp();

        // use world game time for lastDamageStamp, entity tickCount for mob timestamps
        long currentTick = allDamage ? entity.level().getGameTime() : entity.tickCount;

        boolean hurtValid = isValidTimestamp(currentTick, lastHurtTimestamp, timeoutTicks);
        boolean attackValid = isValidTimestamp(entity.tickCount, entity.getLastHurtMobTimestamp(), timeoutTicks);

        return hurtValid || attackValid;
    }

    /**
     * Gets or updates the combat start tick.
     * Handles state transitions (-1L reset on timeout) automatically.
     */
    public static long getCombatStartTick(LivingEntity entity, int timeoutTicks) {
        int currentTick = entity.tickCount;
        long lastHurt = entity.getLastHurtByMobTimestamp();
        int lastAttack = entity.getLastHurtMobTimestamp();

        boolean hurtValid = isValidTimestamp(currentTick, (int) lastHurt, timeoutTicks);
        boolean attackValid = isValidTimestamp(currentTick, lastAttack, timeoutTicks);

        long startTick = entity.getData(CuriositiesAttachments.COMBAT_START_TIME.get());

        // out of combat: reset
        if (!isInCombat(entity, timeoutTicks)) {
            if (startTick != -1L) {
                entity.setData(CuriositiesAttachments.COMBAT_START_TIME.get(), -1L);
            }
            return -1L;
        }

        // entered combat: consider valid timestamps
        if (startTick == -1L) {
            long initialStart;
            if (hurtValid && attackValid) {
                initialStart = Math.min(lastHurt, lastAttack);
            } else if (hurtValid) {
                initialStart = lastHurt;
            } else {
                initialStart = lastAttack;
            }

            entity.setData(CuriositiesAttachments.COMBAT_START_TIME.get(), initialStart);
            return initialStart;
        }

        return startTick;
    }

    /**
     * Returns total continuous ticks spent in combat. Returns 0 if out of combat.
     */
    public static long getTicksInCombat(LivingEntity entity, int timeoutTicks) {
        long startTick = getCombatStartTick(entity, timeoutTicks);
        if (startTick == -1L) return 0L;

        long elapsed = entity.tickCount - startTick;
        if (elapsed < 0) {
            entity.setData(CuriositiesAttachments.COMBAT_START_TIME.get(), (long) entity.tickCount);
            return 0L;
        }

        return elapsed;
    }
}
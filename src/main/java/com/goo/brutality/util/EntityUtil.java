package com.goo.brutality.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class EntityUtil {
    public static boolean isAlly(LivingEntity tester, LivingEntity toTest) {
        if (tester == null || toTest == null) return false;
        if (tester.equals(toTest)) return true;

        // scoreboard teams
        if (tester.getTeam() != null && tester.isAlliedTo(toTest)) {
            return true;
        }

        // tame-ables, if tester is owned by toTest
        if (tester instanceof OwnableEntity ownableTester) {
            UUID ownerUUID = ownableTester.getOwnerUUID();
            if (ownerUUID != null) {
                if (toTest.getUUID().equals(ownerUUID)) return true;
                if (toTest instanceof OwnableEntity ownableToTest && ownerUUID.equals(ownableToTest.getOwnerUUID())) {
                    return true;
                }
            }
        }

        // mirror check, if tester is the owner
        if (toTest instanceof OwnableEntity ownableToTest && tester.getUUID().equals(ownableToTest.getOwnerUUID())) {
            return true;
        }

        // iron golems (player-built ones are allies)
        if (tester instanceof IronGolem golem && toTest instanceof Player) {
            return golem.isPlayerCreated();
        }
        if (toTest instanceof IronGolem golem && tester instanceof Player) {
            return golem.isPlayerCreated();
        }

        // enemy of my enemy is my friend (revenge targets match)
        LivingEntity testerTarget = tester.getLastHurtByMob();
        LivingEntity toTestTarget = toTest.getLastHurtByMob();
        if (testerTarget != null && testerTarget.equals(toTestTarget)) {
            return true;
        }

        // active combat targeting using the Targeting interface
        if (tester instanceof Targeting targetingTester && toTest instanceof Targeting targetingToTest) {
            LivingEntity testerCombatTarget = targetingTester.getTarget();
            LivingEntity toTestCombatTarget = targetingToTest.getTarget();
            if (testerCombatTarget != null && testerCombatTarget.equals(toTestCombatTarget)) {
                return true;
            }
        }

        // pack/species alignment (same type are allies unless actively fighting each other)
        if (tester.getType() == toTest.getType()) {
            LivingEntity t1Target = tester instanceof Targeting t ? t.getTarget() : null;
            LivingEntity t2Target = toTest instanceof Targeting t ? t.getTarget() : null;
            return t1Target != toTest && t2Target != tester;
        }

        return false;
    }
}
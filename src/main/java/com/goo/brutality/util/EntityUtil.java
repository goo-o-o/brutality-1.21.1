package com.goo.brutality.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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

    @Nullable
    public static Double getClosestGroundY(Entity entity, int threshold) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(entity.getX(), entity.getY(), entity.getZ());
        Level level = entity.level();

        while (threshold > 0 && level.getBlockState(mutableBlockPos).isAir() && mutableBlockPos.getY() > level.getMinBuildHeight()) {
            BlockPos below = mutableBlockPos.below();
            if (!level.getBlockState(below).isAir()) {
                mutableBlockPos.move(Direction.DOWN);
                break;
            }

            mutableBlockPos.move(Direction.DOWN);
            threshold--;
        }

        // If we finished the loop and it's still air, we didn't find any ground
        if (level.getBlockState(mutableBlockPos).isAir()) {
            return null;
        }

        double maxHeight = level.getBlockState(mutableBlockPos).getShape(level, mutableBlockPos).max(Direction.Axis.Y, 0.0, 0.0);

        if (!Double.isFinite(maxHeight)) {
            return null;
        }

        // Combine the block's base Y coordinate with its precise collision surface offset
        return mutableBlockPos.getY() + maxHeight;
    }


    public static List<LivingEntity> getFoes(LivingEntity entity, float radius) {
        return entity.level().getNearbyEntities(
                LivingEntity.class,
                TargetingConditions.forCombat()
                        .range(radius)
                        .selector(e -> !EntityUtil.isAlly(entity, e)),
                entity,
                entity.getBoundingBox().inflate(radius)
        );
    }
}
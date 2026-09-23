package com.goo.curiosities.common.entity.goals;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import lombok.Getter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class RegalEmeraldFollowGoal extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final PathfinderMob mob;
    @Nullable
    protected Player player;
    private int calmDown;
    @Getter
    private boolean isRunning;
    private static final float RANGE_PER_EMERALD = 5F;

    public RegalEmeraldFollowGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
    }

    @Override
    public boolean canUse() {
        if (this.calmDown > 0) {
            this.calmDown--;
            return false;
        } else {
            this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
            return this.player != null;
        }
    }

    private boolean shouldFollow(LivingEntity entity) {
        return getRegalEmeraldCount(entity) > 0;
    }

    private int getRegalEmeraldCount(LivingEntity livingEntity) {
        return CuriosApi.getCuriosInventory(livingEntity).map(handler ->
                handler.findCurios(CuriositiesItems.REGAL_EMERALD.value()).size()).orElse(0);
    }

    @Override
    public void start() {
        assert this.player != null;
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.player = null;
        this.mob.getNavigation().stop();
        this.calmDown = reducedTickDelay(100);
        this.isRunning = false;
    }

    @Override
    public void tick() {
        assert this.player != null;
        this.mob.getLookControl().setLookAt(this.player, (float)(this.mob.getMaxHeadYRot() + 20), (float)this.mob.getMaxHeadXRot());

        int emeraldCount = getRegalEmeraldCount(player);

        if (this.mob.distanceToSqr(this.player) < RANGE_PER_EMERALD * emeraldCount) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.player, emeraldCount * 0.5);
        }
    }

}

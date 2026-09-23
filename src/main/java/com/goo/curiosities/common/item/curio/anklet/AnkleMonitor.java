package com.goo.curiosities.common.item.curio.anklet;

import com.goo.curiosities.common.item.AnkletCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class AnkleMonitor extends AnkletCurioItem {
    public AnkleMonitor(Properties properties) {
        super(properties);
    }

    // in blocks
    // TODO: add config
    private final static float RANGE = 300;

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.conditional(MobEffects.MOVEMENT_SLOWDOWN, 2, AnkleMonitor::isTooFarAway),
                PassiveMobEffect.conditional(MobEffects.DIG_SLOWDOWN, 1, AnkleMonitor::isTooFarAway)
        );
    }

    private static boolean isTooFarAway(LivingEntity livingEntity) {
        BlockPos pos = BlockPos.containing(livingEntity.position());

        if (livingEntity instanceof ServerPlayer player) {
            // too far from spawn
            if (!player.getRespawnDimension().equals(player.level().dimension())) {
                // different dimension = instant out
                return true;
            } else return player.getRespawnPosition() != null && !player.getRespawnPosition().closerThan(pos, RANGE);
        }
        // non-player entities
        return !livingEntity.level().getSharedSpawnPos().closerThan(pos, RANGE);
    }
}

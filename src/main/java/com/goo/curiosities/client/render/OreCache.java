package com.goo.curiosities.client.render;

import com.goo.curiosities.common.Curiosities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OreCache {

    private static final int RADIUS = 15;
    private static final int RADIUS_SQ = RADIUS * RADIUS;

    private static final Set<BlockPos> ACTIVE_ORES = ConcurrentHashMap.newKeySet();
    private static BlockPos lastPlayerPos = null;

    public static Set<BlockPos> getActiveOres() {
        return ACTIVE_ORES;
    }

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        BlockPos currentPos = mc.player.blockPosition();

        // first load or teleport: do a full initial scan around the player
        if (lastPlayerPos == null || currentPos.distSqr(lastPlayerPos) > 64) {
            lastPlayerPos = currentPos.immutable();
            fullInitialScan(mc.level, currentPos);
            return;
        }

        // if player hasn't moved block position, do nothing
        if (currentPos.equals(lastPlayerPos)) return;

        // incremental update on movement
        shiftSphere(mc.level, lastPlayerPos, currentPos);
        lastPlayerPos = currentPos.immutable();
    }

    // incremental slice update (handles 1-block steps in any direction)
    private static void shiftSphere(ClientLevel level, BlockPos oldCenter, BlockPos newCenter) {
        // remove blocks that left the sphere
        ACTIVE_ORES.removeIf(pos -> pos.distSqr(newCenter) > RADIUS_SQ);

        // scan the new bounding box on the edge moved
        int minX = newCenter.getX() - RADIUS;
        int maxX = newCenter.getX() + RADIUS;
        int minY = Math.max(level.getMinBuildHeight(), newCenter.getY() - RADIUS);
        int maxY = Math.min(level.getMaxBuildHeight(), newCenter.getY() + RADIUS);
        int minZ = newCenter.getZ() - RADIUS;
        int maxZ = newCenter.getZ() + RADIUS;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);

                    // only check blocks that are IN the new sphere, but were OUTSIDE the old sphere
                    if (pos.distSqr(newCenter) <= RADIUS_SQ && pos.distSqr(oldCenter) > RADIUS_SQ) {
                        if (level.hasChunkAt(pos)) {
                            BlockState state = level.getBlockState(pos);
                            if (state.is(Tags.Blocks.ORES)) {
                                ACTIVE_ORES.add(pos.immutable());
                            }
                        }
                    }
                }
            }
        }
    }

    // called only once when joining world or teleporting
    private static void fullInitialScan(ClientLevel level, BlockPos center) {
        ACTIVE_ORES.clear();

        BlockPos.betweenClosedStream(
                center.offset(-RADIUS, -RADIUS, -RADIUS),
                center.offset(RADIUS, RADIUS, RADIUS)
        ).forEach(pos -> {
            if (pos.distSqr(center) <= RADIUS_SQ && level.hasChunkAt(pos)) {
                BlockState state = level.getBlockState(pos);
                if (state.is(Tags.Blocks.ORES)) {
                    ACTIVE_ORES.add(pos.immutable());
                }
            }
        });
    }

    // handle real-time block updates (placed, mined, blown up)
    public static void handleBlockUpdate(BlockPos pos, BlockState newState) {
        if (lastPlayerPos == null) return;

        if (pos.distSqr(lastPlayerPos) <= RADIUS_SQ) {
            if (newState.is(Tags.Blocks.ORES)) {
                ACTIVE_ORES.add(pos.immutable());
            } else {
                ACTIVE_ORES.remove(pos);
            }
        }
    }
}
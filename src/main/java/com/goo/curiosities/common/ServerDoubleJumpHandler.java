package com.goo.curiosities.common;

import com.goo.curiosities.common.item.DoubleJumpCurioItem;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles the double jump state on players on the server
 */
@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class ServerDoubleJumpHandler {

    public static final Map<UUID, DoubleJumpState> ACTIVE_JUMPS = new ConcurrentHashMap<>();

    public static final class DoubleJumpState {
        public final DoubleJumpCurioItem curio;
        private int currentTick;

        public DoubleJumpState(DoubleJumpCurioItem curio, int currentTick) {
            this.curio = curio;
            this.currentTick = currentTick;
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (ACTIVE_JUMPS.isEmpty()) return;


        Iterator<Map.Entry<UUID, DoubleJumpState>> iterator = ACTIVE_JUMPS.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, DoubleJumpState> entry = iterator.next();
            UUID uuid = entry.getKey();
            DoubleJumpState state = entry.getValue();
            ServerPlayer player = event.getServer().getPlayerList().getPlayer(uuid);

            // stop jump if dead, disconnected, died or on ground or in water
            if (player == null || !player.isAlive() || player.onGround() || player.isInWater()) {
                if (player != null) {
                    // this is for automatic cleanup (some curios such as sandstorm in the bottle end their jumps based on the player's input, not the player's conditions)
                    state.curio.onWearerEndDoubleJump(player);
                }
                // remove
                iterator.remove();
                continue;
            }

            int maxTicks = state.curio.getTotalDoubleJumpTicks(player);

            if (state.currentTick >= maxTicks) {
                // remove if over
                state.curio.onWearerEndDoubleJump(player);
                iterator.remove();
                continue;
            }

            state.currentTick++;
            state.curio.onWearerDoubleJumpTick(player, state.currentTick);
        }

    }

    public static void onPlayerDisconnect(UUID playerId) {
        ACTIVE_JUMPS.remove(playerId);
    }
}

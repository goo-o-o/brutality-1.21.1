package com.goo.curiosities.client;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.DoubleJumpCurioItem;
import com.goo.curiosities.common.networking.serverbound.DoubleJumpPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.*;

/**
 * Tracks the double jump state of the local player
 */
@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class ClientDoubleJumpHandler {
    // track curios consumed during the current jump/airborne state
    private static final Set<Item> CONSUMED_ITEMS = new HashSet<>();

    private static boolean isHoldingJump = false;
    private static boolean isDoubleJumping = false;
    private static boolean wasReleasedInAir = false; // requires releasing jump mid-air
    private static int currentTick = 0;
    private static DoubleJumpCurioItem activeCurio = null;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.isPaused()) return;
        LocalPlayer player = mc.player;

        boolean isGrounded = player.onGround() || player.onClimbable() || player.isInWater() || player.isFallFlying();
        boolean jumpPressed = mc.options.keyJump.isDown();

        // reset on ground
        if (isGrounded) {
            CONSUMED_ITEMS.clear();
            wasReleasedInAir = false; // reset flag on ground
            if (isDoubleJumping) {
                stopJump(player);
            }
        } else {
            // if not on ground and not jump = releasedInAir = true
            if (!jumpPressed) {
                wasReleasedInAir = true;
            }
        }

        // if jump was pressed and is not on the ground and already released in air
        if (jumpPressed && !isHoldingJump && !isGrounded && wasReleasedInAir && !player.getAbilities().flying) {
            if (!isDoubleJumping) {
                CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                    List<SlotResult> available = new ArrayList<>();

                    handler.findCurios(s -> s.getItem() instanceof DoubleJumpCurioItem).forEach(result -> {
                        Item item = result.stack().getItem();
                        if (!CONSUMED_ITEMS.contains(item)) {
                            available.add(result);
                        }
                    });

                    if (!available.isEmpty()) {
                        ItemStack stack = available.getFirst().stack();
                        Item item = stack.getItem();

                        CONSUMED_ITEMS.add(item);
                        startJump(player, (DoubleJumpCurioItem) item, stack);

                        // set released to false so that this part only runs once
                        wasReleasedInAir = false;
                    }
                });
            }
        }

        // if space already held
        else if (isDoubleJumping && activeCurio != null) {
            int maxTicks = activeCurio.getTotalDoubleJumpTicks(player);

            if (!jumpPressed || currentTick >= maxTicks) {
                stopJump(player);
            } else {
                currentTick++;
                activeCurio.onWearerDoubleJumpTick(player, currentTick);
            }
        }

        isHoldingJump = jumpPressed;
    }

    private static void startJump(LocalPlayer player, DoubleJumpCurioItem curio, ItemStack stack) {
        isDoubleJumping = true;
        currentTick = 0;
        activeCurio = curio;

        curio.onWearerStartDoubleJump(player);

        // notify server once to start particle/sound broadcast loop
        PacketDistributor.sendToServer(new DoubleJumpPayload(DoubleJumpPayload.Action.START, stack.getItemHolder()));
    }

    private static void stopJump(LocalPlayer player) {
        if (activeCurio != null) {
            activeCurio.onWearerEndDoubleJump(player);
            // notify server once to stop particle/sound loop
            PacketDistributor.sendToServer(new DoubleJumpPayload(DoubleJumpPayload.Action.STOP, activeCurio.asItem().builtInRegistryHolder()));
        }

        isDoubleJumping = false;
        activeCurio = null;

    }
}

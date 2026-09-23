package com.goo.curiosities.client.event;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.entity.TimeStopField;
import com.goo.curiosities.common.networking.serverbound.JumpStatePayload;
import com.goo.curiosities.util.FootstepTracker;
import com.goo.goo_lib.util.color.EnvironmentColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class ClientPlayerEvents {

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseButton.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (TimeStopField.shouldBeAffected(player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (TimeStopField.shouldBeAffected(player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onScreenMouseScrolled(ScreenEvent.MouseScrolled.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (TimeStopField.shouldBeAffected(player)) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onScreenMouseClicked(ScreenEvent.MouseButtonPressed.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (TimeStopField.shouldBeAffected(player)) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onScreenMouseDragged(ScreenEvent.MouseDragged.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (TimeStopField.shouldBeAffected(player)) {
            event.setCanceled(true);
        }
    }



    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        EnvironmentColorUtil.resetAllColors();
        FootstepTracker.clear();
    }

    private static boolean wasJumping = false;

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean isJumping = event.getInput().jumping;

        // only send payload when state changes
        if (isJumping != wasJumping) {
            wasJumping = isJumping;
            PacketDistributor.sendToServer(new JumpStatePayload(isJumping));
        }
    }
}

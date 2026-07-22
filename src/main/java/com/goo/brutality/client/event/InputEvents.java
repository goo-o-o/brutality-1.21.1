package com.goo.brutality.client.event;

import com.goo.brutality.client.render.gui.DivineImmortalsRingOverlay;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.curio.hand.BrokenController;
import com.goo.brutality.common.networking.serverbound.divine_immortals_ring.ToggleDivineImmortalsRingOverlayPayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.goo.brutality.client.registry.BrutalityKeyNames.Mappings.OPEN_DIVINE_IMMORTALS_RING;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class InputEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        BrokenController.onKeyInput(player, event);

        boolean isKeyDown = OPEN_DIVINE_IMMORTALS_RING.get().isDown();

        if (DivineImmortalsRingOverlay.isOpen != isKeyDown) {
            DivineImmortalsRingOverlay.isOpen = isKeyDown;
            PacketDistributor.sendToServer(new ToggleDivineImmortalsRingOverlayPayload(isKeyDown));
        }
        if (DivineImmortalsRingOverlay.isOpen)
            if (mc.options.keyDrop.matches(event.getKey(), event.getScanCode()) && event.getAction() == InputConstants.PRESS) {
                DivineImmortalsRingOverlay.shouldExtract = false; // flag to prevent onClose from swapping items
            }
    }


    @SubscribeEvent
    public static void onMouseInGameScroll(InputEvent.MouseScrollingEvent event) {
        DivineImmortalsRingOverlay.onScroll(event);
    }


}

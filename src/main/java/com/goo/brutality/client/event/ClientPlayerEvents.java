package com.goo.brutality.client.event;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.curio.ring.DivineImmortalsRing;
import com.goo.brutality.common.networking.serverbound.divine_immortals_ring.OpenDivineImmortalsRingPayload;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class ClientPlayerEvents {
    @SubscribeEvent
    public static void onCreativeRightClick(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getButton() != GLFW.GLFW_MOUSE_BUTTON_RIGHT) return;

        // only intercept when inside CreativeModeInventoryScreen
        if (event.getScreen() instanceof CreativeModeInventoryScreen creativeScreen) {
            Slot hoveredSlot = creativeScreen.getSlotUnderMouse();

            if (hoveredSlot != null && hoveredSlot.getItem().getItem() instanceof DivineImmortalsRing) {
                event.setCanceled(true);

                // custom payload required here because creative mode sends no vanilla click packet
                PacketDistributor.sendToServer(new OpenDivineImmortalsRingPayload(hoveredSlot.getSlotIndex()));
            }
        }
    }
}

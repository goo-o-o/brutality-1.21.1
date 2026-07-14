package com.goo.brutality.client.event.render;

import com.goo.brutality.client.gui.RageMeterOverlay;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class OverlayRenderEvents {
    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            if (CurioUtil.isWearingCurio(event.getPlayer(), BrutalityItems.Curio.LAVA_LENSES.value(), BrutalityItems.Curio.SURTRS_HORN.value())) {
                event.getPoseStack().translate(0, -0.25, 0);
            }
        }
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(Brutality.loc("rage_meter"), new RageMeterOverlay());
    }
}

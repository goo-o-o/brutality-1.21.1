package com.goo.curiosities.client.event.render;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class OverlayRenderEvents {
    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            if (CurioUtil.isWearingCurio(event.getPlayer(), CuriositiesItems.LAVA_LENSES.value(), CuriositiesItems.SURTRS_HORN.value())) {
                event.getPoseStack().translate(0, -0.25, 0);
            }
        }
    }






}

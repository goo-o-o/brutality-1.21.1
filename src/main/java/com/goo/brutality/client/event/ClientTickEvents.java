package com.goo.brutality.client.event;

import com.goo.brutality.client.registry.BrutalityKeyMappings;
import com.goo.brutality.client.tooltip.TooltipRenderPipelineRegistry;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.rage.RageHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class ClientTickEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (BrutalityKeyMappings.ACTIVATE_RAGE.get().consumeClick()) {
            RageHandler.onPressRageKeymapping();
        }

        if (!TooltipRenderPipelineRegistry.isSlotHovered()) {
            TooltipRenderPipelineRegistry.resetPipeline();
        }
    }

}

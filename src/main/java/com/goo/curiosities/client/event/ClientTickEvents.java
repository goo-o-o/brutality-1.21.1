package com.goo.curiosities.client.event;

import com.goo.curiosities.client.registry.CuriositiesKeymappings;
import com.goo.curiosities.client.render.ClientGlitchState;
import com.goo.curiosities.client.render.OreCache;
import com.goo.curiosities.client.tooltip.TooltipRenderPipelineRegistry;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class ClientTickEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;


        while (CuriositiesKeymappings.Mappings.ACTIVE_ABILITY.get().consumeClick()) {
            CuriositiesCurioItem.Hooks.onPressActiveAbilityKeymapping(player);
        }


        if (!TooltipRenderPipelineRegistry.isSlotHovered()) {
            TooltipRenderPipelineRegistry.resetPipeline();
        }

        OreCache.tick();
        ClientGlitchState.tick();
    }

}

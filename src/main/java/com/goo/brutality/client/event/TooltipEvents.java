package com.goo.brutality.client.event;

import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.client.tooltip.TooltipRenderPipelineRegistry;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityDataComponents;
import com.goo.brutality.common.tooltip.ItemDescriptions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class TooltipEvents {
    @SubscribeEvent
    public static void onGatherTooltips(ItemTooltipEvent event) {
        ItemDescriptions customDescriptions = event.getItemStack().get(BrutalityDataComponents.ITEM_DESCRIPTIONS.get());

        if (customDescriptions != null && !customDescriptions.isEmpty()) {
            // an effective final counter to keep lines in the correct sequential order
            // 1 because name is first in tooltip
            var indexWrapper = new Object() { int value = 1; };

            // immediately adds each component to the main tooltip list at the correct index
            customDescriptions.addToTooltip(event.getContext(), comp -> event.getToolTip().add(indexWrapper.value++, comp), event.getFlags());
        }
    }

    @SubscribeEvent
    public static void onRenderTooltipPre(RenderTooltipEvent.Pre event) {
        TooltipRenderPipeline pipeline = TooltipRenderPipelineRegistry.getRenderPipeline(event.getItemStack());
        if (pipeline != null) {
            event.setCanceled(true);
            pipeline.run(event.getGraphics(), event.getItemStack(), event.getFont(), event.getComponents(),
                    event.getX(), event.getY(), event.getScreenWidth(), event.getScreenHeight(), event.getTooltipPositioner());
        }
    }


}

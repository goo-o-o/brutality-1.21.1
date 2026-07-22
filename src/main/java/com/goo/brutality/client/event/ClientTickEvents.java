package com.goo.brutality.client.event;

import com.goo.brutality.client.registry.BrutalityKeyNames;
import com.goo.brutality.client.render.gui.DivineImmortalsRingOverlay;
import com.goo.brutality.client.tooltip.TooltipRenderPipelineRegistry;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.BrutalityCurioItem;
import com.goo.brutality.common.rage.RageHandler;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class ClientTickEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (BrutalityKeyNames.Mappings.TRIGGER_RAGE.get().consumeClick()) {
            RageHandler.onPressRageKeymapping();
        }

        while (BrutalityKeyNames.Mappings.ACTIVE_ABILITY.get().consumeClick()) {
            BrutalityCurioItem.Hooks.onPressActiveAbilityKeymapping(Minecraft.getInstance().player);
        }

        if (CurioUtil.isWearingCurio(Minecraft.getInstance().player, BrutalityItems.Curio.DIVINE_IMMORTALS_RING.value())) {
            DivineImmortalsRingOverlay.isOpen = BrutalityKeyNames.Mappings.OPEN_DIVINE_IMMORTALS_RING.get().isDown();
            DivineImmortalsRingOverlay.tick();
        }
//        while (BrutalityKeyMappings.ARMOR_SET_BONUS.get().consumeClick()) {
//        }

        if (!TooltipRenderPipelineRegistry.isSlotHovered()) {
            TooltipRenderPipelineRegistry.resetPipeline();
        }

    }

}

package com.goo.brutality.client.event;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

import java.util.List;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class InputEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;

        if (event.getAction() == InputConstants.PRESS) {
            InputConstants.Key pressedKey = InputConstants.getKey(event.getKey(), event.getScanCode());
            List<KeyMapping> mappings = KeyMapping.MAP.getAll(pressedKey, false);
            if (CurioUtil.isWearingCurio(player, BrutalityItems.Curio.Rage.BROKEN_CONTROLLER.value())) {

                if (player != null && player.getRandom().nextFloat() <= 0.1) {
                    for (KeyMapping mapping : mappings) {
                        while (mapping.consumeClick()) {
                            // clear the click buffer
                        }
                        mapping.setDown(false);
                    }
                }
            }
        }
    }
}

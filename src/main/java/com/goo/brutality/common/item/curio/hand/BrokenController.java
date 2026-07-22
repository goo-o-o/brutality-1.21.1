package com.goo.brutality.common.item.curio.hand;

import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;

import java.util.List;

public class BrokenController extends BrutalityRageCurioItem {
    public BrokenController(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public static void onKeyInput(Player player, InputEvent.Key event) {
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

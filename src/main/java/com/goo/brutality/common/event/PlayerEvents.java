package com.goo.brutality.common.event;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.goo.goo_lib.common.event.custom.EventResult;
import com.goo.goo_lib.common.event.custom.PlayerSwimEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Player-specific events, {@link net.neoforged.neoforge.event.tick.PlayerTickEvent}should be handled in {@link  CommonTickEvents}
 */
@EventBusSubscriber(modid = Brutality.MOD_ID)
public class PlayerEvents {
    @SubscribeEvent
    public static void onPlayerSwim(PlayerSwimEvent event) {
        Player player = event.getEntity();

        if (player.isInLava()) {
            if (CurioUtil.isWearingCurio(player, BrutalityItems.Curio.SURTRS_HORN.value())) {
                event.setResult(EventResult.FAIL);
            }
        } else if (player.isInWater()) {
            if (CurioUtil.isWearingCurio(player, BrutalityItems.Curio.POSEIDONS_BLESSING.value(), BrutalityItems.Curio.HYDROPHOBIC_NANOCOATING.value())) {
                event.setResult(EventResult.FAIL);
            }
        } else if (CurioUtil.isWearingCurio(player, BrutalityItems.Curio.FLIPPERS_OF_ICARUS.value())) {
            event.setResult(EventResult.SUCCESS);
        }
    }
}

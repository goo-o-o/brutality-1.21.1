package com.goo.curiosities.common.event;


import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesItems;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class VillagerEvents {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.ARMORER) {
            // add to journeyman
            event.getTrades().get(3).add((entity, random) -> {
                // check if swamp villager
                if (entity instanceof VillagerDataHolder villagerHolder) {
                    if (villagerHolder.getVillagerData().getType() == VillagerType.SWAMP) {
                        return new MerchantOffer(
                                new ItemCost(Items.EMERALD, 32),
                                new ItemStack(CuriositiesItems.AMPHIBIAN_BOOTS.value()),
                                12,
                                10,
                                0.05F
                        );
                    }
                }
                // return null to skip
                return null;
            });

            event.getTrades().get(4).add((trader, random) -> {
                if (trader instanceof VillagerDataHolder villagerDataHolder) {
                    if (villagerDataHolder.getVariant() == VillagerType.SNOW) {
                        return new MerchantOffer(
                                new ItemCost(Items.EMERALD, 32),
                                new ItemStack(CuriositiesItems.BLIZZARD_IN_A_BOTTLE.value()),
                                12,
                                10,
                                0.05F
                        );
                    }
                }
                return null;
            });
        }
    }


}
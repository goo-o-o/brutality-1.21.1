package com.goo.curiosities.common;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.*;
import com.goo.goo_lib.util.DynamicAttributeUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Curiosities.MOD_ID)
public class Curiosities {
    public static final String MOD_ID = "curiosities";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Curiosities(IEventBus modEventBus, ModContainer modContainer) {
        CuriositiesParticles.PARTICLE_TYPES.register(modEventBus);
        CuriositiesSounds.SOUND_EVENTS.register(modEventBus);
        CuriositiesEntities.ENTITY_TYPES.register(modEventBus);
        CuriositiesAttachments.ATTACHMENT_TYPES.register(modEventBus);
        CuriositiesDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        CuriositiesEffects.MOB_EFFECTS.register(modEventBus);
        CuriositiesLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        CuriositiesItems.register(modEventBus);
        CuriositiesCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::onCommonSetup);
        modContainer.registerConfig(ModConfig.Type.SERVER, CuriositiesServerConfig.SPEC);
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        DynamicAttributeUtil.registerModifier(CuriositiesCurioItem.Hooks::modifyDynamicAttributeValues);
    }

    public static ResourceLocation loc(String s) {
        return ResourceLocation.fromNamespaceAndPath(Curiosities.MOD_ID, s);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}

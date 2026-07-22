package com.goo.brutality.common;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.item.BrutalityCurioItem;
import com.goo.brutality.common.registry.*;
import com.goo.goo_lib.common.DynamicAttributeAPI;
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

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Brutality.MOD_ID)
public class Brutality {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "brutality";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Brutality(IEventBus modEventBus, ModContainer modContainer) {
        BrutalityMenus.MENUS.register(modEventBus);
        BrutalityAttributes.ATTRIBUTES.register(modEventBus);
        BrutalityParticles.PARTICLE_TYPES.register(modEventBus);
        BrutalitySounds.SOUND_EVENTS.register(modEventBus);
        BrutalityEntities.ENTITY_TYPES.register(modEventBus);
        BrutalityAttachments.ATTACHMENT_TYPES.register(modEventBus);
        BrutalityDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        BrutalityEffects.MOB_EFFECTS.register(modEventBus);

        BrutalityItems.register(modEventBus);
        BrutalityCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        // for force loading

        BrutalityTextEffects.REGISTRY.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::onCommonSetup);
        modContainer.registerConfig(ModConfig.Type.SERVER, BrutalityServerConfig.SPEC);
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        DynamicAttributeAPI.registerModifier(BrutalityCurioItem.Hooks::modifyDynamicAttributeValues);
    }

    public static ResourceLocation loc(String s) {
        return ResourceLocation.fromNamespaceAndPath(Brutality.MOD_ID, s);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}

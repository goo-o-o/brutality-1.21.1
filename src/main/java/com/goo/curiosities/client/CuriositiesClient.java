package com.goo.curiosities.client;

import com.goo.curiosities.common.Curiosities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Curiosities.MOD_ID, dist = Dist.CLIENT)
public class CuriositiesClient {
    public CuriositiesClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        container.registerConfig(ModConfig.Type.CLIENT, CuriositiesClientConfig.SPEC);

    }

}

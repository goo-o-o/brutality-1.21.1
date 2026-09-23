package com.goo.curiosities.client.registry;

import com.goo.curiosities.common.Curiosities;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

/**
 * Wrapper class so we can get the constants on both sides
 */
public class CuriositiesKeymappings {
    private static final String CATEGORY = "key.categories." + Curiosities.MOD_ID;
    public static final String ACTIVE_ABILITY = "key." + Curiosities.MOD_ID + ".active_ability";

    @OnlyIn(Dist.CLIENT)
    public static class Mappings {


        public static final Lazy<KeyMapping> ACTIVE_ABILITY = Lazy.of(() ->
                new KeyMapping(
                        CuriositiesKeymappings.ACTIVE_ABILITY,
                        KeyConflictContext.IN_GAME,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        CATEGORY));


    }
}

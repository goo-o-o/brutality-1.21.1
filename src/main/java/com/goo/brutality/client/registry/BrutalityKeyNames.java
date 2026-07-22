package com.goo.brutality.client.registry;

import com.goo.brutality.common.Brutality;
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
public class BrutalityKeyNames {
    private static final String CATEGORY = "key.categories." + Brutality.MOD_ID;
    public static final String TRIGGER_RAGE = "key." + Brutality.MOD_ID + ".trigger_rage";
    public static final String ACTIVE_ABILITY = "key." + Brutality.MOD_ID + ".active_ability";
    public static final String ARMOR_SET_BONUS = "key." + Brutality.MOD_ID + ".armor_set_bonus";
    public static final String DIVINE_IMMORTALS_RING = "key." + Brutality.MOD_ID + ".divine_immortals_ring";

    @OnlyIn(Dist.CLIENT)
    public static class Mappings {


        public static final Lazy<KeyMapping> TRIGGER_RAGE = Lazy.of(() ->
                new KeyMapping(
                        BrutalityKeyNames.TRIGGER_RAGE,
                        KeyConflictContext.IN_GAME,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_N,
                        CATEGORY));

        public static final Lazy<KeyMapping> ACTIVE_ABILITY = Lazy.of(() ->
                new KeyMapping(
                        BrutalityKeyNames.ACTIVE_ABILITY,
                        KeyConflictContext.IN_GAME,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        CATEGORY));

        public static final Lazy<KeyMapping> ARMOR_SET_BONUS = Lazy.of(() ->
                new KeyMapping(
                        BrutalityKeyNames.ARMOR_SET_BONUS,
                        KeyConflictContext.IN_GAME,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_X,
                        CATEGORY));
        public static final Lazy<KeyMapping> OPEN_DIVINE_IMMORTALS_RING = Lazy.of(() ->
                new KeyMapping(
                        DIVINE_IMMORTALS_RING,
                        KeyConflictContext.IN_GAME,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_V,
                        CATEGORY));

    }
}

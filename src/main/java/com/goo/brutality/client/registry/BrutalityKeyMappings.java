package com.goo.brutality.client.registry;

import com.goo.brutality.common.Brutality;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class BrutalityKeyMappings {
    public static final Lazy<KeyMapping> ACTIVATE_RAGE = Lazy.of(() ->
            new KeyMapping(
                    "key." + Brutality.MOD_ID + ".activate_rage",
                    KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_N,
                    "key.categories." + Brutality.MOD_ID + ".combat"
            ));

    public static final Lazy<KeyMapping> ACTIVE_ABILITY = Lazy.of(() ->
            new KeyMapping(
                    "key." + Brutality.MOD_ID + ".active_ability",
                    KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_H,
                    "key.categories." + Brutality.MOD_ID + ".combat"
            ));

    public static final Lazy<KeyMapping> ARMOR_SET_BONUS = Lazy.of(() ->
            new KeyMapping(
                    "key." + Brutality.MOD_ID + ".armor_set_bonus",
                    KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_X,
                    "key.categories." + Brutality.MOD_ID + ".combat"
            ));

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(ACTIVATE_RAGE.get());
    }
}

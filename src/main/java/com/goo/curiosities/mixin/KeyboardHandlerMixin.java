package com.goo.curiosities.mixin;

import com.goo.curiosities.common.entity.TimeStopField;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void blockKeyWhenFrozen(
            long windowPointer, int key, int scanCode, int action, int modifiers,
            CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        if (!TimeStopField.shouldBeAffected(player)) return;
        if (key == GLFW.GLFW_KEY_ESCAPE) return;
        if (key == GLFW.GLFW_KEY_F2) return;    // screenshot
        if (key == GLFW.GLFW_KEY_F3) return;    // debug
        if (key == GLFW.GLFW_KEY_F11) return;   // fullscreen
        // let key release through
        if (action == InputConstants.RELEASE) return;

        ci.cancel();
    }
}
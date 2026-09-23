package com.goo.curiosities.mixin;

import com.goo.curiosities.common.entity.TimeStopField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
    private void blockMouseMoveWhenFrozen(long windowPointer, double xpos, double ypos, CallbackInfo ci) {

        Player player = Minecraft.getInstance().player;
        // in case in menu and stuff
        if (player != null) {
            if (!TimeStopField.shouldBeAffected(player)) return;

        MouseHandler self = (MouseHandler) (Object) this;
        // still update the positions so that when unfrozen it doesn't snap to the new position using its accumulated delta
        self.xpos = xpos;
        self.ypos = ypos;

        ci.cancel();
        }
    }
}
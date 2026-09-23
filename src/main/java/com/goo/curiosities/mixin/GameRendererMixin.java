package com.goo.curiosities.mixin;

import com.goo.curiosities.common.entity.TimeStopField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @ModifyArg(
        method = "renderItemInHand",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderHandsWithItems(FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/player/LocalPlayer;I)V"
        ),
        index = 0
    )
    private float freezeItemBob(float partialTick) {
        Player player = Minecraft.getInstance().player;
        if (player != null && TimeStopField.shouldBeAffected(player)) {
            return 1.0F;
        }
        return partialTick;
    }
}
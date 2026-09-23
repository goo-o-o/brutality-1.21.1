package com.goo.curiosities.mixin;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Unique
    private static final ResourceLocation BLACK_TEXTURE = Curiosities.loc("textures/black.png");


    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
    private void modifySkinTexture(CallbackInfoReturnable<PlayerSkin> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

        // active potion effects automatically sync to tracking clients on dedicated servers
        if (player.hasEffect(CuriositiesEffects.INCOGNITO)) {
            PlayerSkin original = cir.getReturnValue();
            if (original != null) {
                cir.setReturnValue(new PlayerSkin(
                        BLACK_TEXTURE,
                        null,
                        original.capeTexture() != null ? BLACK_TEXTURE : null,
                        BLACK_TEXTURE,
                        original.model(),
                        original.secure()
                ));
            }
        }
    }
}
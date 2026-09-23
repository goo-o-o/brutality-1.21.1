package com.goo.curiosities.client.render.render_pipelines;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.goo_lib.client.render.pipeline.ScreenPostEffectPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlitchPipeline extends ScreenPostEffectPipeline {

    @Override
    public ResourceLocation getLocation() {
        return CuriositiesRenderTypes.GLITCH_SHADER_LOCATION;
    }

    @Override
    public boolean isDisabled() {
        if (Minecraft.getInstance().player != null) {
            return !Minecraft.getInstance().player.hasEffect(CuriositiesEffects.GLITCHED);
        }
        return false;
//        return true;
    }

    @Override
    public BlitMode getBlitMode() {
        return BlitMode.OPAQUE;
    }

}

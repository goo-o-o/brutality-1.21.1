package com.goo.curiosities.client.render.render_pipelines;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import net.minecraft.resources.ResourceLocation;

public class GlitchSpherePipeline extends SphereEffectPipeline{
    @Override
    public ResourceLocation getLocation() {
        return CuriositiesRenderTypes.GLITCH_SPHERE_SHADER_LOCATION;
    }
}

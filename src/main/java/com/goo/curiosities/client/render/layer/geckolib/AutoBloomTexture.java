package com.goo.curiosities.client.render.layer.geckolib;

import com.goo.goo_lib.client.registry.GLRenderTypes;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;

import java.util.function.Function;

public class AutoBloomTexture extends AutoGlowingTexture {

    private static final String APPENDIX = "_bloom";

    public AutoBloomTexture(ResourceLocation originalLocation, ResourceLocation location) {
        super(originalLocation, location);
    }

    public static ResourceLocation getEmissiveResource(ResourceLocation baseResource) {
        ResourceLocation path = appendToPath(baseResource, APPENDIX);

        generateTexture(path, textureManager -> textureManager.register(path, new AutoBloomTexture(baseResource, path)));

        return path;
    }

    /**
     * Return a cached instance of the RenderType for the given texture for AutoGlowingGeoLayer rendering
     *
     * @param texture The texture of the resource to apply a glow layer to
     */
    public static RenderType getRenderType(ResourceLocation texture) {
        return BLOOM_RENDER_TYPE.apply(getEmissiveResource(texture));
    }


    private static final Function<ResourceLocation, RenderType> BLOOM_RENDER_TYPE =
            Util.memoize(resourceLocation -> GLRenderTypes.getBloomRenderType(resourceLocation, RenderStateShard.LEQUAL_DEPTH_TEST, RenderStateShard.CULL));

}

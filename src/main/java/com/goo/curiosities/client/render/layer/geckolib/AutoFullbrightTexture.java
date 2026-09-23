package com.goo.curiosities.client.render.layer.geckolib;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;

import java.util.function.BiFunction;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class AutoFullbrightTexture extends AutoGlowingTexture {

    private static final String APPENDIX = "_fullbright";

    public AutoFullbrightTexture(ResourceLocation originalLocation, ResourceLocation location) {
        super(originalLocation, location);
    }

    public static ResourceLocation getEmissiveResource(ResourceLocation baseResource) {
        ResourceLocation path = appendToPath(baseResource, APPENDIX);

        generateTexture(path, textureManager -> textureManager.register(path, new AutoFullbrightTexture(baseResource, path)));

        return path;
    }

    /**
     * Return a cached instance of the RenderType for the given texture for AutoGlowingGeoLayer rendering
     *
     * @param texture The texture of the resource to apply a glow layer to
     */
    public static RenderType getRenderType(ResourceLocation texture) {
        return FULLBRIGHT_RENDER_TYPE.apply(getEmissiveResource(texture), false);
    }

    /**
     * Return a cached instance of the RenderType for the given texture for AutoGlowingGeoLayer rendering, while the entity has an outline
     *
     * @param texture The texture of the resource to apply a glow layer to
     */
    public static RenderType getOutlineRenderType(ResourceLocation texture) {
        return FULLBRIGHT_RENDER_TYPE.apply(getEmissiveResource(texture), true);
    }

    private static final BiFunction<ResourceLocation, Boolean, RenderType> FULLBRIGHT_RENDER_TYPE =
            Util.memoize((texture, isGlowing) ->
            RenderType.create("geo_fullbright_layer", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder()
                    .setShaderState(new ShaderStateShard(CuriositiesRenderTypes.InternalShaders.FULLBRIGHT_CUTOUT::getInstance))
                    .setTextureState(new TextureStateShard(texture, false, false))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setCullState(CULL)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .createCompositeState(isGlowing)));

}

package com.goo.curiosities.client.registry;

import com.goo.curiosities.common.Curiosities;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.goo.goo_lib.common.GooLib;
import com.goo.goo_lib.mixin.CompositeRenderTypeAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import static net.minecraft.client.renderer.RenderStateShard.LIGHTNING_TRANSPARENCY;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class CuriositiesRenderTypes {

    public static final ParticleRenderType PARTICLE_SHEET_BLOOM = new ParticleRenderType() {
        @Override
        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
            // queue shader pipeline before buffer recording
            PostEffectRegistry.renderEffectForNextTick(
                    GLRenderTypes.BLOOM_SHADER_LOCATION,
                    ShaderPipeline.PipelineStage.WORLD
            );

            RenderSystem.depthMask(true);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.defaultBlendFunc();
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public String toString() {
            return "PARTICLE_SHEET_BLOOM";
        }
    };

    public static final Map<RenderType, RenderType> PIXELATE_CACHE = new HashMap<>();

    public static RenderType redirectPixelate(RenderType original) {
        return PIXELATE_CACHE.computeIfAbsent(original, type -> {
            if (type instanceof CompositeRenderTypeAccessor composite) {
                RenderType.CompositeState state = composite.getState();

                // preserve original shaders, textures, formats, and depth states; swap only output target
                RenderType.CompositeState newState = RenderType.CompositeState.builder()
                        .setShaderState(state.shaderState)
                        .setTextureState(state.textureState)
                        .setTransparencyState(state.transparencyState)
                        .setDepthTestState(state.depthTestState)
                        .setCullState(state.cullState)
                        .setLightmapState(state.lightmapState)
                        .setOverlayState(state.overlayState)
                        .setWriteMaskState(state.writeMaskState)
                        .setOutputState(GLRenderTypes.PIXELATE_OUTPUT)
                        .createCompositeState(state.outlineProperty);

                return RenderType.create(
                        type + "_pixelate_redirect",
                        type.format(),
                        type.mode(),
                        type.bufferSize(),
                        type.affectsCrumbling(),
                        type.sortOnUpload(),
                        newState
                );
            }
            return type;
        });
    }

    public static RenderType getEncryptedRenderType(ResourceLocation location, RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Curiosities.MOD_ID + ":encrypted_texture_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.ENCRYPTED_TEXTURE::getInstance))
                        .setTextureState(new RenderStateShard.TextureStateShard(location, false, true))
                        .setDepthTestState(depthTestStateShard)
                        .setTransparencyState(LIGHTNING_TRANSPARENCY)
                        .createCompositeState(true)
        );
    }

    public static RenderType getEncryptedRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Curiosities.MOD_ID + ":encrypted_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.ENCRYPTED::getInstance))
                        .setDepthTestState(depthTestStateShard)
                        .setTransparencyState(LIGHTNING_TRANSPARENCY)
                        .createCompositeState(true)
        );
    }

    public static RenderType getWaterRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Curiosities.MOD_ID + ":water_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.WATER::getInstance))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getSmokeRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Curiosities.MOD_ID + ":smoke_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.SMOKE::getInstance))
                        .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getEmbersRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Curiosities.MOD_ID + ":embers_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.EMBERS::getInstance))
                        .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }
    public static RenderType getSnowRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Curiosities.MOD_ID + ":snow_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.SNOW::getInstance))
                        .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }



    public static RenderType getNanoMachinesRenderType(ResourceLocation resourceLocation, RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(
                GooLib.MOD_ID + ":nano_machines" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                1536, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.NANO_MACHINES::getInstance))
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, true))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }
    public static final MultiBufferSource.BufferSource SPHERE_MASK_BUFFER =
            MultiBufferSource.immediate(new ByteBufferBuilder(65536));
    public static RenderType getTimeStopRenderType() {
        return RenderType.create(
                GooLib.MOD_ID + ":time_stop",
                DefaultVertexFormat.POSITION_COLOR_NORMAL,
                VertexFormat.Mode.TRIANGLES,
                65536, false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.SPHERE_MASK::getInstance))
                        .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setOutputState(TIME_STOP_SPHERE_OUTPUT)
                        .createCompositeState(false)
        );
    }
    public static RenderType getGlitchRenderType() {
        return RenderType.create(
                GooLib.MOD_ID + ":glitch",
                DefaultVertexFormat.POSITION_COLOR_NORMAL,
                VertexFormat.Mode.TRIANGLES,
                65536, false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.SPHERE_MASK::getInstance))
                        .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setOutputState(GLITCH_SPHERE_OUTPUT)
                        .createCompositeState(false)
        );
    }


    public static final ResourceLocation GLITCH_SHADER_LOCATION = Curiosities.loc("shaders/post/glitch.json");
    public static final ResourceLocation GLITCH_SPHERE_SHADER_LOCATION = Curiosities.loc("shaders/post/glitch_sphere.json");
    public static final RenderStateShard.OutputStateShard GLITCH_SPHERE_OUTPUT =
            new RenderStateShard.OutputStateShard("glitch_sphere_target", () -> {
                RenderTarget mask = PostEffectRegistry.getTempTarget(
                        CuriositiesRenderTypes.GLITCH_SPHERE_SHADER_LOCATION,
                        ShaderPipeline.PipelineStage.SCREEN,
                        "sphere_mask"
                );
                if (mask != null) {
                    mask.clear(Minecraft.ON_OSX);
                    mask.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                    mask.bindWrite(true);
                }
            }, () -> Minecraft.getInstance().getMainRenderTarget().bindWrite(true));

    public static final ResourceLocation TIME_STOP_SPHERE_SHADER_LOCATION = Curiosities.loc("shaders/post/time_stop_sphere.json");
    public static final RenderStateShard.OutputStateShard TIME_STOP_SPHERE_OUTPUT =
            new RenderStateShard.OutputStateShard("time_stop_sphere_target", () -> {
                RenderTarget mask = PostEffectRegistry.getTempTarget(
                        CuriositiesRenderTypes.TIME_STOP_SPHERE_SHADER_LOCATION,
                        ShaderPipeline.PipelineStage.SCREEN,
                        "sphere_mask"
                );
                if (mask != null) {
                    mask.clear(Minecraft.ON_OSX);
                    mask.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                    mask.bindWrite(true);
                }
            }, () -> Minecraft.getInstance().getMainRenderTarget().bindWrite(true));

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            for (InternalShaders shader : InternalShaders.values()) {
                String shaderPath = "rendertype_" + shader.name().toLowerCase(Locale.ROOT);

                event.registerShader(
                        new ShaderInstance(event.getResourceProvider(), Curiosities.loc(shaderPath), shader.getFormat()),
                        shader::setInstance
                );
            }
            Curiosities.LOGGER.info("Successfully consolidated and loaded internal shaders.");
        } catch (IOException exception) {
            Curiosities.LOGGER.error("Failed to register unified pipeline shaders", exception);
        }
    }


    public enum InternalShaders {
        WATER(DefaultVertexFormat.POSITION_COLOR),
        FULLBRIGHT_CUTOUT(DefaultVertexFormat.NEW_ENTITY),

        NANO_MACHINES(DefaultVertexFormat.POSITION_TEX_COLOR),
        SPHERE_MASK(DefaultVertexFormat.POSITION_COLOR),

        ENCRYPTED(DefaultVertexFormat.POSITION_TEX_COLOR),
        ENCRYPTED_TEXTURE(DefaultVertexFormat.POSITION_TEX_COLOR),
        SNOW(DefaultVertexFormat.POSITION_TEX_COLOR),
        SMOKE(DefaultVertexFormat.POSITION_TEX_COLOR),
        EMBERS(DefaultVertexFormat.POSITION_TEX_COLOR);

        @Getter
        private final VertexFormat format;
        private final Consumer<ShaderInstance> onGetCallback;
        @Setter
        private ShaderInstance instance;

        InternalShaders(VertexFormat format) {
            this(format, instance -> {
            });
        }

        InternalShaders(VertexFormat format, Consumer<ShaderInstance> onGetCallback) {
            this.format = format;
            this.onGetCallback = onGetCallback;
        }

        @Nullable
        public ShaderInstance getInstance() {
            this.onGetCallback.accept(this.instance);
            return this.instance;
        }

    }
}

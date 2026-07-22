package com.goo.brutality.client.registry;

import com.goo.brutality.common.Brutality;
import com.goo.goo_lib.mixin.CompositeRenderTypeAccessor;
import com.goo.goo_lib.mixin.CompositeStateAccessor;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Locale;
import java.util.function.Consumer;

import static net.minecraft.client.renderer.RenderStateShard.LIGHTNING_TRANSPARENCY;
import static net.minecraft.client.renderer.RenderStateShard.TRANSLUCENT_TRANSPARENCY;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class BrutalityRenderTypes {

    public static RenderType getEncryptedRenderType(ResourceLocation location, RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":encrypted_texture_" + depthTestStateShard,
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
        return RenderType.create(Brutality.MOD_ID + ":encrypted_" + depthTestStateShard,
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
        return RenderType.create(Brutality.MOD_ID + ":water_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.WATER::getInstance))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getStygianRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":stygian_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.STYGIAN::getInstance))
                        .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getSmokeRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":smoke_" + depthTestStateShard,
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
        return RenderType.create(Brutality.MOD_ID + ":embers_" + depthTestStateShard,
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

    public static RenderType getElectricityRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":electricity_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.ELECTRICITY::getInstance))
                        .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getVoidRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":void_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.VOID::getInstance))
                        .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getBoxShadowRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":box_shadow_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.BOX_SHADOW::getInstance))
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getStarsRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":stars_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.STARS::getInstance))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getNebulaRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":nebula_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.NEBULA::getInstance))
                        .setDepthTestState(depthTestStateShard)
                        .setTextureState(
                                RenderStateShard.MultiTextureStateShard.builder()
                                        .add(Brutality.loc("textures/entity/cthonian_void.png"), false, false)
                                        .add(Brutality.loc("textures/entity/cthonian_void.png"), false, false)
                                        .build()
                        )
                        .createCompositeState(true)
        );
    }

    public static RenderType getFireRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":fire_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.FIRE::getInstance))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }

    public static RenderType getTextReefRenderType(RenderType sourceType) {
        return RenderType.create(
                Brutality.MOD_ID + ":text_reef",
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                256, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders.TEXT_REEF::getInstance))
                        .setTextureState(((CompositeStateAccessor) (Object) ((CompositeRenderTypeAccessor) sourceType).getState()).getTextureState())
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .createCompositeState(false)
        );
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            for (InternalShaders shader : InternalShaders.values()) {
                String shaderPath = "rendertype_" + shader.name().toLowerCase(Locale.ROOT);

                event.registerShader(
                        new ShaderInstance(event.getResourceProvider(), Brutality.loc(shaderPath), shader.getFormat()),
                        shader::setInstance
                );
            }
            Brutality.LOGGER.info("Successfully consolidated and loaded internal shaders.");
        } catch (IOException exception) {
            Brutality.LOGGER.error("Failed to register unified pipeline shaders", exception);
        }
    }

    public enum InternalShaders {
        TEXT_REEF(DefaultVertexFormat.POSITION_TEX_COLOR),
        WATER(DefaultVertexFormat.POSITION_COLOR),
        FIRE(DefaultVertexFormat.POSITION_TEX_COLOR, instance -> {
            // updates gui scale dynamically on access
            if (instance != null) {
                instance.safeGetUniform("GuiScale").set((float) Minecraft.getInstance().getWindow().getGuiScale());
            }
        }),
        NEBULA(DefaultVertexFormat.POSITION_TEX_COLOR, instance -> {
            // updates gui scale dynamically on access
            if (instance != null) {
                instance.safeGetUniform("GuiScale").set((float) Minecraft.getInstance().getWindow().getGuiScale());
            }
        }),

        ENCRYPTED(DefaultVertexFormat.POSITION_TEX_COLOR),
        ENCRYPTED_TEXTURE(DefaultVertexFormat.POSITION_TEX_COLOR),
        SMOKE(DefaultVertexFormat.POSITION_TEX_COLOR),
        EMBERS(DefaultVertexFormat.POSITION_TEX_COLOR),
        ELECTRICITY(DefaultVertexFormat.POSITION_TEX_COLOR),
        STYGIAN(DefaultVertexFormat.POSITION_TEX_COLOR),
        VOID(DefaultVertexFormat.POSITION_TEX_COLOR),
        BOX_SHADOW(DefaultVertexFormat.POSITION_TEX_COLOR),
        STARS(DefaultVertexFormat.POSITION_TEX_COLOR);


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

package com.goo.brutality.client.registry;

import com.goo.brutality.common.Brutality;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class BrutalityRenderTypes {

    public static RenderType getEncryptedRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":encrypted_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeEncryptedShader))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
                );
    }
    public static RenderType getWaterRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":water_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeWaterShader))
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
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeStygianShader))
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
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeSmokeShader))
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
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeEmbersShader))
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
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeElectricityShader))
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
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeVoidShader))
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
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeBoxShadowShader))
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
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeStarsShader))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }
    public static RenderType getFireRenderType(RenderStateShard.DepthTestStateShard depthTestStateShard) {
        return RenderType.create(Brutality.MOD_ID + ":fire_" + depthTestStateShard,
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                512, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(InternalShaders::getRenderTypeFireShader))
                        .setDepthTestState(depthTestStateShard)
                        .createCompositeState(true)
        );
    }


    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            // screen space shaders don't need quad size aka texture


            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_water"),
                    DefaultVertexFormat.POSITION_COLOR), InternalShaders::setRenderTypeWaterShader);


            // ─────────────────────────────────────────────────────────────────────────────

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_fire"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeFireShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_encrypted"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeEncryptedShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_smoke"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeSmokeShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_embers"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeEmbersShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_electricity"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeElectricityShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_stygian"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeStygianShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_void"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeVoidShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_box_shadow"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeBoxShadowShader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), Brutality.loc("rendertype_stars"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), InternalShaders::setRenderTypeStarsShader);


            Brutality.LOGGER.info("Successfully consolidated and loaded internal shaders.");
        } catch (IOException exception) {
            Brutality.LOGGER.error("Failed to register unified pipeline shaders");
            exception.printStackTrace();
        }
    }

    public static class InternalShaders {
        private static ShaderInstance renderTypeFireShader;
        public static ShaderInstance getRenderTypeFireShader() {
            renderTypeFireShader.safeGetUniform("GuiScale").set((float) Minecraft.getInstance().getWindow().getGuiScale());
            return renderTypeFireShader;
        }
        public static void setRenderTypeFireShader(ShaderInstance instance) { renderTypeFireShader = instance; }

        private static ShaderInstance renderTypeEncryptedShader;
        public static ShaderInstance getRenderTypeEncryptedShader() { return renderTypeEncryptedShader; }
        public static void setRenderTypeEncryptedShader(ShaderInstance instance) { renderTypeEncryptedShader = instance; }

        private static ShaderInstance renderTypeWaterShader;
        public static ShaderInstance getRenderTypeWaterShader() { return renderTypeWaterShader; }
        public static void setRenderTypeWaterShader(ShaderInstance instance) { renderTypeWaterShader = instance; }

        private static ShaderInstance renderTypeStarsShader;
        public static ShaderInstance getRenderTypeStarsShader() { return renderTypeStarsShader; }
        public static void setRenderTypeStarsShader(ShaderInstance instance) { renderTypeStarsShader = instance; }

        private static ShaderInstance renderTypeSmokeShader;
        public static ShaderInstance getRenderTypeSmokeShader() { return renderTypeSmokeShader; }
        public static void setRenderTypeSmokeShader(ShaderInstance instance) { renderTypeSmokeShader = instance; }

        private static ShaderInstance renderTypeEmbersShader;
        public static ShaderInstance getRenderTypeEmbersShader() { return renderTypeEmbersShader; }
        public static void setRenderTypeEmbersShader(ShaderInstance instance) { renderTypeEmbersShader = instance; }

        private static ShaderInstance renderTypeElectricityShader;
        public static ShaderInstance getRenderTypeElectricityShader() { return renderTypeElectricityShader; }
        public static void setRenderTypeElectricityShader(ShaderInstance instance) { renderTypeElectricityShader = instance; }

        private static ShaderInstance renderTypeStygianShader;
        public static ShaderInstance getRenderTypeStygianShader() { return renderTypeStygianShader; }
        public static void setRenderTypeStygianShader(ShaderInstance instance) { renderTypeStygianShader = instance; }

        private static ShaderInstance renderTypeVoidShader;
        public static ShaderInstance getRenderTypeVoidShader() { return renderTypeVoidShader; }
        public static void setRenderTypeVoidShader(ShaderInstance instance) { renderTypeVoidShader = instance; }

        private static ShaderInstance renderTypeBoxShadowShader;
        public static ShaderInstance getRenderTypeBoxShadowShader() { return renderTypeBoxShadowShader; }
        public static void setRenderTypeBoxShadowShader(ShaderInstance instance) { renderTypeBoxShadowShader = instance; }

    }
}

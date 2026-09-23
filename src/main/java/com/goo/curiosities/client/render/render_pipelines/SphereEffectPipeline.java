package com.goo.curiosities.client.render.render_pipelines;

import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ScreenPostEffectPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public abstract class SphereEffectPipeline extends ScreenPostEffectPipeline {

    @Override
    public void onBeforeProcess(PostChain chain, RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();

        RenderTarget maskTarget = PostEffectRegistry.getTempTarget(
                getLocation(),
                getStage(),
                "sphere_mask"
        );

        chain.passes.forEach(pass -> {
            EffectInstance effect = pass.getEffect();

            // pass scene and sphere depth samplers to post process
            effect.setSampler("SceneDepth", mc.getMainRenderTarget()::getDepthTextureId);
            if (maskTarget != null) {
                effect.setSampler("SphereDepth", maskTarget::getDepthTextureId);
            }

            effect.safeGetUniform("GameTime").set(RenderSystem.getShaderGameTime());
        });
    }
}
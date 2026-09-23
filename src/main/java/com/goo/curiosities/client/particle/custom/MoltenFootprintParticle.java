package com.goo.curiosities.client.particle.custom;

import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.client.particle.FlatParticle;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: make into FootprintParticle and do inheritance
public class MoltenFootprintParticle extends FlatParticle {
    public MoltenFootprintParticle(ClientLevel level, double x, double y, double z, float radius, float pitch, float yaw, float roll) {
        super(level, x, y, z, radius, pitch, yaw, roll);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        // Start at white-hot intensity
        this.setColor(1.0F, 0.95F, 0.8F);
        this.setLifetime(60);
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        super.render(buffer, camera, partialTicks);
        PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLOOM_SHADER_LOCATION, ShaderPipeline.PipelineStage.WORLD);
        VertexConsumer effectConsumer = GLRenderTypes.BLOOM_BUFFER_SOURCE.getBuffer(GLRenderTypes.getBloomRenderType(TextureAtlas.LOCATION_PARTICLES, RenderStateShard.LEQUAL_DEPTH_TEST, RenderStateShard.NO_CULL));
        super.render(effectConsumer, camera, partialTicks);
    }

    // ai-assisted, I got lazy trying to figure out what colors are best for a molten look
    @Override
    public void tick() {
        super.tick();

        float progress = (float) this.age / (float) this.lifetime;

        // colors
        int whiteHot = 0xFFFFFBE0; // Extreme heat initial strike (~1500°C)
        int brightYellow = Colors.FIRE[0];
        int moltenOrange = 0xFFFF5500; // Liquid magma core
        int deepGrenadier = Colors.FIRE[2]; // Cooling red crust
        int cooledCrust = 0xFF1A0A0A; // Near-black cooled rock

        int color;

        // interpolation
        if (progress < 0.15F) {
            // (0% - 15% lifetime)
            float stageProgress = progress / 0.15F;
            color = FastColor.ARGB32.lerp(stageProgress, whiteHot, brightYellow);
        } else if (progress < 0.45F) {
            // (15% - 45% lifetime)
            float stageProgress = (progress - 0.15F) / 0.30F;
            color = FastColor.ARGB32.lerp(stageProgress, brightYellow, moltenOrange);
        } else if (progress < 0.80F) {
            // (45% - 80% lifetime)
            float stageProgress = (progress - 0.45F) / 0.35F;
            color = FastColor.ARGB32.lerp(stageProgress, moltenOrange, deepGrenadier);
        } else {
            // (80% - 100% lifetime)
            float stageProgress = (progress - 0.80F) / 0.20F;
            color = FastColor.ARGB32.lerp(stageProgress, deepGrenadier, cooledCrust);
        }

        this.setColor(
                FastColor.ARGB32.red(color) / 255.0F,
                FastColor.ARGB32.green(color) / 255.0F,
                FastColor.ARGB32.blue(color) / 255.0F
        );

        // keep fully opaque while hot, then fade out quickly at the end as it crusts over
        if (progress > 0.75F) {
            float alphaProgress = (progress - 0.75F) / 0.25F;
            this.setAlpha(1.0F - alphaProgress);
        } else {
            this.setAlpha(1.0F);
        }
    }

    @Override
    protected int getLightColor(float partialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    public static class Provider implements ParticleProvider<FlatParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public @Nullable Particle createParticle(FlatParticleOption data, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MoltenFootprintParticle flatParticle = new MoltenFootprintParticle(level, x, y, z, data.radius(), data.rotX(), data.rotY(), data.rotZ());
            flatParticle.pickSprite(this.sprites);
            return flatParticle;
        }
    }
}

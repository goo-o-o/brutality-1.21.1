package com.goo.curiosities.client.particle.custom;

import com.goo.goo_lib.client.particle.FlatParticle;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import com.goo.goo_lib.client.particle.gui.EmberGuiParticle;
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
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class EmberParticle extends FlatParticle {
    // physical parameters for movement simulation
    private final float draftSpeed;
    private final float wobbleFrequency;
    private final float wobbleMagnitude;
    private final float initialPhase;

    public EmberParticle(ClientLevel level, double x, double y, double z, float radius, float pitch, float yaw, float roll) {
        super(level, x, y, z, radius, pitch, yaw, roll);

        // remove constant negative gravity so particle reaches terminal velocity
        this.gravity = 0.0F;
        this.friction = 0.96F; // drag to prevent infinite speed buildup

        ThreadLocalRandom random = ThreadLocalRandom.current();

        // random movement profile
        this.draftSpeed = random.nextFloat(0.02F, 0.05F);
        this.wobbleFrequency = random.nextFloat(0.1F, 0.25F);
        this.wobbleMagnitude = random.nextFloat(0.01F, 0.03F);
        this.initialPhase = random.nextFloat(0.0F, (float) (Math.PI * 2));

        // initial burst of speed
        this.xd = random.nextFloat(-0.02F, 0.02F);
        this.yd = random.nextFloat(0.05F, 0.1F);
        this.zd = random.nextFloat(-0.02F, 0.02F);

        this.withRotation(
                random.nextFloat(0.0F, 360.0F),
                random.nextFloat(0.0F, 360.0F),
                random.nextFloat(0.0F, 360.0F)
        );

        float rotateSpeed = random.nextFloat(3F, 6F);
        this.withAngularVelocity(
                random.nextFloat(-4.0F, 4.0F) * rotateSpeed,
                random.nextFloat(-4.0F, 4.0F) * rotateSpeed,
                random.nextFloat(-6.0F, 6.0F) * rotateSpeed
        );

        this.setColor(1.0F, 0.95F, 0.8F);
        this.setLifetime(level.getRandom().nextIntBetweenInclusive(20, 40));
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        super.render(buffer, camera, partialTicks);
        PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLOOM_SHADER_LOCATION, ShaderPipeline.PipelineStage.WORLD);
        VertexConsumer effectConsumer = GLRenderTypes.BLOOM_BUFFER_SOURCE.getBuffer(GLRenderTypes.getBloomRenderType(TextureAtlas.LOCATION_PARTICLES, RenderStateShard.LEQUAL_DEPTH_TEST, RenderStateShard.NO_CULL));
        super.render(effectConsumer, camera, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();

        // float up
        this.yd += this.draftSpeed * 0.2F;

        // use game time to calculate a shared wind across all particles
        long gameTime = this.level.getGameTime();
        float windAngle = (gameTime % 24000) * 0.005F; // smooth full rotation

        // directional wind vector
        float windX = Mth.cos(windAngle) * 0.008F;
        float windZ = Mth.sin(windAngle) * 0.008F;

        // particle-local wobble overlay so they don't move in a rigid grid
        float localTime = (this.age + this.initialPhase) * this.wobbleFrequency;
        float localWobbleX = Mth.cos(localTime) * this.wobbleMagnitude;
        float localWobbleZ = Mth.sin(localTime * 0.8F) * this.wobbleMagnitude;

        // apply global wind + local wobble
        this.xd += windX + localWobbleX;
        this.zd += windZ + localWobbleZ;

        // color stuffs
        float progress = (float) this.age / (float) this.lifetime;
        if (progress < 0.5F) {
            float t = progress * 2.0F;
            this.rCol = Mth.lerp(t, EmberGuiParticle.START_R, EmberGuiParticle.MID_R);
            this.gCol = Mth.lerp(t, EmberGuiParticle.START_G, EmberGuiParticle.MID_G);
            this.bCol = Mth.lerp(t, EmberGuiParticle.START_B, EmberGuiParticle.MID_B);
            this.alpha = 1.0F;
        } else {
            float t = (progress - 0.5F) * 2.0F;
            this.rCol = Mth.lerp(t, EmberGuiParticle.MID_R, EmberGuiParticle.END_R);
            this.gCol = Mth.lerp(t, EmberGuiParticle.MID_G, EmberGuiParticle.END_G);
            this.bCol = Mth.lerp(t, EmberGuiParticle.MID_B, EmberGuiParticle.END_B);
        }

        if (progress > 0.7F) {
            float fadeProgress = (progress - 0.7F) / 0.3F;
            this.alpha = Mth.lerp(fadeProgress, 1.0F, 0.0F);
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
            EmberParticle flatParticle = new EmberParticle(level, x, y, z, data.radius(), data.rotX(), data.rotY(), data.rotZ());
            flatParticle.pickSprite(this.sprites);
            return flatParticle;
        }
    }
}

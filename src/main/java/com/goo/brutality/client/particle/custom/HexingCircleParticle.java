package com.goo.brutality.client.particle.custom;

import com.goo.brutality.util.ParticleUtil;
import com.goo.goo_lib.client.particle.FlatParticle;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class HexingCircleParticle extends FlatParticle {
    private final SpriteSet sprites;

    public HexingCircleParticle(ClientLevel level, double x, double y, double z, float size, float pitch, float yaw, float roll, SpriteSet sprites) {
        super(level, x, y, z, size, pitch, yaw, roll);
        this.lifetime = 2000;
        this.sprites = sprites;
        setParticleSpeed(0, 0, 0);
        setAlpha(1);
    }

    @Override
    public void tick() {
        super.tick();

        float progress = (float) this.age / (float) this.lifetime;
        float startFadeProgress = 0.8F;

        if (progress > startFadeProgress) {
            float windowProgress = (progress - startFadeProgress) / (1.0F - startFadeProgress);

            float alphaFade = 1.0F - windowProgress;

            this.setAlpha(alphaFade);
        }

    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        float continuousAge = (float) this.age + partialTicks;
        continuousAge *= 0.015F;
        Quaternionf rotation = this.getRotations(partialTicks);

        TextureAtlasSprite base = ParticleUtil.getDirectSprite(sprites, 0, 3);
        TextureAtlasSprite inner = ParticleUtil.getDirectSprite(sprites, 1, 3);
        TextureAtlasSprite outer = ParticleUtil.getDirectSprite(sprites, 2, 3);

        setSprite(base);
        renderRotatedQuad(buffer, camera, rotation, partialTicks);

        setSprite(inner);
        Quaternionf first = new Quaternionf(rotation).rotateLocalY(continuousAge);
        renderRotatedQuad(buffer, camera, first, partialTicks);

        setSprite(outer);
        Quaternionf second = new Quaternionf(rotation).rotateLocalY(-continuousAge);
        renderRotatedQuad(buffer, camera, second, partialTicks);

    }


    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return GLRenderTypes.PARTICLE_SHEET_TRANSLUCENT_NO_FOG;
    }


    public static class Provider implements ParticleProvider<FlatParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public @Nullable Particle createParticle(FlatParticleOption data, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new HexingCircleParticle(level, x, y, z, data.size(), data.rotX(), data.rotY(), data.rotZ(), sprites);
        }
    }
}

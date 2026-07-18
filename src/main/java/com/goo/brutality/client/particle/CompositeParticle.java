package com.goo.brutality.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CompositeParticle extends TextureSheetParticle {
    protected final SpriteSet spriteSet;
    protected float angularVelocity;
    protected float angularAcceleration;
    protected boolean fullbright;

    private ParticleRenderType renderType = ParticleRenderType.PARTICLE_SHEET_LIT;
    private Consumer<CompositeParticle> tickBehavior;

    public CompositeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.spriteSet = spriteSet;

        this.setSize(0.2F, 0.2F);
        this.quadSize *= 1.6F;
        this.lifetime = Math.max(1, 24 + (this.random.nextInt(12) - 6));
        this.hasPhysics = true;
    }

    public void setColor(int color) {
        rCol = FastColor.ARGB32.red(color) / 255F;
        gCol = FastColor.ARGB32.green(color) / 255F;
        bCol = FastColor.ARGB32.blue(color) / 255F;
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    public int getAge() {
        return age;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
    }

    public void setQuadSize(float quadSize) {
        this.quadSize = quadSize;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getQuadSize() {
        return getQuadSize(1);
    }

    public void setAngularVelocityAndAcceleration(float velocity, float acceleration) {
        setAngularVelocity(velocity);
        setAngularAcceleration(acceleration);
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setAngularAcceleration(float acceleration) {
        this.angularAcceleration = acceleration;
    }

    public void setRandomRotation(float velScale, float accelScale) {
        int direction = this.level.getRandom().nextBoolean() ? -1 : 1;
        setAngularVelocity(0.5F * direction);
        setAngularAcceleration((0.5F / (this.lifetime / 2F)) * -direction);
    }

    public void setFullbright(boolean fullbright) {
        this.fullbright = fullbright;
    }

    @Override
    protected int getLightColor(float partialTick) {
        return fullbright ? LightTexture.FULL_BRIGHT : super.getLightColor(partialTick);
    }

    public CompositeParticle withRenderType(ParticleRenderType renderType) {
        this.renderType = renderType;
        return this;
    }

    public CompositeParticle withTickBehavior(Consumer<CompositeParticle> tickBehavior) {
        if (this.tickBehavior == null) {
            this.tickBehavior = tickBehavior;
        } else {
            this.tickBehavior = this.tickBehavior.andThen(tickBehavior);
        }
        return this;
    }

    public SpriteSet getSpriteSet() {
        return this.spriteSet;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.angularVelocity != 0 || this.angularAcceleration != 0) {
            this.oRoll = this.roll;
            this.roll += this.angularVelocity;
            this.angularVelocity += this.angularAcceleration;
        }
        if (this.tickBehavior != null) {
            this.tickBehavior.accept(this);
        }
    }


    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return this.renderType;
    }

    @FunctionalInterface
    public interface Builder {
        void configure(CompositeParticle p, ClientLevel level, double xSpeed, double ySpeed, double zSpeed);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        private final Builder builder;

        public Provider(SpriteSet spriteSet, Builder builder) {
            this.spriteSet = spriteSet;
            this.builder = builder;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            CompositeParticle p = new CompositeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            this.builder.configure(p, level, xSpeed, ySpeed, zSpeed);
            return p;
        }
    }


}
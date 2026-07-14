package com.goo.brutality.client.particle.custom;

import com.goo.goo_lib.client.particle.ComponentParticle;
import com.goo.goo_lib.client.particle.ComponentParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class MultiplierParticle extends ComponentParticle {
    private final float initialAlpha;
    private final float initialSize;
    @Nullable
    private Float multiplier;

    protected MultiplierParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, ComponentParticleOption options) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, options);
        this.initialSize = this.size;
        this.initialAlpha = this.alpha;
        this.multiplier = Float.valueOf(component.getString());
        String text = multiplier % 1 == 0
                ? String.format("x%.0f", multiplier)
                : String.format("x%.2f", multiplier);

        this.component = Component.literal(text).withStyle(options.component().getStyle());
    }

    @Override
    public void tick() {
        super.tick();

        float progress = (float) this.age / (float) this.lifetime; // 0 to 1
        float timeLeft = 1.0F - progress; // 1 to 0

        this.roll = (float) Mth.clamp(Mth.sin(this.age) * timeLeft * (multiplier != null ? Math.log(multiplier) * 15 : 20), -40.0F, 40.0F);
        // anim windows
        float fadeInWindow = 0.05F;
        float fadeOutWindow = 0.33F;

        if (progress < fadeInWindow) {
            // fade in
            float fadeInProgress = progress / fadeInWindow;

            // no need alpha
            this.size = this.initialSize * fadeInProgress;

        } else if (timeLeft < fadeOutWindow) {
            // fade out
            float fadeProgress = timeLeft / fadeOutWindow;

            this.alpha = this.initialAlpha * fadeProgress;
            this.size = this.initialSize * fadeProgress;

        } else {
            this.alpha = this.initialAlpha;
            this.size = this.initialSize;
        }
    }


    @Override
    protected int getLightColor(float partialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    public static class Provider implements ParticleProvider<ComponentParticleOption> {

        public @Nullable Particle createParticle(ComponentParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new MultiplierParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options);
        }
    }
}

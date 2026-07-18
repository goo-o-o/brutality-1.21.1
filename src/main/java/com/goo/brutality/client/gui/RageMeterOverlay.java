package com.goo.brutality.client.gui;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityAttachments;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalityTags;
import com.goo.brutality.util.CurioUtil;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.registry.PostEffectRegistry;
import com.goo.goo_lib.util.RenderUtil;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class RageMeterOverlay implements LayeredDraw.Layer {
    private static final int totalWidth = 64;
    private static final int totalHeight = 16;
    private float lastTargetRatio;
    private long flashStartTime;
    private float smoothRatio;
    private static final long flashDurationMillis = 200L;
    private int lastEndTick = -1;
    private int initialEnragedDuration = 1;
    private float activationRageRatio = 1.0F;


    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().player == null) return;
        if (Minecraft.getInstance().options.hideGui) return;
        Player player = Minecraft.getInstance().player;
        if (!CurioUtil.isWearingCurio(player, stack -> stack.is(BrutalityTags.Items.RAGE_ITEMS))) return;

        float targetRatio = 0.0F;
        MobEffectInstance enragedInstance = player.getEffect(BrutalityEffects.ENRAGED);
        boolean isEnraged = enragedInstance != null;

        if (isEnraged) {
            int currentDuration = enragedInstance.getDuration();
            int currentEndTick = player.tickCount + currentDuration;

            if (Math.abs(currentEndTick - this.lastEndTick) > 1) {
                this.lastEndTick = currentEndTick;
                this.initialEnragedDuration = currentDuration;
                // get the percentage at which it activated, in case the player has more than 100 rage
                this.activationRageRatio = (this.lastTargetRatio > 0.01F) ? this.lastTargetRatio : 1.0F;
            }

            float progress = Mth.clamp((float) currentDuration / (float) this.initialEnragedDuration, 0.0F, 1.0F);
            targetRatio = progress * this.activationRageRatio;
        } else {
            // reset if expired/cleared
            this.lastEndTick = -1;
            this.initialEnragedDuration = 1;

            float rage = player.getData(BrutalityAttachments.RAGE.get());
            double maxRage = player.getAttributeValue(BrutalityAttributes.MAX_RAGE);
            if (maxRage > 0) {
                targetRatio = (float) (rage / maxRage);
            }
        }
        float deltaTicks = deltaTracker.getGameTimeDeltaTicks();

        // safeguard against game freezes (such as my debugger, or some potato pc)
        float blendFactor = Mth.clamp(deltaTicks * 0.4F, 0.0F, 1.0F);
        if (Float.isNaN(blendFactor)) {
            blendFactor = 1.0F; // fallback to instant snap if the delta tracker glitched out
        }

        // recover smoothRatio instantly if a previous unhandled frame corrupted it
        if (Float.isNaN(this.smoothRatio)) {
            this.smoothRatio = targetRatio;
        }

        this.smoothRatio = Mth.lerp(blendFactor, this.smoothRatio, targetRatio);
        // check if target changed to trigger the time-based flash
        long now = Util.getMillis();
        if (targetRatio != this.lastTargetRatio) {
            // only trigger the visual flash if rage is increasing and we aren't ticking down an active effect
            if (targetRatio > this.lastTargetRatio && !isEnraged) {
                this.flashStartTime = now;
            }
            this.lastTargetRatio = targetRatio;
        }

        // calculate remaining time factor (1.0 down to 0.0)
        long elapsed = now - this.flashStartTime;
        float flashAlpha = 1.0f - Mth.clamp((float) elapsed / flashDurationMillis, 0.0f, 1.0f);

        Position position = BrutalityClientConfig.CONFIG.RAGE_METER_POSITION.get();
        Style style = BrutalityClientConfig.CONFIG.RAGE_METER_STYLE.get();

        boolean isMainArmLeft = player.getMainArm() == HumanoidArm.LEFT;
        boolean itemInOffhand = !player.getOffhandItem().isEmpty();
        int x = position.getX(guiGraphics, isMainArmLeft, itemInOffhand);
        int y = position.getY(guiGraphics);

        ResourceLocation base = Brutality.loc("rage_meter/" + style.name().toLowerCase(Locale.ROOT) + "/");
        ResourceLocation frame = base.withSuffix("frame");
        ResourceLocation meter = base.withSuffix("meter");
        ResourceLocation bg = base.withSuffix("bg");

        style.renderUnderlay(guiGraphics, x, y, smoothRatio);

        if (isEnraged) {
            long time = Util.getMillis();

            int currentDuration = enragedInstance.getDuration();
            float intensity = Mth.clamp((float) enragedInstance.getDuration() / this.initialEnragedDuration, 0.0F, 1.0F);
            int ticksElapsed = this.initialEnragedDuration - currentDuration;

            float overlayAlpha = 1.0F;
            if (ticksElapsed <= 5) {
                // fade in
                overlayAlpha = ticksElapsed / 5.0F;
            } else if (currentDuration <= 5) {
                // fade out
                overlayAlpha = currentDuration / 5.0F;
            }


            // cap the maximum opacity at 0.35F so the player can still see the game
            int alpha = (int) (overlayAlpha * 0.35F * 255);
            if (alpha > 0) {
                int redColor = FastColor.ARGB32.color(alpha, 255, 0, 0);
                guiGraphics.fill(RenderType.guiOverlay(), 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), redColor);
            }
            guiGraphics.pose().pushPose();

            // remaining time factor from 1.0 (start) down to 0.0 (near expiration)

            // shake amplifies slightly as rage runs out (inverted intensity)
            if (!Minecraft.getInstance().isPaused()) {
                float force = 1.5F + intensity;

                // calculate jitter offsets using high-frequency sine waves
                float shakeX = Mth.sin(time * 0.08F) * force;
                float shakeY = Mth.cos(time * 0.07F) * (force * 0.8F);

                // calculate smooth rotational rocking back and forth (-4 to +4 degrees max)
                float rotation = Mth.sin(time * 0.04F) * (((1 + intensity) * 2) * force);

                // apply transformations relative to the meter's origin point
                guiGraphics.pose().translate(x + (totalWidth / 2.0F), y + (totalHeight / 2.0F), 0.0F);
                guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(rotation));
                guiGraphics.pose().translate(-(x + (totalWidth / 2.0F)) + shakeX, -(y + (totalHeight / 2.0F)) + shakeY, 0.0F);
            }
        }

        guiGraphics.blitSprite(bg, x + style.meterOffset, y + 8, 401, style.meterWidth, 5);

        if (smoothRatio > 0) {

            RenderUtil.blitSprite(guiGraphics, meter, style.meterWidth, 5, 0, 0,
                    x + style.meterOffset, y + 8, 401, style.meterWidth * smoothRatio, 5);

            // draw flash indicator with safe alpha limits (0-255 max)
            if (flashAlpha > 0.0F) {
                RenderUtil.fill(RenderType.gui(), guiGraphics,
                        x + style.meterOffset, y + 8,
                        x + style.meterOffset + (style.meterWidth * smoothRatio), y + 8 + 5,
                        401,
                        FastColor.ARGB32.color((int) (flashAlpha * 255), 255, 255, 255));
            }

        }

        guiGraphics.blitSprite(frame, x, y, 402, totalWidth, totalHeight);
        style.renderOverlay(guiGraphics, x, y, smoothRatio);

        if (smoothRatio > 0) {
            PostEffectRegistry.renderGuiWithEffect(GLRenderTypes.BLOOM_SHADER_LOCATION, guiGraphics,
                    postPass -> {
                        if (postPass.getEffect().getName().equals("goo_lib:bloom_upsample"))
                            postPass.getEffect().safeGetUniform("Intensity").set(1.35F);
                    },
                    () -> {
                        RenderUtil.blitSprite(guiGraphics, meter, style.meterWidth, 5, 0, 0,
                                x + style.meterOffset, y + 8, 401, style.meterWidth * smoothRatio, 5);

                        if (flashAlpha > 0.0F) {
                            RenderUtil.fill(RenderType.gui(), guiGraphics,
                                    x + style.meterOffset, y + 8,
                                    x + style.meterOffset + (style.meterWidth * smoothRatio), y + 8 + 5,
                                    402,
                                    FastColor.ARGB32.color((int) (flashAlpha * 255), 255, 255, 255));
                        }
                    });

        }

        if (isEnraged) guiGraphics.pose().popPose();
    }


    public enum Position {
        TOP_RIGHT {
            @Override
            protected int getX(GuiGraphics gui, boolean isMainArmLeft, boolean itemInOffhand) {
                return gui.guiWidth() - totalWidth - pad;
            }

            @Override
            protected int getY(GuiGraphics gui) {
                return 3 + 16;
            }
        },
        TOP_LEFT {
            @Override
            protected int getX(GuiGraphics gui, boolean isMainArmLeft, boolean itemInOffhand) {
                return totalWidth + pad - totalWidth;
            }

            @Override
            protected int getY(GuiGraphics gui) {
                return 3 + 16;
            }
        },
        BOTTOM_RIGHT {
            @Override
            protected int getX(GuiGraphics gui, boolean isMainArmLeft, boolean itemInOffhand) {
                return gui.guiWidth() - totalWidth - pad;
            }

            @Override
            protected int getY(GuiGraphics gui) {
                return gui.guiHeight() - 3 - 16;
            }
        },
        BOTTOM_LEFT {
            @Override
            protected int getX(GuiGraphics gui, boolean isMainArmLeft, boolean itemInOffhand) {
                return totalWidth + pad - totalWidth;
            }

            @Override
            protected int getY(GuiGraphics gui) {
                return gui.guiHeight() - 3 - 16;
            }
        },
        HOTBAR_RIGHT {
            @Override
            protected int getX(GuiGraphics gui, boolean isMainArmLeft, boolean itemInOffhand) {
                return (gui.guiWidth() + 182) / 2 + pad + (isMainArmLeft && itemInOffhand ? 22 + pad : 0);
            }

            @Override
            protected int getY(GuiGraphics gui) {
                return gui.guiHeight() - 3 - 16;
            }
        },
        HOTBAR_LEFT {
            @Override
            protected int getX(GuiGraphics gui, boolean isMainArmLeft, boolean itemInOffhand) {
                return (gui.guiWidth() - 182) / 2 - pad - (!isMainArmLeft && itemInOffhand ? 22 + pad : 0) - totalWidth;
            }

            @Override
            protected int getY(GuiGraphics gui) {
                return gui.guiHeight() - 3 - 16;
            }
        };

        private static final int pad = 7;

        protected int getX(GuiGraphics gui, boolean isMainArmLeft, boolean itemInOffhand) {
            return 0;
        }

        protected int getY(GuiGraphics gui) {
            return 0;
        }
    }

    public enum Style {
        CLASSIC(56, 4) {
            final ResourceLocation skull = Brutality.loc("rage_meter/" + name().toLowerCase(Locale.ROOT) + "/skull");

            @Override
            protected void renderUnderlay(GuiGraphics guiGraphics, int x, int y, float ratio) {

                ShaderInstance shader = BrutalityRenderTypes.InternalShaders.FIRE.getInstance();
                if (shader != null)
                    shader.safeGetUniform("Amount").set(ratio);


                RenderUtil.fillWithUv(BrutalityRenderTypes.getFireRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), guiGraphics,
                        x, y - totalWidth + 8, x + totalWidth, y + 8, 399,
                        FastColor.ARGB32.color(255, 255, 255));

                PostEffectRegistry.renderGuiWithEffect(GLRenderTypes.BLOOM_SHADER_LOCATION, guiGraphics,
                        postPass -> {
                            if (postPass.getEffect().getName().equals("goo_lib:bloom_upsample"))
                                postPass.getEffect().safeGetUniform("Intensity").set(1.15F);
                        }, () -> RenderUtil.fillWithUv(BrutalityRenderTypes.getFireRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), guiGraphics,
                                x, y - totalWidth + 8, x + totalWidth, y + 8, 399,
                                FastColor.ARGB32.color(255, 255, 255)));
            }

            @Override
            protected void renderOverlay(GuiGraphics guiGraphics, int x, int y, float ratio) {
                guiGraphics.blitSprite(skull, x + totalWidth / 2 - 16 / 2, y - 9, 402, 16, 18);
            }
        },
        MECHANICAL(58, 3);

        final int meterWidth;
        final int meterOffset;

        Style(int meterWidth, int meterOffset) {
            this.meterWidth = meterWidth;
            this.meterOffset = meterOffset;
        }

        protected void renderOverlay(GuiGraphics guiGraphics, int x, int y, float ratio) {
        }

        protected void renderUnderlay(GuiGraphics guiGraphics, int x, int y, float ratio) {
        }
    }
}

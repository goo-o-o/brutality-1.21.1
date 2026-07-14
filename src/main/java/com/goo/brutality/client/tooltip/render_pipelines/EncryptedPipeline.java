package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.Mth;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EncryptedPipeline extends TooltipRenderPipeline {

    private final Set<GlitchBox> glitchBoxes = new HashSet<>();
    private final Set<RainDrop> raindrops = new HashSet<>();
    private long lastTickTime = -1;

    // config values for easy customization

    @Override
    protected void modifyColors() {
        this.borderStart = Colors.ERIN;
        this.borderEnd = Colors.PHTHALO_GREEN;
        this.bgStart = Colors.BLACK;
        this.bgEnd = Colors.BLACK;
    }

    @Override
    protected void renderOverlayPass() {
        float textScale = (float) BrutalityClientConfig.CONFIG.ENCRYPTED_TEXT_SCALE.getAsDouble(); // text size
        int padding = BrutalityClientConfig.CONFIG.ENCRYPTED_OBJECT_PADDING.getAsInt();


        ShaderInstance shader = BrutalityRenderTypes.InternalShaders.getRenderTypeEncryptedShader();
        if (shader != null)
            shader.safeGetUniform("CellSize").set(10F);

        RenderUtil.fillWithUv(BrutalityRenderTypes.getEncryptedRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), gui,
                pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, Colors.ERIN
        );


        gui.fillGradient(pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, TRANSPARENT, Colors.BLACK);
        long currentTime = Util.getMillis();

        // 1. Time-Based Animation Logic
        if (currentTime - lastTickTime >= BrutalityClientConfig.CONFIG.ENCRYPTED_UPDATE_INTERVAL.getAsLong()) {
            lastTickTime = currentTime;
            if (Minecraft.getInstance().level != null) {

                if (raindrops.size() < BrutalityClientConfig.CONFIG.ENCRYPTED_MAX_DROPLETS.getAsInt() && textScale > 0) {
                    if (RANDOM.nextFloat() <= 0.4F) {
                        int spawnX = pos.x + RANDOM.nextIntBetweenInclusive(-padding, width + padding);
                        int spawnY = pos.y + RANDOM.nextIntBetweenInclusive(-padding, padding);
                        raindrops.add(new RainDrop(spawnX, spawnY, height, textScale));
                    }
                }

                for (RainDrop drop : raindrops) {
                    drop.tick();
                }
                raindrops.removeIf(RainDrop::isDead);

                // Tick existing boxes and remove dead ones
                for (GlitchBox box : glitchBoxes) {
                    box.tick();
                }
                glitchBoxes.removeIf(GlitchBox::isDead);

                if (RANDOM.nextFloat() < 0.1) {
                    int spawnX = RANDOM.nextIntBetweenInclusive(-padding, this.width + padding) + pos.x;
                    int boxWidth = RANDOM.nextIntBetweenInclusive(10, 40);
                    int spawnY = RANDOM.nextIntBetweenInclusive(-padding, this.height + padding) + pos.y;
                    int boxHeight = RANDOM.nextIntBetweenInclusive(10, 40);

                    // Keep for 4 iterations (approx 200ms total)
                    glitchBoxes.add(new GlitchBox(spawnX, spawnY, boxWidth, boxHeight, 4));
                }
            }
        }

        // 2. Render Pass
        int currentBottomLimit = pos.y + height + padding;
        for (RainDrop drop : raindrops) {
            if (drop.x >= pos.x && drop.x <= pos.x + width) {
                drop.render(gui, currentBottomLimit, textScale);
            }
        }

        // glitching shapes
        for (GlitchBox box : glitchBoxes) {
            box.render(gui);
        }
    }

    private static class GlitchBox {
        public final int x, y, width, height;
        private int lifeLeft;

        public GlitchBox(int x, int y, int width, int height, int lifespan) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.lifeLeft = lifespan;
        }

        public void tick() {
            this.lifeLeft--;
        }

        public boolean isDead() {
            return this.lifeLeft <= 0;
        }

        public void render(GuiGraphics gui) {
            ShaderInstance shader = BrutalityRenderTypes.InternalShaders.getRenderTypeEncryptedShader();
            if (shader == null) return;
            shader.safeGetUniform("CellSize").set(width * 0.45F);
            RenderUtil.fillWithUv(BrutalityRenderTypes.getEncryptedRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), gui, x, y, x + width, y + height, 400, Colors.ERIN);
        }
    }

    private static class RainDrop {
        private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-=+{[}];:',./<>?";

        final Queue<DropCharacter> chars = new ConcurrentLinkedQueue<>();
        final int x;
        final int startY;
        final int capacity;
        final float visualRowHeight;

        int spawnedCount = 0;
        boolean isDead = false;

        private static class DropCharacter {
            private char character;
            public float oAlpha;
            public float alpha;
            public final int relativeY; // keeps position static once spawned

            public DropCharacter(char character, int relativeY) {
                this.alpha = oAlpha = 1.0F;
                this.character = character;
                this.relativeY = relativeY;
            }

            public void decrementAlpha() {
                this.oAlpha = alpha;
                this.alpha -= 0.04F; // completely fades over 20 updates
            }

            public void setCharacter(char c) {
                this.character = c;
            }
        }

        public RainDrop(int x, int y, float totalHeight, float scale) {
            this.x = x;
            this.startY = y;

            // dynamically scale total row capacity based on configuration size
            this.visualRowHeight = 9.0F * scale;
            this.capacity = Math.max(2, (int) (totalHeight / visualRowHeight));

            // start stream with the first leading element
            addNextCharacter();
        }

        private void addNextCharacter() {
            char randomChar = CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length()));
            int nextYOffset = spawnedCount * 9; // steps down in scaled pixel units

            chars.add(new DropCharacter(randomChar, nextYOffset));
            spawnedCount++;
        }

        public boolean isDead() {
            return isDead;
        }

        public void tick() {

            // age characters and apply a 5% mutation chance
            for (DropCharacter dc : chars) {
                dc.decrementAlpha();
                if (RANDOM.nextFloat() < 0.025F) {
                    dc.setCharacter(CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length())));
                }
            }

            // append next stream item below if within size bounds
            if (spawnedCount < capacity) {
                addNextCharacter();
            }

            // safely remove fully invisible items from the queue head
            DropCharacter head = chars.peek();
            while (head != null && head.alpha <= 0) {
                chars.poll();
                head = chars.peek();
            }

            if (chars.isEmpty()) {
                this.isDead = true;
            }
        }

        public void render(GuiGraphics gui, int bottomLimit, float scale) {
            Font font = Minecraft.getInstance().font;

            gui.pose().pushPose();
            gui.pose().scale(scale, scale, 1.0F);
            gui.pose().translate(0, 0, 400);
            float scaledX = this.x / scale;
            float scaledBottomLimit = bottomLimit / scale;

            int index = 0;
            int total = chars.size();
            int padding = BrutalityClientConfig.CONFIG.ENCRYPTED_OBJECT_PADDING.getAsInt();
            for (DropCharacter dc : chars) {
                // compute exact scaled rendering point
                float currentScaledY = (this.startY / scale) + dc.relativeY;

                if (currentScaledY >= scaledBottomLimit + padding) {
                    break;
                }

                if (dc.alpha <= 0) {
                    index++;
                    continue;
                }

                float lerpedAlpha = Mth.lerp(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true), dc.oAlpha, dc.alpha);
                int alphaInt = Math.clamp((int) (lerpedAlpha * 255), 0, 255);
                int color;

                // bright glow highlight on the lowest active character tip
                if (index == total - 1 && spawnedCount < capacity) {
                    color = (alphaInt << 24) | (140 << 16) | (255 << 8) | 160;
                } else {
                    color = (alphaInt << 24) | (12 << 16) | (180 << 8) | 42;
                }
                gui.drawString(font, String.valueOf(dc.character), scaledX, currentScaledY, color, false);

                index++;
            }
            gui.pose().translate(0, 0, -400);


            gui.pose().popPose();
        }
    }
}

package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.particle.gui.StygianGuiParticle;
import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.client.particle.gui.GuiParticleSystem;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;

import java.awt.*;

public class StygianPipeline extends TooltipRenderPipeline {
    private static final int[] STYGIAN_COLORS = new int[]{
            new Color(255, 0, 0).getRGB(),
            new Color(160, 0, 0).getRGB()
    };

    @Override
    protected void modifyColors() {


        this.borderStart = STYGIAN_COLORS[0];
        this.borderEnd = STYGIAN_COLORS[1];
        this.bgStart = Colors.BLACK;
        this.bgEnd = Colors.BLACK;
    }

    @Override
    protected void renderOverlayPass() {
        int iterations = BrutalityClientConfig.CONFIG.STYGIAN_ITERATIONS.getAsInt();
        if (iterations > 0) {
            ShaderInstance shader = BrutalityRenderTypes.InternalShaders.STYGIAN.getInstance();
            if (shader != null) {
                shader.safeGetUniform("Iterations").set(iterations);
                shader.safeGetUniform("Brightness").set(((float) BrutalityClientConfig.CONFIG.STYGIAN_BRIGHTNESS.getAsDouble()));
            }

            RenderUtil.fillWithUv(BrutalityRenderTypes.getStygianRenderType(RenderStateShard.NO_DEPTH_TEST),
                    gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, STYGIAN_COLORS[1]);
        }

        if (Util.getMillis() % BrutalityClientConfig.CONFIG.STYGIAN_SOUL_SPAWN_INTERVAL.getAsInt() == 0
                && BrutalityClientConfig.CONFIG.STYGIAN_SOUL_SIZE_MULTIPLIER.getAsDouble() > 0) {
            int x = pos.x + RANDOM.nextIntBetweenInclusive(0, width);
            int y = pos.y + RANDOM.nextIntBetweenInclusive(0, height);

            GuiParticleSystem.getInstance().add(new StygianGuiParticle(x, y, 400, 16F * (float) BrutalityClientConfig.CONFIG.STYGIAN_SOUL_SIZE_MULTIPLIER.getAsDouble(), RANDOM));

        }
    }
}
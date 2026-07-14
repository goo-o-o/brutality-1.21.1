package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.FastColor;

import java.awt.*;

public class CosmicPipeline extends TooltipRenderPipeline {

    private static final int[] COSMIC_COLORS = new int[]{
            new Color(200, 136, 231).getRGB(),
            new Color(173, 96, 255).getRGB(),
            new Color(116, 52, 213).getRGB(),
            new Color(81, 0, 188).getRGB(),
            new Color(17, 0, 81).getRGB(),
            new Color(0, 0, 0).getRGB()
    };

    @Override
    protected void modifyColors() {
        this.borderStart = COSMIC_COLORS[0];
        this.borderEnd = COSMIC_COLORS[2];
        this.bgStart = Colors.BLACK;
        this.bgEnd = Colors.BLACK;
    }


    @Override
    protected void renderOverlayPass() {
//        float chance = (float) BrutalityClientConfig.CONFIG.COSMIC_STAR_CHANCE.getAsDouble();
//        float scale = (float) BrutalityClientConfig.CONFIG.COSMIC_STAR_SIZE_MULTIPLIER.getAsDouble();
//        if (chance > 0 && scale > 0) {
        // I forgot, still render the nebula
        ShaderInstance shader = BrutalityRenderTypes.InternalShaders.getRenderTypeStarsShader();
        if (shader != null) {
            shader.safeGetUniform("StarChance").set((float) BrutalityClientConfig.CONFIG.COSMIC_STAR_CHANCE.getAsDouble());
            shader.safeGetUniform("StarScale").set((float) BrutalityClientConfig.CONFIG.COSMIC_STAR_SIZE_MULTIPLIER.getAsDouble());
            shader.safeGetUniform("RotationSpeed").set(((float) BrutalityClientConfig.CONFIG.COSMIC_STAR_ROTATION_SPEED.getAsDouble()));
            shader.safeGetUniform("BloomSize").set(((float) BrutalityClientConfig.CONFIG.COSMIC_STAR_BLOOM_SIZE.getAsDouble()));
        }

        RenderUtil.fillWithUv(BrutalityRenderTypes.getStarsRenderType(RenderStateShard.NO_DEPTH_TEST),
                gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, FastColor.ARGB32.color(255, 75, 225));
//        }

    }

}

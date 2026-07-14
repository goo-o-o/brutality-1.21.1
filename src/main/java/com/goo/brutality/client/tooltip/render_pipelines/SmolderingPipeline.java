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

public class SmolderingPipeline extends TooltipRenderPipeline {

    public static int[] SMOLDERING_COLORS = new int[]{
            new Color(255, 237, 43).getRGB(),
            new Color(255, 187, 30).getRGB(),
            new Color(255, 124, 0).getRGB(),
            new Color(255, 93, 0).getRGB(),
            new Color(224, 59, 0).getRGB(),
            new Color(160, 18, 0).getRGB()
    };

    @Override
    protected void modifyColors() {
        this.borderStart = SMOLDERING_COLORS[0];
        this.borderEnd = SMOLDERING_COLORS[5];
        this.bgStart = Colors.BLACK;
        this.bgEnd = FastColor.ARGB32.lerp(0.5F, SMOLDERING_COLORS[5], Colors.BLACK);
//        this.bgStart = TRANSPARENT;
//        this.bgEnd = TRANSPARENT;
    }

    @Override
    protected void renderOverlayPass() {
        int amount = BrutalityClientConfig.CONFIG.SMOLDERING_EMBER_AMOUNT.getAsInt();
        if (amount > 0) {
            ShaderInstance embers = BrutalityRenderTypes.InternalShaders.getRenderTypeEmbersShader();
            if (embers != null)
                embers.safeGetUniform("Amount").set(amount);
            RenderUtil.fillWithUv(BrutalityRenderTypes.getEmbersRenderType(RenderStateShard.LEQUAL_DEPTH_TEST),
                    gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, SMOLDERING_COLORS[5]); // disabled vertex color support for now as hardcoding the colors in the shader would make it easier to color match them to the actual border colors
        }

        float intensity = (float) BrutalityClientConfig.CONFIG.SMOLDERING_SMOKE_INTENSITY.getAsDouble();
        if (intensity > 0) {
            ShaderInstance smoke = BrutalityRenderTypes.InternalShaders.getRenderTypeSmokeShader();
            if (smoke != null)
                smoke.safeGetUniform("Intensity").set(intensity);
            RenderUtil.fillWithUv(BrutalityRenderTypes.getSmokeRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), gui, pos.x - 20, pos.y - 40, pos.x + width + 20, pos.y + height + 20, 400, FastColor.ARGB32.color(150, 150, 150));
        }
    }

}

package com.goo.curiosities.client.tooltip.render_pipelines;

import com.goo.curiosities.client.CuriositiesClientConfig;
import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.client.tooltip.TooltipRenderPipeline;
import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.FastColor;

import java.awt.*;

public class FirePipeline extends TooltipRenderPipeline {


    @Override
    protected void modifyColors() {
        this.borderStart = Colors.FIRE[0];
        this.borderEnd = Colors.FIRE[2];
        this.bgStart = Colors.BLACK;
        this.bgEnd = FastColor.ARGB32.lerp(0.5F, Colors.FIRE[2], Colors.BLACK);
//        this.bgStart = TRANSPARENT;
//        this.bgEnd = TRANSPARENT;
    }

    @Override
    protected void renderOverlayPass() {
        int amount = CuriositiesClientConfig.CONFIG.FIRE_EMBER_AMOUNT.getAsInt();
        if (amount > 0) {
            ShaderInstance embers = CuriositiesRenderTypes.InternalShaders.EMBERS.getInstance();
            if (embers != null)
                embers.safeGetUniform("Amount").set(amount);
            RenderUtil.fillWithUv(CuriositiesRenderTypes.getEmbersRenderType(RenderStateShard.LEQUAL_DEPTH_TEST),
                    gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, Colors.FIRE[2]); // disabled vertex color support for now as hardcoding the colors in the shader would make it easier to color match them to the actual border colors
        }

        float intensity = (float) CuriositiesClientConfig.CONFIG.FIRE_SMOKE_INTENSITY.getAsDouble();
        if (intensity > 0) {
            ShaderInstance smoke = CuriositiesRenderTypes.InternalShaders.SMOKE.getInstance();
            if (smoke != null)
                smoke.safeGetUniform("Intensity").set(intensity);
//            RenderUtil.fillWithUv(CuriositiesRenderTypes.getSmokeRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), gui, pos.x - 20, pos.y - 40, pos.x + width + 20, pos.y + height + 20, 400, FastColor.ARGB32.color(150, 150, 150));
            RenderUtil.fillWithUv(CuriositiesRenderTypes.getSmokeRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), gui, pos.x, pos.y, pos.x + width, pos.y + height, 400, FastColor.ARGB32.color(150, 150, 150));
        }
    }

}

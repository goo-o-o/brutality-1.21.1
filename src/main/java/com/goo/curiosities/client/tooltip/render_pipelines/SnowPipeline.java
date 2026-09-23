package com.goo.curiosities.client.tooltip.render_pipelines;

import com.goo.curiosities.client.CuriositiesClientConfig;
import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.client.tooltip.TooltipRenderPipeline;
import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;

public class SnowPipeline extends TooltipRenderPipeline {


    @Override
    protected void modifyColors() {
        this.borderStart = Colors.ICE[0];
        this.borderEnd = Colors.ICE[4];
        this.bgStart = Colors.ICE[2];
        this.bgEnd = Colors.ICE[3];
    }

    @Override
    protected void renderOverlayPass() {
        double speed = CuriositiesClientConfig.CONFIG.SNOW_SPEED.getAsDouble();
        if (speed > 0) {
            ShaderInstance snow = CuriositiesRenderTypes.InternalShaders.SNOW.getInstance();
            if (snow != null)
                snow.safeGetUniform("Speed").set((float) speed);
            RenderUtil.fillWithUv(CuriositiesRenderTypes.getSnowRenderType(RenderStateShard.LEQUAL_DEPTH_TEST),
                    gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, Colors.BLACK); // disabled vertex color support for now as hardcoding the colors in the shader would make it easier to color match them to the actual border colors
        }

    }

}

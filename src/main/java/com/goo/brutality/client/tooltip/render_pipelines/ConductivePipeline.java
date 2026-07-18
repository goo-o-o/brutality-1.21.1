package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import java.awt.*;

public class ConductivePipeline extends TooltipRenderPipeline {
    private static final ResourceLocation BORDER = Brutality.loc("tooltip/conductite_border");
    private static final int distanceFromEdge = 9;
    private static final ResourceLocation TESLA_COIL = Brutality.loc("tooltip/conductite_tesla_coil");
    private static final int coilWidth = 39, coilHeight = 66;

    private static final int[] CONDUCTIVE_COLORS = new int[]{
            new Color(62, 50, 43).getRGB(),
            new Color(93, 77, 65).getRGB(),
            new Color(255, 223, 81).getRGB()
    };

    @Override
    protected void modifyColors() {
        this.bgStart = FastColor.ARGB32.lerp(0.5F, FastColor.ARGB32.color(93, 77, 65), Colors.BLACK);
        this.bgEnd = FastColor.ARGB32.lerp(0.5F, FastColor.ARGB32.color(62, 50, 43), Colors.BLACK);
        this.borderStart = CONDUCTIVE_COLORS[2];
        this.borderEnd = CONDUCTIVE_COLORS[2];
    }

    @Override
    protected void renderOverlayPass() {

        float strikeChance = (float) BrutalityClientConfig.CONFIG.CONDUCTIVE_STRIKE_CHANCE.getAsDouble();
        if (strikeChance > 0) {
            float brightnessMultiplier = (float) BrutalityClientConfig.CONFIG.CONDUCTIVE_CORE_BRIGHTNESS_MULTIPLIER.getAsDouble();
            float strikeFadeout = (float) BrutalityClientConfig.CONFIG.CONDUCTIVE_STRIKE_FADEOUT.getAsDouble();
            float speed = (float) BrutalityClientConfig.CONFIG.CONDUCTIVE_STRIKE_SPEED.getAsDouble();
            float volatility = (float) BrutalityClientConfig.CONFIG.CONDUCTIVE_TENDRIL_VOLATILITY.getAsDouble();

            ShaderInstance shader = BrutalityRenderTypes.InternalShaders.ELECTRICITY.getInstance();
            if (shader != null) {
                shader.safeGetUniform("InnerBrightnessMultiplier").set(brightnessMultiplier);
                shader.safeGetUniform("StrikeFadeout").set(strikeFadeout);
                shader.safeGetUniform("StrikeChance").set(strikeChance);
                shader.safeGetUniform("Speed").set(speed);
                shader.safeGetUniform("TendrilVolatility").set(volatility);
            }

            RenderUtil.fillWithUv(BrutalityRenderTypes.getElectricityRenderType(RenderStateShard.LEQUAL_DEPTH_TEST), gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, FastColor.ARGB32.color(100, 255 - 50, 223 - 50, 81 - 50));
            // disabled vertex color support for now as hardcoding the colors in the shader would make it easier to color match them to the actual border colors

        }

        int topLeftX = pos.x - distanceFromEdge;
        int topLeftY = pos.y - distanceFromEdge;

        int borderWidth = this.width + distanceFromEdge * 2;
        int borderHeight = this.height + distanceFromEdge * 2;
        gui.blitSprite(BORDER, topLeftX, topLeftY, 401, borderWidth, borderHeight);

        int center = pos.x + this.width / 2;
        gui.blitSprite(TESLA_COIL, center - coilWidth / 2, pos.y - 67, 401, coilWidth, coilHeight);

    }

}

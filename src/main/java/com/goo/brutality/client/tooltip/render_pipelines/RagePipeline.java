package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.Colors;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;

public class RagePipeline extends TooltipRenderPipeline {
    private static final int[] RAGE_COLORS = new int[] {
            new Color(224, 59, 0).getRGB(),
            new Color(208, 1, 0).getRGB(),
    };

    private static final ResourceLocation ANGER_SYMBOL = Brutality.loc("textures/gui/tooltip/anger_symbol.png");
    private static final int TEXTURE_SIZE = 18;

    @Override
    protected void modifyColors() {
        this.borderStart = RAGE_COLORS[0];
        this.borderEnd = RAGE_COLORS[1];
        this.bgStart = Colors.BLACK;
        this.bgEnd = Colors.BLACK;
    }

    @Override
    protected void renderOverlayPass() {
        float time = RenderSystem.getShaderGameTime() * 1000F;

        float posX = pos.x + this.width + 2;
        float posY = pos.y - 2;

        // 2. Derive animation factors using distinct mathematical rhythms for erratic behavior
        float pulse = 1.0F + Mth.sin(time * 3.5F) * 0.08F + Mth.cos(time * 7.1F) * 0.04F;
        float swivel = Mth.sin(time * 2.2F) * 12.0F + Mth.cos(time * 5.6F) * 5.0F; // Rotation in degrees

        gui.pose().pushPose();

        // Translate to the target center point for stable scaling/rotation scaling hooks
        gui.pose().translate(posX, posY, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(swivel));
        gui.pose().scale(pulse, pulse, 1.0F);

        // Offset backward by half size to draw it perfectly centered on the translated origin
        int drawX = -TEXTURE_SIZE / 2;
        int drawY = -TEXTURE_SIZE / 2;


        gui.blit(ANGER_SYMBOL, drawX, drawY, 401, 0, 0, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);

        gui.pose().popPose();
    }
}
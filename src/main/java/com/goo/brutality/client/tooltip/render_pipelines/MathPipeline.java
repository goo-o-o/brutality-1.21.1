package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;

public class MathPipeline extends TooltipRenderPipeline {
    private static final ResourceLocation BORDER = Brutality.loc("tooltip/math_border");

    private static final ResourceLocation[] OBJECTS = new ResourceLocation[]{
            Brutality.loc("tooltip/green_chalk"),
            Brutality.loc("tooltip/red_chalk"),
            Brutality.loc("tooltip/blue_chalk"),
            Brutality.loc("tooltip/yellow_chalk"),
            Brutality.loc("tooltip/duster")
    };
    private boolean generated = false;

    private final ArrayList<BlackboardObject> blackboardObjects = new ArrayList<>();

    private record BlackboardObject(int textureIdx, int xOffset) {

    }

    private void generateBlackboardObjects() {
        if (generated || Minecraft.getInstance().level == null) return;
        RandomSource random = Minecraft.getInstance().level.getRandom();
        int totalObjects = width / 30;

        totalObjects *= (int) BrutalityClientConfig.CONFIG.MATH_BLACKBOARD_OBJECT_AMOUNT_MULTIPLIER.getAsDouble();
        for (int i = 0; i < totalObjects; i++) {
            int randX = random.nextIntBetweenInclusive(0, Math.max(1, width - 16));
            int textureIdx = random.nextInt(OBJECTS.length);
            blackboardObjects.add(new BlackboardObject(textureIdx, randX));
        }

        generated = true;
    }

    @Override
    protected void renderOverlayPass() {
        generateBlackboardObjects();

        int gap = 8;


        gui.fill(pos.x - gap + 3, pos.y - gap, pos.x + width + gap - 3, pos.y + height + gap - 3, 400, FastColor.ARGB32.color(39, 76, 67));
//        RenderUtil.fillWithUv(BrutalityRenderTypes.getMathRenderType(RenderStateShard.NO_DEPTH_TEST),
//                gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, FastColor.ARGB32.color(255, 255, 225));
        gui.fillGradient(pos.x - gap + 3, pos.y - gap, pos.x + width + gap - 3, pos.y + height + gap - 3, 400,
                Colors.TRANSPARENT, FastColor.ARGB32.color(150, 0, 0, 0));

        for (BlackboardObject object : blackboardObjects) {
            int currentX = pos.x + object.xOffset;
            gui.blit(
                    currentX, pos.y + height + (gap - 4) - 8, 400, 8, 8, Minecraft.getInstance().getGuiSprites().getSprite(OBJECTS[object.textureIdx])
            );
        }


        gui.blitSprite(BORDER, pos.x - gap, pos.y - gap, 401, width + (gap * 2), height + (gap * 2));
    }

}

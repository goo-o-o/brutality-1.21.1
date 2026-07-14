package com.goo.brutality.client.tooltip.render_pipelines;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.particle.gui.BubbleGuiParticle;
import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.client.tooltip.TooltipRenderPipeline;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.TooltipUtil;
import com.goo.goo_lib.client.particle.gui.GuiParticle;
import com.goo.goo_lib.client.particle.gui.GuiParticleSystem;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;

import java.awt.*;
import java.util.ArrayList;

public class CoralinePipeline extends TooltipRenderPipeline {
    private static final TextureAtlas BLOCK_ATLAS = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
    private static final ResourceLocation[] SEAGRASS = new ResourceLocation[]{
            ResourceLocation.withDefaultNamespace("block/seagrass"),
            ResourceLocation.withDefaultNamespace("block/tall_seagrass_bottom"),
            ResourceLocation.withDefaultNamespace("block/tall_seagrass_top")
    };

    private static final ResourceLocation[] CORAL = new ResourceLocation[]{
            Brutality.loc("tooltip/anemonite_coral_small"),
            Brutality.loc("tooltip/anemonite_coral_big")
    };
    private boolean generated = false;

    private record MarineObject(boolean tall, boolean seagrass, boolean mirrored, int xOffset) {
    }

    private final ArrayList<MarineObject> marineObjectList = new ArrayList<>();
    private static final int[] CORALINE_COLORS = new int[]{
            new Color(232, 248, 255).getRGB(), // 0
            new Color(127, 238, 255).getRGB(), // 1
            new Color(94, 177, 255).getRGB(), // 2
            new Color(39, 142, 206).getRGB(), // 3
            new Color(42, 104, 204).getRGB(), // 4
            new Color(45, 68, 173).getRGB(), // 5
            new Color(109, 83, 204).getRGB(), // 6
            new Color(68, 22, 126).getRGB(), // 7
            new Color(223, 75, 129).getRGB(), // 8
            new Color(130, 68, 124).getRGB(), // 9
            new Color(246, 186, 93).getRGB(), // 10
            new Color(193, 100, 54).getRGB(), // 11
            new Color(147, 35, 20).getRGB(), // 12
            new Color(76, 13, 28).getRGB() // 13
    };


    private void generateMarineObjects() {
        if (generated || Minecraft.getInstance().level == null) return;

        RandomSource random = Minecraft.getInstance().level.getRandom();
        int totalPlants = width / 12 + Minecraft.getInstance().level.getRandom().nextIntBetweenInclusive(0, 8);
        totalPlants *= (int) BrutalityClientConfig.CONFIG.CORALINE_MARINE_OBJECT_AMOUNT_MULTIPLIER.getAsDouble();
        for (int i = 0; i < totalPlants; i++) {
            boolean isTall = random.nextFloat() <= BrutalityClientConfig.CONFIG.CORALINE_TALL_CHANCE.getAsDouble();
            boolean isSeagrass = random.nextFloat() <= BrutalityClientConfig.CONFIG.CORALINE_SEAGRASS_CHANCE.getAsDouble();
            boolean isMirrored = random.nextBoolean();
            int randX = random.nextIntBetweenInclusive(0, Math.max(1, width - 16));
            marineObjectList.add(new MarineObject(isTall, isSeagrass, isMirrored, randX));
        }
        generated = true;
    }

    @Override
    protected void modifyColors() {
        this.borderStart = CORALINE_COLORS[10];
        this.borderEnd = CORALINE_COLORS[11];
        this.bgStart = CORALINE_COLORS[3];
        this.bgEnd = CORALINE_COLORS[9];
    }

    @Override
    protected void renderOverlayPass() {
        generateMarineObjects();
        RenderUtil.fillWithUv(BrutalityRenderTypes.getWaterRenderType(RenderStateShard.NO_DEPTH_TEST),
                gui, pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, FastColor.ARGB32.color(15, 75, 225));
        gui.fillGradient(pos.x - 2, pos.y - 2, pos.x + width + 2, pos.y + height + 2, 400, TRANSPARENT, CORALINE_COLORS[9]);

        if (Util.getMillis() % BrutalityClientConfig.CONFIG.CORALINE_BUBBLE_SPAWN_INTERVAL.getAsInt() == 0
                && BrutalityClientConfig.CONFIG.CORALINE_BUBBLE_SIZE_MULTIPLIER.getAsDouble() > 0) {
            float x = pos.x + RANDOM.nextIntBetweenInclusive(0, width);
            float y = pos.y + height - 5;

            GuiParticle guiParticle = new BubbleGuiParticle(x, y, 400, 0F,
                    (float) (Mth.randomBetween(RANDOM, -0.4F, -1.2F) * BrutalityClientConfig.CONFIG.CORALINE_BUBBLE_SPEED_MULTIPLIER.getAsDouble()),
                    (float) (RANDOM.nextIntBetweenInclusive(6, 12) * BrutalityClientConfig.CONFIG.CORALINE_BUBBLE_SIZE_MULTIPLIER.getAsDouble()), RANDOM);
            GuiParticleSystem.getInstance().add(guiParticle);
        }


        for (MarineObject marineObject : marineObjectList) {

            int currentX = pos.x + marineObject.xOffset;

            if (marineObject.seagrass) {
                if (marineObject.tall) {
                    TooltipUtil.blitMirrored(gui,
                            BLOCK_ATLAS.getSprite(SEAGRASS[2]),
                            currentX, currentX + 16, pos.y - 19 - 16, pos.y - 16,
                            400,
                            16, 16, 0, 0,
                            marineObject.mirrored, false
                    );
                    TooltipUtil.blitMirrored(gui,
                            BLOCK_ATLAS.getSprite(SEAGRASS[1]),
                            currentX, currentX + 16, pos.y - 16, pos.y - 3,
                            400,
                            16, 16, 0, 0,
                            marineObject.mirrored, false
                    );
                } else {
                    TooltipUtil.blitMirrored(gui,
                            BLOCK_ATLAS.getSprite(SEAGRASS[0]),
                            currentX, currentX + 16, pos.y - 19, pos.y - 3,
                            400,
                            16, 16, 0, 0,
                            marineObject.mirrored, false
                    );
                }
            } else {
                if (marineObject.tall) {
                    TooltipUtil.blitMirrored(gui,
                            Minecraft.getInstance().getGuiSprites().getSprite(CORAL[1]),
                            currentX, currentX + 32, pos.y - 19 - 16, pos.y - 3,
                            400,
                            32, 32, 0, 0,
                            marineObject.mirrored, false
                    );
                } else {
                    TooltipUtil.blitMirrored(gui,
                            Minecraft.getInstance().getGuiSprites().getSprite(CORAL[0]),
                            currentX, currentX + 16, pos.y - 19, pos.y - 3,
                            400,
                            16, 16, 0, 0,
                            marineObject.mirrored, false
                    );
                }
            }
        }
    }

    @Override
    public void onClose() {
        marineObjectList.clear();
    }
}
package com.goo.brutality.client.render.gui.screen;

import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.menu.DivineImmortalsRingMenu;
import com.goo.brutality.util.Colors;
import com.goo.brutality.util.Styles;
import com.goo.goo_lib.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DivineImmortalsRingScreen extends AbstractContainerScreen<DivineImmortalsRingMenu> {
    private static final ResourceLocation TEXTURE =
            Brutality.loc("textures/gui/container/divine_immortals_ring_inventory.png");

    private final Component finalTitle;
    private final Component finalInventoryTitle;

    public DivineImmortalsRingScreen(DivineImmortalsRingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 198;
        this.inventoryLabelY = this.imageHeight - 93;
        this.titleLabelY = -64;
        finalTitle = this.title.copy().withStyle(Styles.Special.STYGIAN);
        finalInventoryTitle = this.playerInventoryTitle.copy().withColor(Colors.BRIGHT_RED);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.finalTitle)) / 2;

    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 2.0F);

        guiGraphics.drawString(this.font, finalTitle, this.titleLabelX, this.titleLabelY, 4210752, true);
        guiGraphics.drawString(this.font, finalInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);

        guiGraphics.pose().popPose();
    }


    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        // custom box dimensions
        float boxWidth = 200.0F;
        float boxHeight = 200.0F;

        // exact center point of the 2x2 middle slot relative to top-left of texture
        float slotCenterX = leftPos + 88.0F + 1.0F; // 89.0F
        float slotCenterY = topPos + 50.0F + 1.0F;  // 51.0F

        // derive quad extents centered around slotCenter
        float x1 = slotCenterX - (boxWidth / 2.0F);  // 89.0 - 100.0 = -11.0 from leftPos
        float y1 = slotCenterY - (boxHeight / 2.0F); // 51.0 - 100.0 = -49.0 from topPos
        float x2 = x1 + boxWidth;
        float y2 = y1 + boxHeight;

        // render custom nebula shader box centered over the slot
        RenderUtil.fillWithUv(
                BrutalityRenderTypes.getNebulaRenderType(RenderStateShard.LEQUAL_DEPTH_TEST),
                graphics,
                x1, y1, x2, y2,
                0, // z-level
                Colors.DIVINE_IMMORTALS_RING_RED
        );

        // render base GUI texture over the shader
        graphics.blit(TEXTURE, leftPos, topPos, 1, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
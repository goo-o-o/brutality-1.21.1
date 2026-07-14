package com.goo.brutality.client.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;

public abstract class TooltipRenderPipeline {
    protected static final RandomSource RANDOM = RandomSource.create();
    // common constants
    protected static int TRANSPARENT = FastColor.ARGB32.color(0, 0);

    // ─────────────────────────────────────────────────────────────────────────────────

    protected GuiGraphics gui;
    protected Font font;
    protected List<ClientTooltipComponent> components;
    protected int mouseX, mouseY;
    protected ClientTooltipPositioner positioner;
    protected ItemStack itemStack;

    public int width;
    public int height;
    public final Vector2i pos = new Vector2i();

    protected int bgStart = 0xF0100010;
    protected int bgEnd = 0xF0100010;
    protected int borderStart = 0x505000FF;
    protected int borderEnd = 0x5028007F;

    /**
     * Core runner method invoked by the Mixin.
     * Do not override this unless you want to replace the entire engine execution flow.
     */
    public final boolean run(GuiGraphics gui, ItemStack stack, Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, int screenWidth, int screenHeight, ClientTooltipPositioner positioner) {
        if (components.isEmpty()) return false;
        this.gui = gui;
        this.font = font;
        this.components = components;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.positioner = positioner;
        this.itemStack = stack;


        // size
        calculateSize();
        modifySize(); // hook here

        // layout
        Vector2ic placement = this.positioner.positionTooltip(screenWidth, screenHeight, mouseX, mouseY, this.width, this.height);
        this.pos.set(placement.x(), placement.y());
        modifyPosition(); // hook here

        // colors
        calculateColors(); // event here
        modifyColors(); // hook here

        // actual rendering
        renderBackgroundPass();
        renderOverlayPass();
        renderContentPass();
        // nothing should render over text
//        fillRenderTypeWithColor(GLRenderTypes.getBlurRenderType(RenderStateShard.NO_DEPTH_TEST), gui,pos.x - 40, pos.y - 40, pos.x + width + 40, pos.y + height + 20, 401, FastColor.ARGB32.color(255, 255, 255));

        return true;
    }

    /**
     * Overriding methods below should be all that's needed
     */
    protected void calculateSize() {
        int w = 0;
        int h = this.components.size() == 1 ? -2 : 0;
        for (ClientTooltipComponent comp : this.components) {
            int compWidth = comp.getWidth(this.font);
            if (compWidth > w) w = compWidth;
            h += comp.getHeight();
        }
        this.width = w;
        this.height = h;
    }

    /**
     * Hook to distort or expand width/height parameters easily
     */
    protected void modifySize() {
    }

    /**
     * Hook to offset or re-anchor positions easily
     */
    protected void modifyPosition() {
    }

    protected final void calculateColors() {
        RenderTooltipEvent.Color colorEvent = ClientHooks.onRenderTooltipColor(itemStack, gui, pos.x, pos.y, font, components);
        this.bgStart = colorEvent.getBackgroundStart();
        this.bgEnd = colorEvent.getBackgroundEnd();
        this.borderStart = colorEvent.getBorderStart();
        this.borderEnd = colorEvent.getBorderEnd();
    }

    /**
     * Hook to force-overwrite colors, gradients, or custom ARGB parameters
     */
    protected void modifyColors() {
    }

    /**
     * Draws the physical vanilla tooltip frame. Override to disable or replace entirely.
     */
    protected void renderBackgroundPass() {
        gui.drawManaged(() ->
                TooltipRenderUtil.renderTooltipBackground(
                        gui, pos.x, pos.y, width, height, 400, bgStart, bgEnd, borderStart, borderEnd
                )
        );
    }

    /**
     * Empty by default. Ideal insertion point for glowing shaders or extra textures
     */
    protected void renderOverlayPass() {
    }

    protected void renderContentPass() {
        gui.pose().pushPose();
        gui.pose().translate(0.0F, 0.0F, 400.0F);

        // Text render stage
        int currentY = pos.y;
        for (int i = 0; i < components.size(); i++) {
            ClientTooltipComponent comp = components.get(i);
            comp.renderText(font, pos.x, currentY, gui.pose().last().pose(), gui.bufferSource());
            currentY += comp.getHeight() + (i == 0 ? 2 : 0);
        }

        // Image/Icon render stage
        currentY = pos.y;
        for (int i = 0; i < components.size(); i++) {
            ClientTooltipComponent comp = components.get(i);
            comp.renderImage(font, pos.x, currentY, gui);
            currentY += comp.getHeight() + (i == 0 ? 2 : 0);
        }

        gui.pose().popPose();
    }

    /**
     * Called when a Tooltip is "destroyed", once, either by hovering off or swapping items
     */
    protected void onClose() {
    }

}
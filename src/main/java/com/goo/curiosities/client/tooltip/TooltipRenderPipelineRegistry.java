package com.goo.curiosities.client.tooltip;

import com.goo.curiosities.client.CuriositiesClientConfig;
import com.goo.curiosities.client.tooltip.render_pipelines.*;
import com.goo.curiosities.common.registry.CuriositiesTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TooltipRenderPipelineRegistry {
    private record PipelineEntry(Predicate<ItemStack> predicate, Supplier<TooltipRenderPipeline> factory) {
    }

    private static final List<PipelineEntry> REGISTRY = new ArrayList<>();

    private static ItemStack lastHoveredStack = ItemStack.EMPTY;
    private static TooltipRenderPipeline cachedPipeline = null;

    /**
     * Registers a new custom criteria for pipeline generation.
     * Higher placement priority goes to elements added first.
     */
    public static void register(Predicate<ItemStack> predicate, Supplier<TooltipRenderPipeline> factory) {
        REGISTRY.add(new PipelineEntry(predicate, factory));
    }

    static {
        register(stack -> stack.is(CuriositiesTags.Items.SNOW_TOOLTIP) , SnowPipeline::new);
        register(stack -> stack.is(CuriositiesTags.Items.MATRIX_TOOLTIP) , MatrixPipeline::new);
        register(stack -> stack.is(CuriositiesTags.Items.WATER_TOOLTIP) , WaterPipeline::new);
        register(stack -> stack.is(CuriositiesTags.Items.FIRE_TOOLTIP) , FirePipeline::new);
        register(stack -> stack.is(CuriositiesTags.Items.RAINBOW_TOOLTIP) , RainbowPipeline::new);
    }

    public static boolean isSlotHovered() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof AbstractContainerScreen<?> container) {
            return container.hoveredSlot != null && container.hoveredSlot.hasItem();
        }
        return false;
    }

    public static void resetPipeline() {
        if (cachedPipeline != null) {
            cachedPipeline.onClose();
            cachedPipeline = null;
        }
        lastHoveredStack = ItemStack.EMPTY;
    }

    @Nullable
    public static TooltipRenderPipeline getRenderPipeline(ItemStack stack) {
        if (CuriositiesClientConfig.CONFIG.RENDER_CUSTOM_TOOLTIPS.isFalse()) return null;

        // Check if different item
        if (!ItemStack.isSameItemSameComponents(stack, lastHoveredStack)) {
            if (cachedPipeline != null) {
                cachedPipeline.onClose();
            }

            lastHoveredStack = stack.copy();
            cachedPipeline = null;
            for (PipelineEntry entry : REGISTRY) {
                if (entry.predicate.test(stack)) {
                    cachedPipeline = entry.factory.get();
                    break;
                }
            }
        }

        return cachedPipeline;
    }
}
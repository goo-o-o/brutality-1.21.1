package com.goo.brutality.client.tooltip;

import com.goo.brutality.client.BrutalityClientConfig;
import com.goo.brutality.client.tooltip.render_pipelines.*;
import com.goo.brutality.common.registry.BrutalityRarities;
import com.goo.brutality.common.registry.BrutalityTags;
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
        register(stack -> stack.is(BrutalityTags.Items.RAGE_ITEMS), RagePipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.ENCRYPTED.getValue(), EncryptedPipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.CORALINE.getValue(), CoralinePipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.SMOLDERING.getValue(), SmolderingPipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.CONDUCTIVE.getValue(), ConductivePipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.STYGIAN.getValue(), StygianPipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.VOIDTOUCHED.getValue(), VoidTouchedPipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.COSMIC.getValue(), CosmicPipeline::new);
        register(stack -> stack.getRarity() == BrutalityRarities.GODLY.getValue(), GodlyPipeline::new);
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
        if (BrutalityClientConfig.CONFIG.RENDER_CUSTOM_TOOLTIPS.isFalse()) return null;

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
package com.goo.brutality.client.render.gui;

import com.goo.brutality.client.registry.BrutalityRenderTypes;
import com.goo.brutality.common.item.curio.ring.DivineImmortalsRing;
import com.goo.brutality.common.networking.serverbound.SetItemStackInCurioSlotPayload;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.common.registry.BrutalitySounds;
import com.goo.brutality.util.Colors;
import com.goo.brutality.util.CurioUtil;
import com.goo.goo_lib.common.network.serverbound.SetItemStackInSlotPayload;
import com.goo.goo_lib.util.Easing;
import com.goo.goo_lib.util.RenderUtil;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

import static com.goo.brutality.common.menu.DivineImmortalsRingMenu.SLOTS;

public class DivineImmortalsRingOverlay implements LayeredDraw.Layer {
    public static boolean isOpen = false;
    private static boolean wasOpen = false;
    private static float currentProgress = 0.0f;
    private static float prevProgress = 0.0f;
    private static final float ANIM_SPEED = 0.15f;

    public static boolean shouldExtract = true;

    public static float targetScroll = 0.0f;
    public static float currentScroll = 0.0f;
    private static float prevScroll = 0.0f;

    public record RingSlot(int realSlotIndex, ItemStack stack) {
    }

    public static void tick() {
        prevProgress = currentProgress;
        prevScroll = currentScroll;

        if (isOpen && !wasOpen) {
            onOpen();
        } else if (wasOpen && !isOpen) {
            onClose();
        }
        wasOpen = isOpen;

        if (isOpen) {
            currentProgress = Math.min(1.0f, currentProgress + ANIM_SPEED);
        } else {
            currentProgress = Math.max(0.0f, currentProgress - ANIM_SPEED);
        }

        currentScroll = Mth.lerp(0.3f, currentScroll, targetScroll);
    }

    private static void onOpen() {
        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(BrutalitySounds.PORTAL_OPEN.get(), 1.0F)
        );
        shouldExtract = true;
    }

    private static void onClose() {
        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(BrutalitySounds.PORTAL_CLOSE.get(), 1.0F)
        );
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        List<RingSlot> activeSlots = getActiveSlots(player);
        if (activeSlots.isEmpty()) return;

        if (shouldExtract) {
            int selectedListIndex = getSelectedListIndex(activeSlots.size());
            if (selectedListIndex < 0) return;

            RingSlot selectedSlot = activeSlots.get(selectedListIndex);

            CuriosApi.getCuriosInventory(player).flatMap(handler ->
                    handler.findFirstCurio(BrutalityItems.Curio.DIVINE_IMMORTALS_RING.value())
            ).ifPresent(result -> {
                ItemStack mainHandStack = player.getMainHandItem().copy();
                ItemStack selectedStack = selectedSlot.stack().copy();

                if (selectedStack.isEmpty() && mainHandStack.isEmpty()) return;

                int mainHandSlot = player.getInventory().selected;
                ItemStack newRingStack = result.stack().copy();
                NonNullList<ItemStack> storedItems = DivineImmortalsRing.loadItems(newRingStack);

                int realContainerIndex = selectedSlot.realSlotIndex();
                if (realContainerIndex < 0 || realContainerIndex >= storedItems.size()) return;

                // swap main hand stack with selected ring slot
                storedItems.set(realContainerIndex, mainHandStack);
                DivineImmortalsRing.saveItems(newRingStack, storedItems);

                PacketDistributor.sendToServer(new SetItemStackInSlotPayload(mainHandSlot, selectedStack));
                PacketDistributor.sendToServer(new SetItemStackInCurioSlotPayload(
                        result.slotContext().identifier(),
                        result.slotContext().index(),
                        newRingStack
                ));
            });
        }
        shouldExtract = true;
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if (player == null || !CurioUtil.isWearingCurio(player, BrutalityItems.Curio.DIVINE_IMMORTALS_RING.value()))
            return;

        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
        float progress = Mth.lerp(partialTick, prevProgress, currentProgress);

        if (progress <= 0.001f) return;

        float quadWidth = 200F;
        float quadHeight = 200F;
        float halfWidth = quadWidth * 0.5F;
        float halfHeight = quadHeight * 0.5F;

        float smoothedProgress = Easing.EASE_OUT_CUBIC.ease(progress);
        float xOffset = Mth.lerp(smoothedProgress, -halfWidth, 0.0F);

        float xCenter = 0.0F;
        float yCenter = guiGraphics.guiHeight() / 2.0F;
        float radius = halfHeight * 0.6F;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(xOffset, 0, 0);

        RenderUtil.fillWithUv(
                BrutalityRenderTypes.getNebulaRenderType(RenderStateShard.LEQUAL_DEPTH_TEST),
                guiGraphics,
                -halfWidth, yCenter - halfHeight, halfWidth, yCenter + halfHeight,
                400,
                Colors.DIVINE_IMMORTALS_RING_RED
        );

        // pull slots directly from the ring stack
        List<RingSlot> activeSlots = getActiveSlots(player);
        if (activeSlots.isEmpty()) {
            guiGraphics.pose().popPose();
            return;
        }

        int totalItems = activeSlots.size();
        float animatedScroll = Mth.lerp(partialTick, prevScroll, currentScroll);

        float angleStep = Mth.TWO_PI / SLOTS;
        int halfSlots = SLOTS / 2;

        for (int slotOffset = -halfSlots; slotOffset <= halfSlots; slotOffset++) {
            float virtualItemPosition = animatedScroll + slotOffset;

            // map virtual offset relative to scroll position
            int itemIndex = Math.floorMod(Math.round(virtualItemPosition), totalItems);
            ItemStack item = activeSlots.get(itemIndex).stack();

            // sub-frame continuous angle offset
            float angularOffset = (slotOffset - (animatedScroll - Math.round(animatedScroll))) * angleStep;

            float itemX = xCenter + radius * Mth.cos(angularOffset);
            float itemY = yCenter + radius * Mth.sin(angularOffset);

            float distFromCenter = Math.abs(angularOffset / angleStep);
            float scale = 2.0F - (distFromCenter * 0.5F);
            float brightness = 1.0F - (distFromCenter * 0.25F);

            guiGraphics.pose().pushPose();


            guiGraphics.pose().translate(itemX, itemY, 401);
            guiGraphics.pose().scale(scale, scale, scale);

            float rotationFactor = Mth.clamp(1.0F - (distFromCenter / 0.5F), 0.0F, 1.0F);
            float smoothFactor = Easing.EASE_OUT_CUBIC.ease(rotationFactor);

            if (smoothFactor > 0.001F) {
                float angle = 10.0F * Mth.cos(Util.getMillis() * 0.0075F) * smoothFactor;
                guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(angle));
            }

            guiGraphics.setColor(brightness, brightness, brightness, 1.0F);
            guiGraphics.renderItem(item, -8, -8);
            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, item, -8, -8);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            guiGraphics.pose().popPose();
        }

        guiGraphics.pose().popPose();
    }

    public static int getSelectedListIndex(int totalActive) {
        if (totalActive <= 0) return -1;
        return Math.floorMod(Math.round(targetScroll), totalActive);
    }


    /**
     * Reads directly from the equipped ring itemstack and returns non-empty slots with their original index.
     */
    public static List<RingSlot> getActiveSlots(Player player) {
        List<RingSlot> slots = new ArrayList<>();
        CuriosApi.getCuriosInventory(player).flatMap(handler ->
                handler.findFirstCurio(BrutalityItems.Curio.DIVINE_IMMORTALS_RING.value())
        ).ifPresent(result -> {
            List<ItemStack> items = DivineImmortalsRing.loadItems(result.stack());
            for (int i = 0; i < items.size(); i++) {
                ItemStack stack = items.get(i);
                if (!stack.isEmpty()) {
                    slots.add(new RingSlot(i, stack));
                }
            }
        });
        return slots;
    }

    public static void onScroll(InputEvent.MouseScrollingEvent event) {
        if (DivineImmortalsRingOverlay.isOpen) {
            double scrollDelta = event.getScrollDeltaY();
            if (scrollDelta != 0) {
                int scrollDir = (int) -Math.signum(scrollDelta);

                // Add scroll direction directly to targetScroll
                DivineImmortalsRingOverlay.targetScroll += scrollDir;
                DivineImmortalsRingOverlay.shouldExtract = true;
                event.setCanceled(true);
            }
        }
    }
}
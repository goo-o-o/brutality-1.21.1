package com.goo.brutality.common.menu;

import com.goo.brutality.common.item.curio.ring.DivineImmortalsRing;
import com.goo.brutality.common.registry.BrutalityMenus;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class DivineImmortalsRingMenu extends AbstractContainerMenu {
    public static final int SLOTS = 9;
    private final ItemStack ringStack;
    private final IItemHandler ringHandler;

    // client-side constructor
    public DivineImmortalsRingMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new ItemStackHandler(SLOTS), ItemStack.EMPTY);
    }

    // server-side constructor
    public DivineImmortalsRingMenu(int containerId, Inventory playerInventory, IItemHandler ringHandler, ItemStack ringStack) {
        super(BrutalityMenus.DIVINE_IMMORTALS_RING_MENU.get(), containerId);
        this.ringStack = ringStack;
        this.ringHandler = ringHandler;

// 1. 3x3 ring slots centered around x = 88, y = 50
        int startX = 88 - 18 - 8;
        int startY = 50 - 18 - 8;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int index = col + row * 3;
                int x = startX + col * 18;
                int y = startY + row * 18;

                this.addSlot(new SlotItemHandler(ringHandler, index, x, y) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        // prevent nested ring storage
                        return !(stack.getItem() instanceof DivineImmortalsRing);
                    }
                });
            }
        }
        // 2. player main inventory (y-offset recalculated for imageHeight = 198)
        int playerInvY = 117; // standard offset for 198px tall screen
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, playerInvY + row * 18));
            }
        }

        // 3. player hotbar
        int hotbarY = playerInvY + 58; // 174px from top
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, hotbarY));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isRemoved() && player.getInventory().contains(ringStack);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index < SLOTS) {
                // move from ring to player inventory
                if (!this.moveItemStackTo(stackInSlot, SLOTS, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // move from player inventory to ring
                if (!this.moveItemStackTo(stackInSlot, 0, SLOTS, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!player.level().isClientSide() && !ringStack.isEmpty()) {
            NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
            for (int i = 0; i < SLOTS; i++) {
                items.set(i, ringHandler.getStackInSlot(i));
            }
            DivineImmortalsRing.saveItems(ringStack, items);
        }
    }
}

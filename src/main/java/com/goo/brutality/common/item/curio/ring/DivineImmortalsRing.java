package com.goo.brutality.common.item.curio.ring;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.BrutalityCurioItem;
import com.goo.brutality.common.menu.DivineImmortalsRingMenu;
import com.goo.brutality.common.networking.serverbound.divine_immortals_ring.ToggleDivineImmortalsRingOverlayPayload;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.goo.brutality.util.Styles;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Supplier;

public class DivineImmortalsRing extends BrutalityCurioItem {
    public static Supplier<MutableComponent> SUBSPACE = () -> Component.translatable("item." + Brutality.MOD_ID + ".divine_immortals_ring.subspace").withStyle(Styles.Special.STYGIAN);

    public DivineImmortalsRing(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return !CurioUtil.isWearingCurio(slotContext.entity(), this);
    }

    public static void saveItems(ItemStack ring, NonNullList<ItemStack> items) {
        ring.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    }

    public static NonNullList<ItemStack> loadItems(ItemStack ring) {
        NonNullList<ItemStack> items = NonNullList.withSize(DivineImmortalsRingMenu.SLOTS, ItemStack.EMPTY);
        ItemContainerContents contents = ring.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(items);
        return items;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack ringStack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            openRingMenu(serverPlayer, ringStack);
        }

        return InteractionResultHolder.sidedSuccess(ringStack, level.isClientSide());
    }


    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY) {
            if (player instanceof ServerPlayer serverPlayer)
                if (!(player.containerMenu instanceof DivineImmortalsRingMenu))
                    openRingMenu(serverPlayer, stack);

            return true;
        }

        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }


    public static void openRingMenu(ServerPlayer player, ItemStack ringStack) {
        ItemStackHandler handler = new ItemStackHandler(DivineImmortalsRingMenu.SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                NonNullList<ItemStack> items = NonNullList.withSize(DivineImmortalsRingMenu.SLOTS, ItemStack.EMPTY);
                for (int i = 0; i < DivineImmortalsRingMenu.SLOTS; i++) {
                    items.set(i, getStackInSlot(i));
                }
                saveItems(ringStack, items);
            }
        };

        NonNullList<ItemStack> loaded = loadItems(ringStack);
        for (int i = 0; i < loaded.size(); i++) {
            handler.setStackInSlot(i, loaded.get(i));
        }

        player.openMenu(
                new SimpleMenuProvider(
                        (id, inv, p) -> new DivineImmortalsRingMenu(id, inv, handler, ringStack),
                        Component.translatable("item." + Brutality.MOD_ID + ".divine_immortals_ring")
                )
        );
    }

    public static void handleItemToss(ItemTossEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();

        // check if server recorded the keybind active
        if (!ToggleDivineImmortalsRingOverlayPayload.ACTIVE_PLAYERS.contains(player.getUUID())) {
            return;
        }

        ItemStack tossedStack = event.getEntity().getItem();
        if (tossedStack.isEmpty() || tossedStack.getItem() instanceof DivineImmortalsRing) {
            return;
        }

        // attempt to insert into divine immortals ring curio slot
        CuriosApi.getCuriosInventory(player).flatMap(handler ->
                handler.findFirstCurio(BrutalityItems.Curio.DIVINE_IMMORTALS_RING.value())
        ).ifPresent(result -> {
            ItemStack ringStack = result.stack();
            NonNullList<ItemStack> items = DivineImmortalsRing.loadItems(ringStack);

            boolean inserted = false;
            // insert into first available empty or stackable slot
            for (int i = 0; i < items.size(); i++) {
                ItemStack inSlot = items.get(i);
                if (inSlot.isEmpty()) {
                    items.set(i, tossedStack.copy());
                    inserted = true;
                    break;
                } else if (ItemStack.isSameItemSameComponents(inSlot, tossedStack) && inSlot.getCount() < inSlot.getMaxStackSize()) {
                    int space = inSlot.getMaxStackSize() - inSlot.getCount();
                    int toAdd = Math.min(space, tossedStack.getCount());
                    inSlot.grow(toAdd);
                    tossedStack.shrink(toAdd);
                    if (tossedStack.isEmpty()) {
                        inserted = true;
                        break;
                    }
                }
            }

            if (inserted) {
                DivineImmortalsRing.saveItems(ringStack, items);
                // cancel original entity drop
                event.setCanceled(true);
            }
        });
    }

}

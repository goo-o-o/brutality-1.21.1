package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.menu.DivineImmortalsRingMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BrutalityMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Brutality.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<DivineImmortalsRingMenu>> DIVINE_IMMORTALS_RING_MENU =
            MENUS.register("divine_immortals_ring_menu", () -> IMenuTypeExtension.create((containerId, inv, buf) ->
                    new DivineImmortalsRingMenu(containerId, inv)
            ));
}

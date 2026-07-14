package com.goo.brutality.util;

import net.neoforged.fml.loading.FMLEnvironment;

import java.util.function.Supplier;

public class SideHelper {
    // double supplier for shielding
    public static <T> T shieldFromServer(Supplier<Supplier<T>> clientSupplier) {
        if (FMLEnvironment.dist.isClient()) {
            return clientSupplier.get().get();
        }
        return null;
    }
    public static <T> T shieldFromClient(Supplier<T> serverSupplier) {
        if (FMLEnvironment.dist.isDedicatedServer()) {
            return serverSupplier.get();
        }
        return null;
    }
}

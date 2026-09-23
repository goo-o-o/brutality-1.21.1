package com.goo.curiosities.common;


import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientProxy {
    public static float getPartialTick() {
        return Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
    }

    /**
     * Safely get entity from the client side
     */
    @Nullable
    public static Entity getEntity(UUID uuid) {
        if (Minecraft.getInstance().level != null) {
            for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
                if (entity.getUUID().equals(uuid)) return entity;
            }
        }
        return null;
    }


}
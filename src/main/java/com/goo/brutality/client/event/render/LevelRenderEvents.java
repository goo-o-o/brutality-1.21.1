package com.goo.brutality.client.event.render;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class LevelRenderEvents {
    @SubscribeEvent
    public static void onFogRender(ViewportEvent.RenderFog event) {
        if (event.getType() == FogType.LAVA) {
            Entity entity = event.getCamera().getEntity();
            if (entity instanceof LivingEntity living) {
                float farPlaneDistance = event.getFarPlaneDistance();
                if (CurioUtil.isWearingCurio(living, BrutalityItems.Curio.LAVA_LENSES.value()))
                    farPlaneDistance = 50;
                if (CurioUtil.isWearingCurio(living, BrutalityItems.Curio.SURTRS_HORN.value()))
                    farPlaneDistance = 150;

                event.setNearPlaneDistance(-8.0f);
                event.setFarPlaneDistance(farPlaneDistance);
                event.setCanceled(true);
            }
        }
    }
}

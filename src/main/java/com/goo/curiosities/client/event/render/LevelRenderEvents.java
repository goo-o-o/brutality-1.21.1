package com.goo.curiosities.client.event.render;

import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.client.render.GenericFieldRenderer;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.entity.GlitchField;
import com.goo.curiosities.common.entity.TimeStopField;
import com.goo.curiosities.common.item.curio.head.MiningGoggles;
import com.goo.curiosities.common.registry.CuriositiesItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class LevelRenderEvents {
    @SubscribeEvent
    public static void onFogRender(ViewportEvent.RenderFog event) {
        if (event.getType() == FogType.LAVA) {
            Entity entity = event.getCamera().getEntity();
            if (entity instanceof LivingEntity living) {
                event.setNearPlaneDistance(-8.0f);
                CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
                    if (handler.isEquipped(CuriositiesItems.LAVA_LENSES.value())) {
                        event.setFarPlaneDistance(50);
                    } else if (handler.isEquipped(CuriositiesItems.SURTRS_HORN.value())) {
                        event.setFarPlaneDistance(150);
                    }
                });
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        MiningGoggles.render(event);
        GenericFieldRenderer.renderField(
                event,
                TimeStopField.class,
                CuriositiesRenderTypes.TIME_STOP_SPHERE_SHADER_LOCATION,
                CuriositiesRenderTypes.SPHERE_MASK_BUFFER,
                CuriositiesRenderTypes.getTimeStopRenderType()
        );

        // 2. render GlitchField using fallback PositionColor shader (null renderType)
        GenericFieldRenderer.renderField(
                event,
                GlitchField.class,
                CuriositiesRenderTypes.GLITCH_SPHERE_SHADER_LOCATION,
                CuriositiesRenderTypes.SPHERE_MASK_BUFFER,
                CuriositiesRenderTypes.getGlitchRenderType()
        );
    }
}

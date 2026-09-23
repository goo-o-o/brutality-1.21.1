package com.goo.curiosities.client.registry;

import com.goo.curiosities.client.render.layer.geckolib.CuriosityGeoEntityRenderer;
import com.goo.curiosities.client.render.renderers.GlobuleRenderer;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesEntities;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.function.Function;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class CuriositiesEntityRenderers {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CuriositiesEntities.TIME_STOP_FIELD.value(), NoopRenderer::new);
        event.registerEntityRenderer(CuriositiesEntities.GLITCH_FIELD.value(), NoopRenderer::new);
        event.registerEntityRenderer(CuriositiesEntities.HONEY_GLOBULE.value(), GlobuleRenderer::new);
    }

    /**
     * Helper to cleanly register a GeoEntityRenderer alongside functional layers.
     */
    @SafeVarargs
    private static <T extends Entity & GeoAnimatable> void registerWithLayers(
            EntityRenderersEvent.RegisterRenderers event,
            EntityType<T> entityType,
            Function<GeoRenderer<T>, GeoRenderLayer<T>>... layerFactories) {

        event.registerEntityRenderer(entityType, context -> {
            String path = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath();
            GeoEntityRenderer<T> renderer = new CuriosityGeoEntityRenderer<>(context, new DefaultedEntityGeoModel<>(Curiosities.loc(path)));

            for (var factory : layerFactories) {
                renderer.addRenderLayer(factory.apply(renderer));
            }

            return renderer;
        });
    }
}
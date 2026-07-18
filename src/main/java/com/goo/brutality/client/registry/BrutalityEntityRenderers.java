package com.goo.brutality.client.registry;

import com.goo.brutality.client.render.layer.geckolib.AutoFullbrightGeoLayer;
import com.goo.brutality.client.render.layer.geckolib.BrutalityGeoEntityRenderer;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityEntities;
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

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class BrutalityEntityRenderers {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        registerWithLayers(event, BrutalityEntities.MIST_WRAITH.value(), AutoFullbrightGeoLayer::new);
        event.registerEntityRenderer(BrutalityEntities.CYLINDER_COLLIDER.value(), NoopRenderer::new);
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
            GeoEntityRenderer<T> renderer = new BrutalityGeoEntityRenderer<>(context, new DefaultedEntityGeoModel<>(Brutality.loc(path)));

            for (var factory : layerFactories) {
                renderer.addRenderLayer(factory.apply(renderer));
            }

            return renderer;
        });
    }
}
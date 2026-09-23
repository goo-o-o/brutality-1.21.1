package com.goo.curiosities.client.render.layer.geckolib;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CuriosityGeoItemRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {

    public <I extends T> CuriosityGeoItemRenderer(I item) {
        super(new DefaultedItemGeoModel<>(BuiltInRegistries.ITEM.getKey(item)) {
            @Override
            protected String subtype() {
                return super.subtype() + "/weapon";
            }
        });
    }

    @Override
    public @Nullable RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}

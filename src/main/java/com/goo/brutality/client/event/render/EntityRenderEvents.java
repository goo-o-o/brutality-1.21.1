package com.goo.brutality.client.event.render;

import com.goo.brutality.client.render.layer.entity.MonocleOfBrutalityLayer;
import com.goo.brutality.client.render.layer.entity.PotionEffectLayer;
import com.goo.brutality.client.render.layer.entity.XRayGogglesLayer;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.curio.function.Pi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class EntityRenderEvents {
    @SubscribeEvent
    public static void onLivingRender(RenderLivingEvent.Post<?, ?> event) {
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            Pi.render(event.getEntity(), event.getPoseStack(), event.getPartialTick(), event.getMultiBufferSource());
        }
    }

    @SubscribeEvent
    public static void registerRenderLayers(EntityRenderersEvent.AddLayers event) {

        for (PlayerSkin.Model skinType : event.getSkins()) {
            PlayerRenderer playerRenderer = event.getSkin(skinType);
            if (playerRenderer != null) {
                addRenderLayers(playerRenderer);
            }
        }

        for (EntityType<?> entityType : event.getEntityTypes()) {
            if (event.getRenderer(entityType) instanceof LivingEntityRenderer<?, ?> livingRenderer) {
                addRenderLayers(livingRenderer);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends LivingEntity, M extends EntityModel<T>> void addRenderLayers(LivingEntityRenderer<?, ?> renderer) {
        LivingEntityRenderer<T, M> castedRenderer = (LivingEntityRenderer<T, M>) renderer;
        castedRenderer.addLayer(new MonocleOfBrutalityLayer<>(castedRenderer));
        castedRenderer.addLayer(new XRayGogglesLayer<>(castedRenderer));
        castedRenderer.addLayer(new PotionEffectLayer<>(castedRenderer));
    }
}

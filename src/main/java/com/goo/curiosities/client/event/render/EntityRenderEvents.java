package com.goo.curiosities.client.event.render;

import com.goo.curiosities.client.render.ClientGlitchState;
import com.goo.curiosities.client.render.layer.entity.MobEffectLayer;
import com.goo.curiosities.client.render.layer.entity.XRayGogglesLayer;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.mixin.TextureStateAccessor;
import com.goo.curiosities.util.CombatTracker;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.goo.goo_lib.mixin.CompositeRenderTypeAccessor;
import com.goo.goo_lib.mixin.CompositeStateAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.MapItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class EntityRenderEvents {
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayer player = mc.player;
        if (player == null) return;

        if (event.getItemStack().getItem() instanceof MapItem) return;

        boolean isCensored = player.hasEffect(CuriositiesEffects.CENSORED);

        if (!isCensored) return;

        MultiBufferSource originalBuffer = event.getMultiBufferSource();

        // bind pixelate framebuffer only if censored effect is active
        RenderTarget handTarget = PostEffectRegistry.getRenderTargetFor(
                GLRenderTypes.PIXELATE_SHADER_LOCATION, ShaderPipeline.PipelineStage.SCREEN);
        if (handTarget != null) {
            handTarget.clear(Minecraft.ON_OSX);
            handTarget.copyDepthFrom(mc.getMainRenderTarget());
            handTarget.bindWrite(false);
        }

        // wrap buffer to override texture if 404 is active, and/or convert to shader render type if censored
        MultiBufferSource customBuffer = requestedRenderType -> {
            ResourceLocation texture = InventoryMenu.BLOCK_ATLAS;

            if (requestedRenderType instanceof CompositeRenderTypeAccessor composite) {
                RenderStateShard.EmptyTextureStateShard texState = ((CompositeStateAccessor) (Object) composite.getState()).getTextureState();
                if (texState instanceof TextureStateAccessor accessor) {
                    texture = accessor.getTexture().orElse(InventoryMenu.BLOCK_ATLAS);
                }
            }


            return originalBuffer.getBuffer(
                    GLRenderTypes.getPixelateScreenRenderType(
                            texture,
                            RenderStateShard.LEQUAL_DEPTH_TEST,
                            RenderStateShard.CULL
                    )
            );

        };

        // render arm and item
        ItemInHandRenderer itemInHandRenderer = mc.getEntityRenderDispatcher().getItemInHandRenderer();
        itemInHandRenderer.renderArmWithItem(
                player,
                event.getPartialTick(),
                event.getInterpolatedPitch(),
                event.getHand(),
                event.getSwingProgress(),
                event.getItemStack(),
                event.getEquipProgress(),
                event.getPoseStack(),
                customBuffer,
                event.getPackedLight()
        );

        // flush and process shader blit only if censored
        if (originalBuffer instanceof MultiBufferSource.BufferSource bufferSource) {
            bufferSource.endBatch();
        }

        mc.getMainRenderTarget().bindWrite(false);

        PostEffectRegistry.renderEffectForNextTick(
                GLRenderTypes.PIXELATE_SHADER_LOCATION, ShaderPipeline.PipelineStage.SCREEN);

        PostEffectRegistry.processAndBlitWith(
                GLRenderTypes.PIXELATE_SHADER_LOCATION, ShaderPipeline.PipelineStage.SCREEN, ShaderPipeline.BlitMode.TRANSLUCENT);

        event.setCanceled(true);
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
        castedRenderer.addLayer(new XRayGogglesLayer<>(castedRenderer));
        castedRenderer.addLayer(new MobEffectLayer<>(castedRenderer));
    }

    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void preRender(RenderLivingEvent.Pre<T, M> event) {
        Minecraft minecraft = Minecraft.getInstance();
        CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(handler -> {
            if (handler.isEquipped(CuriositiesItems.CLOAK_OF_INVISIBILITY.value())) {
                if (!CombatTracker.isInCombat(event.getEntity(), 100)) {
                    event.setCanceled(true);
                }
            } else if (handler.isEquipped(CuriositiesItems.ANTI_CHEAT.value())) {
                if (!minecraft.options.getCameraType().isFirstPerson()) {
                    event.setCanceled(true);
                }
            }


        });

        ClientGlitchState.preRender(event);

    }
    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void postRender(RenderLivingEvent.Post<T, M> event) {
        ClientGlitchState.postRender(event);
    }
}

package com.goo.curiosities.client.event;

import com.goo.curiosities.client.datagen.CuriositiesGlobalLootModifierProvider;
import com.goo.curiosities.client.registry.CuriositiesKeymappings;
import com.goo.curiosities.client.registry.CuriositiesRenderTypes;
import com.goo.curiosities.client.render.render_pipelines.GlitchPipeline;
import com.goo.curiosities.client.render.render_pipelines.GlitchSpherePipeline;
import com.goo.curiosities.client.render.render_pipelines.TimeStopSpherePipeline;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.registry.CuriositiesCreativeModeTabs;
import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.text.effect.BloomEffect;
import com.goo.goo_lib.client.text.effect.ColorGradientEffect;
import com.goo.goo_lib.client.text.effect.SmoothWaveEffect;
import com.goo.goo_lib.client.text.effect.base.ConfiguredEffect;
import com.goo.goo_lib.common.registry.TextEffects;
import com.goo.goo_lib.util.StyleEffectUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class ClientSetupEvents {


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CuriositiesGlobalLootModifierProvider.bootstrap();
        PostEffectRegistry.registerPipeline(new TimeStopSpherePipeline());
        PostEffectRegistry.registerPipeline(new GlitchSpherePipeline());
        PostEffectRegistry.registerPipeline(new GlitchPipeline());
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(CuriositiesKeymappings.Mappings.ACTIVE_ABILITY.get());
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
            @Override
            protected @NotNull Void prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(@NotNull Void object, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
                CuriositiesRenderTypes.PIXELATE_CACHE.clear();

                CuriositiesGlobalLootModifierProvider.bootstrap();
                Style rainbowStyle = StyleEffectUtil.createStyleWithEffects(
                        Style.EMPTY.withBold(true),
                        List.of(
                                new ConfiguredEffect<>(
                                        TextEffects.COLOR_GRADIENT_TYPE.get(),
                                        new ColorGradientEffect(),
                                        ColorGradientEffect.Config.builder()
                                                .colors(Colors.RAINBOW_LIST)
                                                .spread(250)
                                                .waveSpeed(0.77F)
                                                .build()
                                ),
                                new ConfiguredEffect<>(
                                        TextEffects.SMOOTH_WAVE_TYPE.get(),
                                        new SmoothWaveEffect(),
                                        SmoothWaveEffect.Config.builder().speed(2F).amplitude(0.5F).frequency(0.25F).build()
                                ),
                                new ConfiguredEffect<>(
                                        TextEffects.BLOOM_TYPE.get(),
                                        new BloomEffect(),
                                        0.5F
                                )
                        )
                );

                CreativeModeTab tab = CuriositiesCreativeModeTabs.EQUIPMENT.get();
                MutableComponent title = Component.translatable("itemGroup." + Curiosities.MOD_ID + ".equipment");
                tab.displayName = title.withStyle(rainbowStyle);
            }
        });
    }
}

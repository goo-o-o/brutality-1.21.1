package com.goo.brutality.client.event;

import com.goo.brutality.client.registry.BrutalityKeyNames;
import com.goo.brutality.client.render.gui.screen.DivineImmortalsRingScreen;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.common.registry.BrutalityMenus;
import com.goo.brutality.util.Colors;
import com.goo.goo_lib.client.render.OutlineColorRegistry;
import com.goo.goo_lib.client.render.model.InflatedBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.FastColor;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(BrutalityKeyNames.Mappings.TRIGGER_RAGE.get());
        event.register(BrutalityKeyNames.Mappings.OPEN_DIVINE_IMMORTALS_RING.get());
        event.register(BrutalityKeyNames.Mappings.ACTIVE_ABILITY.get());
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(BrutalityMenus.DIVINE_IMMORTALS_RING_MENU.get(), DivineImmortalsRingScreen::new);
    }
    @SubscribeEvent
    public static void onModelBaking(ModelEvent.ModifyBakingResult event) {

        event.getModels().computeIfPresent(
                ModelResourceLocation.inventory(BuiltInRegistries.ITEM.getKey(BrutalityItems.Weapon.LightGreatsword.BLADE_OF_THE_RUINED_KING.value())),
                (location, originalModel) -> new InflatedBakedModel(originalModel,
                        new InflatedBakedModel.ContextScale(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, 1 / 32F),
                        new InflatedBakedModel.ContextScale(ItemDisplayContext.GUI, 1 / 16F),
                        new InflatedBakedModel.ContextScale(ItemDisplayContext.GROUND, 1 / 16F)
                )
        );

        event.getModels().computeIfPresent(
                ModelResourceLocation.inventory(BuiltInRegistries.ITEM.getKey(BrutalityItems.Curio.Rage.GRUDGE_TOTEM.value())),
                (location, originalModel) -> new InflatedBakedModel(originalModel,
                        new InflatedBakedModel.ContextScale(ItemDisplayContext.GUI, 1 / 16F)
                )
        );

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
                OutlineColorRegistry.register(BrutalityItems.Weapon.LightGreatsword.BLADE_OF_THE_RUINED_KING.value(),
                        FastColor.ARGB32.color(34, 247, 207));
                OutlineColorRegistry.register(BrutalityItems.Curio.Rage.GRUDGE_TOTEM.value(),
                        Colors.GRENADIER);

            }
        });
    }
}

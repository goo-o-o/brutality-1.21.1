package com.goo.curiosities.common.item;

import com.goo.curiosities.util.Colors;
import com.goo.goo_lib.client.text.effect.ColorGradientEffect;
import com.goo.goo_lib.client.text.effect.JitterEffect;
import com.goo.goo_lib.client.text.effect.base.ConfiguredEffect;
import com.goo.goo_lib.common.registry.TextEffects;
import com.goo.goo_lib.util.StyleEffectUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

/**
 * Just gives some nice formatting to the tooltip
 */
public abstract class TemperatureDependentFishingLuckModifyingCurio extends FishingLuckModifyingCurioItem {

    public TemperatureDependentFishingLuckModifyingCurio(Properties properties) {
        super(properties);
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        Level level = player.level();
        BlockPos pos = player.blockPosition();

        Biome biome = level.getBiome(pos).value();
        float temp = biome.getTemperature(pos);
        boolean isSnowing = level.isRaining() && biome.getPrecipitationAt(pos) == Biome.Precipitation.SNOW;

        Style dynamicStyle;


        if (biome.coldEnoughToSnow(pos) || isSnowing) {
            // from 0.15 temp
            float coldFactor = ((0.15F - temp) / 0.85F) + (isSnowing ? 0.3F : 0.0F);
            float jitterIntensity = Mth.lerp(coldFactor, 1.5F, 4.5F);
            float jitterSpeed = Mth.lerp(coldFactor, 1.5F, 4.0F);

            ConfiguredEffect<ColorGradientEffect.Config> ICE_GRADIENT = new ConfiguredEffect<>(
                    TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                    ColorGradientEffect.Config.builder()
                            .colors(Colors.ICE_LIST) // lerp the colors from darker to brighter
                            .spread(180).waveSpeed(0.77F).build());


            ConfiguredEffect<JitterEffect.Config> JITTER = new ConfiguredEffect<>(
                    TextEffects.JITTER_TYPE.get(), new JitterEffect(),
                    JitterEffect.Config.builder()
                            .speed(jitterSpeed)
                            .intensity(jitterIntensity)
                            .build());


            dynamicStyle = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(ICE_GRADIENT, JITTER));

        } else {

            ConfiguredEffect<ColorGradientEffect.Config> FIRE_GRADIENT = new ConfiguredEffect<>(
                    TextEffects.COLOR_GRADIENT_TYPE.get(), new ColorGradientEffect(),
                    ColorGradientEffect.Config.builder()
                            .colors(Colors.FIRE_LIST) // lerp the colors from darker to brighter
                            .spread(100)
                            .waveSpeed(1)
                            .build()
            );

            dynamicStyle = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(FIRE_GRADIENT));
        }

        // temperature text
        MutableComponent tempValue = Component.literal(String.format("%.2f", temp)).setStyle(dynamicStyle);
        tooltipComponents.add(Component.translatable("tooltip.curiosities.current_temperature", tempValue));

    }



}
package com.goo.curiosities.util;


import com.goo.curiosities.common.Curiosities;
import com.goo.goo_lib.client.text.effect.ShakeEffect;
import com.goo.goo_lib.client.text.effect.base.ConfiguredEffect;
import com.goo.goo_lib.common.registry.TextEffects;
import com.goo.goo_lib.util.StyleEffectUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForgeConfig;
import org.joml.Matrix4f;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.function.Supplier;

import static net.neoforged.neoforge.common.extensions.IAttributeExtension.FORMAT;

public class TooltipUtil {

    public static final MutableComponent WEARER = Component.translatable("tooltip." + Curiosities.MOD_ID + ".wearer").withColor(Colors.MATRIX[0]);
    public static final MutableComponent ENTITIES = Component.translatable("tooltip." + Curiosities.MOD_ID + ".entities").withColor(Colors.MATRIX[0]);
    public static final MutableComponent FOES = Component.translatable("tooltip." + Curiosities.MOD_ID + ".foes").withColor(Colors.NETHER[0]);
    public static final MutableComponent FOE = Component.translatable("tooltip." + Curiosities.MOD_ID + ".foe").withColor(Colors.NETHER[0]);
    public static final MutableComponent ATTACKER = Component.translatable("tooltip." + Curiosities.MOD_ID + ".attacker").withColor(Colors.NETHER[0]);
    public static final MutableComponent IGNITE = Component.translatable("tooltip." + Curiosities.MOD_ID + ".ignite").withStyle(Styles.FIRE);
    public static final MutableComponent VICTIM = Component.translatable("tooltip." + Curiosities.MOD_ID + ".victim").withColor(Colors.NETHER[0]);
    public static final MutableComponent ON_HIT = Component.translatable("tooltip." + Curiosities.MOD_ID + ".on_hit").withColor(Colors.FIRE[0]);
    public static final MutableComponent CURRENT_HEALTH = Component.translatable("tooltip." + Curiosities.MOD_ID + ".current_health").withStyle(ChatFormatting.RED);
    public static final MutableComponent MISSING_HEALTH = Component.translatable("tooltip." + Curiosities.MOD_ID + ".missing_health").withStyle(ChatFormatting.RED);
    public static final MutableComponent HEALTH = Component.translatable("tooltip." + Curiosities.MOD_ID + ".health").withStyle(Styles.RAINBOW);
    public static final MutableComponent BONUS = Component.translatable("tooltip." + Curiosities.MOD_ID + ".bonus").withStyle(Styles.GOLD.withBold(true));
    public static final MutableComponent FATAL = Component.translatable("tooltip." + Curiosities.MOD_ID + ".fatal").withStyle(Styles.REGENERATION);
    public static final MutableComponent DAMAGE = Component.translatable("tooltip." + Curiosities.MOD_ID + ".damage").withColor(Colors.FIRE[1]);
    public static final MutableComponent HEAL = Component.translatable("tooltip." + Curiosities.MOD_ID + ".heal").withColor(Colors.MATRIX[2]);
    public static final MutableComponent DEBUFF = Component.translatable("tooltip." + Curiosities.MOD_ID + ".debuff").withStyle(Styles.SLOWNESS);
    public static final MutableComponent BUFF = Component.translatable("tooltip." + Curiosities.MOD_ID + ".buff").withColor(Colors.MATRIX[0]);

    public static Component item(Holder<Item> item) {
        return item.value().getDescription();
    }
    public static Component item(Item item) {
        return item.getDescription();
    }

    public static MutableComponent itemWithStyle(Holder<Item> item, Style style) {
        return item(item).copy().withStyle(style);
    }

    public static MutableComponent entityWithStyle(EntityType<?> entityType, Style style) {
        return entityType.getDescription().copy().withStyle(style);
    }

    public static MutableComponent blockWithStyle(Block block, Style style) {
        return block.getName().withStyle(style);
    }

    public static MutableComponent effectWithStyle(Holder<MobEffect> effect, Style style) {
        return effect.value().getDisplayName().copy().withStyle(style);
    }
    public static MutableComponent effect(Holder<MobEffect> effect) {
        return effect.value().getDisplayName().copy().withStyle(Style.EMPTY.withColor(effect.value().getColor()));
    }

    public static MutableComponent effectWithStyle(Holder<MobEffect> effect, int amplifier, Style style) {
        return effect.value().getDisplayName().copy().append(" " + convertToRoman(amplifier + 1).toUpperCase(Locale.ROOT)).withStyle(style);
    }

    public static MutableComponent attribute(Holder<Attribute> attribute) {
        return Component.translatable(attribute.value().getDescriptionId()).withStyle(attribute.value().getStyle(true));
    }

    public static MutableComponent attribute(Holder<Attribute> attribute, double value, AttributeModifier.Operation operation) {
        Attribute attr = attribute.value();
        TooltipFlag flag = TooltipFlag.NORMAL;
        String key = value > 0 ? "neoforge.modifier.plus" : "neoforge.modifier.take";
        ChatFormatting color = attr.getStyle(value > 0);

        Component attrDesc = Component.translatable(attr.getDescriptionId());
        Component valueComp = attr.toValueComponent(operation, value, flag);
        MutableComponent comp = Component.translatable(key, valueComp, attrDesc).withStyle(color);

        return comp.append(getDebugInfo(value, operation, flag));
    }

    private static Component getDebugInfo(double value, AttributeModifier.Operation operation, TooltipFlag flag) {
        Component debugInfo = CommonComponents.EMPTY;

        if (flag.isAdvanced() && NeoForgeConfig.COMMON.attributeAdvancedTooltipDebugInfo.get()) {
            // Advanced Tooltips show the underlying operation and the "true" value. We offset MULTIPLY_TOTAL by 1 due to how the operation is calculated.
            double advValue = (operation == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL ? 1 : 0) + value;
            String valueStr = FORMAT.format(advValue);
            String txt = switch (operation) {
                case ADD_VALUE -> String.format(Locale.ROOT, advValue > 0 ? "[+%s]" : "[%s]", valueStr);
                case ADD_MULTIPLIED_BASE -> String.format(Locale.ROOT, advValue > 0 ? "[+%sx]" : "[%sx]", valueStr);
                case ADD_MULTIPLIED_TOTAL -> String.format(Locale.ROOT, "[x%s]", valueStr);
            };
            debugInfo = Component.literal(" ").append(Component.literal(txt).withStyle(ChatFormatting.GRAY));
        }
        return debugInfo;
    }

    public static MutableComponent percent(float percent) {


        // clamp percent between 0 and 100 to prevent color bleeding
        float clamped = Math.clamp(percent, 0, 100);
        int color;

        if (clamped < 66F) {
            // map 0 -> 66 to a 0.0 -> 1.0 range
            float delta = clamped / 66F;
            color = FastColor.ARGB32.lerp(delta, Colors.FIRE[2], Colors.FIRE[1]);
        } else {
            // map 66 -> 100 to a 0.0 -> 1.0 range
            float delta = (clamped - 66F) / (100F - 66F);
            color = FastColor.ARGB32.lerp(delta, Colors.FIRE[1], Colors.MATRIX[2]);
        }

        String value = percent % 1 == 0 ? String.valueOf((int) percent) : String.valueOf(percent);
        Style style;
        if (percent > 100) {
            Supplier<ConfiguredEffect<ShakeEffect.Config>> SHAKE_SHR = () -> new ConfiguredEffect<>(
                    TextEffects.SHAKE_TYPE.get(), new ShakeEffect(), ShakeEffect.Config.builder().speed(percent * 0.01F - 1).intensity(0.5F).build()
            );
            style = StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true).withColor(color), List.of(SHAKE_SHR.get()));

        } else {
            style = Style.EMPTY.withBold(true).withColor(color);
        }

        return Component.literal(value + "%").withStyle(style);
    }


    public static String convertToSingularWords(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart(); // java 9+
        long seconds = duration.toSecondsPart();
        int millis = duration.toMillisPart();

        StringJoiner joiner = new StringJoiner(" ");

        if (hours > 0) {
            joiner.add(hours + " hour");
        }
        if (minutes > 0) {
            joiner.add(minutes + " minute");
        }

        if (seconds > 0 || millis > 0) {
            if (millis > 0) {
                float totalSeconds = seconds + (millis / 1000.0F);
                joiner.add(String.format("%.2f second", totalSeconds).replaceAll("0+$", "").replaceAll("\\.$", ""));
            } else {
                joiner.add(seconds + " second");
            }
        }
        return joiner.length() > 0 ? joiner.toString() : "0 second";
    }

    public static String convertToRoman(int number) {
        if (number <= 0) return String.valueOf(number);

        StringBuilder fullRoman = new StringBuilder();
        int level = 0;

        // deconstruct the number from right to left (ones, then tens, then hundreds...)
        while (number > 0) {
            int digit = number % 10;

            // prepend the new characters to the front of the string
            fullRoman.insert(0, formatRomanDigit(level, digit));

            number /= 10;
            level++;
        }

        return fullRoman.toString();
    }

    private static final char[][] romanChars = new char[][]{{'i', 'v'}, {'x', 'l'}, {'c', 'd'}, {'m', '?'}};

    private static String formatRomanDigit(int level, int digit) {
        StringBuilder result = new StringBuilder();
        if (digit == 9) {
            result.append(romanChars[level][0]);
            result.append(romanChars[level + 1][0]);
            return result.toString();
        } else if (digit == 4) {
            result.append(romanChars[level][0]);
            result.append(romanChars[level][1]);
            return result.toString();
        } else {
            if (digit >= 5) {
                result.append(romanChars[level][1]);
                digit -= 5;
            }

            result.repeat(String.valueOf(romanChars[level][0]), Math.max(0, digit));

            return result.toString();
        }
    }

    /**
     * {@link GuiGraphics#blit(ResourceLocation, int, int, int, int, int, int, int, float, float, int, int)} but mirrorable
     */
    public static void blitMirrored(
            GuiGraphics graphics,
            TextureAtlasSprite sprite,
            int x1, int x2, int y1, int y2, int blitOffset,
            int uWidth, int vHeight, int uOffset, int vOffset,
            boolean mirrorHorizontally, boolean mirrorVertically
    ) {
        // 1. Convert pixel positions to 0.0-1.0 percentages based on your custom frame size
        float pctMinU = (float) uOffset / (float) uWidth;
        float pctMaxU = (float) (uOffset + uWidth) / (float) uWidth;

        float pctMinV = (float) vOffset / (float) vHeight;
        float pctMaxV = (float) (vOffset + vHeight) / (float) vHeight;

        // 2. Ask the sprite to resolve those 0.0-1.0 percentages into global atlas UVs
        float minU = sprite.getU(pctMinU);
        float maxU = sprite.getU(pctMaxU);
        float minV = sprite.getV(pctMinV);
        float maxV = sprite.getV(pctMaxV);

        // 3. MIRROR LOGIC: Swap the bounds if mirroring is true
        float finalMinU = mirrorHorizontally ? maxU : minU;
        float finalMaxU = mirrorHorizontally ? minU : maxU;
        float finalMinV = mirrorVertically ? maxV : minV;
        float finalMaxV = mirrorVertically ? minV : maxV;

        // 4. Bind the atlas and draw the vertices
        RenderSystem.setShaderTexture(0, sprite.atlasLocation());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        Matrix4f matrix4f = graphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y1, (float) blitOffset).setUv(finalMinU, finalMinV);
        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y2, (float) blitOffset).setUv(finalMinU, finalMaxV);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, (float) blitOffset).setUv(finalMaxU, finalMaxV);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y1, (float) blitOffset).setUv(finalMaxU, finalMinV);

        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

}
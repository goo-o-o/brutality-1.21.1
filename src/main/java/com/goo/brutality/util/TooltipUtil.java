package com.goo.brutality.util;


import com.goo.brutality.common.Brutality;
import com.goo.goo_lib.client.text.effect.ShakeEffect;
import com.goo.goo_lib.client.text.effect.base.ConfiguredEffect;
import com.goo.goo_lib.client.text.effect.config.ShakeConfig;
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


    private static final Supplier<ConfiguredEffect<ShakeConfig>> ATTACKER_SHAKE = () -> new ConfiguredEffect<>(
            TextEffects.SHAKE_TYPE.get(),
            new ShakeEffect(),
            new ShakeConfig(0.25F, 1F));
    public static final MutableComponent USER = Component.translatable("tooltip." + Brutality.MOD_ID + ".user").withColor(Colors.SPRING_BUD);
    public static final MutableComponent WEARER = Component.translatable("tooltip." + Brutality.MOD_ID + ".wearer").withColor(Colors.SPRING_BUD);
    public static final MutableComponent ENTITIES = Component.translatable("tooltip." + Brutality.MOD_ID + ".entities").withColor(Colors.SPRING_BUD);
    public static final MutableComponent ATTACKER = Component.translatable("tooltip." + Brutality.MOD_ID + ".attacker").withStyle(
            StyleEffectUtil.createStyleWithEffects(Style.EMPTY.withBold(true), List.of(ATTACKER_SHAKE.get(), Styles.RAGE_GRADIENT.get())));
    public static final MutableComponent VICTIM = Component.translatable("tooltip." + Brutality.MOD_ID + ".victim").withStyle(Styles.GRAY_STYLE);
    public static final MutableComponent CURRENT_HEALTH = Component.translatable("tooltip." + Brutality.MOD_ID + ".current_health").withStyle(ChatFormatting.RED);
    public static final MutableComponent BONUS = Component.translatable("tooltip." + Brutality.MOD_ID + ".bonus").withStyle(Styles.LEGENDARY_STYLE.withBold(true));
    public static final MutableComponent FATAL = Component.translatable("tooltip." + Brutality.MOD_ID + ".fatal").withStyle(Styles.RAGE_STYLE);
    public static final MutableComponent DAMAGE = Component.translatable("tooltip." + Brutality.MOD_ID + ".damage").withColor(Colors.ORANGE);
    public static final MutableComponent HEAL = Component.translatable("tooltip." + Brutality.MOD_ID + ".heal").withColor(Colors.ERIN);
    public static final MutableComponent RAGE = Component.translatable("tooltip." + Brutality.MOD_ID + ".rage").withStyle(Styles.RAGE_STYLE);
    public static final MutableComponent DEBUFF = Component.translatable("tooltip." + Brutality.MOD_ID + ".debuff").withStyle(Styles.VITALITY_STYLE);
    public static final MutableComponent BUFF = Component.translatable("tooltip." + Brutality.MOD_ID + ".buff").withStyle(Styles.TOXIC_STYLE);

    public static MutableComponent entityWithStyle(EntityType<?> entityType, Style style) {
        return entityType.getDescription().copy().withStyle(style);
    }
    public static MutableComponent blockWithStyle(Block block, Style style) {
        return block.getName().withStyle(style);
    }

    public static MutableComponent effectWithStyle(Holder<MobEffect> effect, Style style) {
        return effect.value().getDisplayName().copy().withStyle(style);
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

    public static MutableComponent percentage(float percent) {
        // clamp percent between 0 and 100 to prevent color bleeding
        float clamped = Math.clamp(percent, 0, 100);
        int color;

        if (clamped < 66F) {
            // map 0 -> 66 to a 0.0 -> 1.0 range
            float delta = clamped / 66F;
            color = FastColor.ARGB32.lerp(delta, Colors.GRENADIER, Colors.ORANGE);
        } else {
            // map 66 -> 100 to a 0.0 -> 1.0 range
            float delta = (clamped - 66F) / (100F - 66F);
            color = FastColor.ARGB32.lerp(delta, Colors.ORANGE, Colors.ERIN);
        }

        String value = percent % 1 == 0 ? String.valueOf((int) percent) : String.valueOf(percent);

        // apply the dynamically calculated color directly to this component
        return Component.literal(value + "%").withStyle(Style.EMPTY.withColor(color).withBold(true));
    }

    /**
     * Hacky way to determine how many Arguments are in a translation key
     *
     * @param format
     * @return
     */
    public static int countFormatSpecifiers(String format) {
        if (format == null || format.isEmpty()) return 0;

        int count = 0;
        int len = format.length();

        for (int i = 0; i < len - 1; i++) {
            if (format.charAt(i) == '%') {
                char next = format.charAt(i + 1);

                // ignore escaped percentages like "%%"
                if (next == '%') {
                    i++;
                    continue;
                }

                // check if the character following the '%' matches valid string format characters
                // s = string, d = integer, f = float, c = char, b = boolean
                if (next == 's' || next == 'd' || next == 'f' || next == 'c' || next == 'b') {
                    count++;
                }
            }
        }
        return count;
    }

    public static String convertToSingularWords(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        StringJoiner joiner = new StringJoiner(" ");

        if (hours > 0) joiner.add(hours + " hour");
        if (minutes > 0) joiner.add(minutes + " minute");
        if (seconds > 0) joiner.add(seconds + " second");

        // Handle case where duration is completely zero (PT0S)
        return joiner.length() > 0 ? joiner.toString() : "0 second";
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

    /**
     * Custom blit implementation that takes a standalone texture {@link ResourceLocation}
     * and supports horizontal and vertical mirroring.
     */
    public static void blitMirrored(
            GuiGraphics graphics,
            ResourceLocation textureLocation,
            int x1, int x2, int y1, int y2, int blitOffset,
            int uWidth, int vHeight, float uOffset, float vOffset,
            int textureWidth, int textureHeight,
            boolean mirrorHorizontally, boolean mirrorVertically
    ) {
        // 1. Convert pixel positions to 0.0-1.0 UV percentages based on total texture file dimensions
        float minU = uOffset / (float) textureWidth;
        float maxU = (uOffset + (float) uWidth) / (float) textureWidth;

        float minV = vOffset / (float) textureHeight;
        float maxV = (vOffset + (float) vHeight) / (float) textureHeight;

        // 2. MIRROR LOGIC: Swap the bounds if mirroring is true
        float finalMinU = mirrorHorizontally ? maxU : minU;
        float finalMaxU = mirrorHorizontally ? minU : maxU;
        float finalMinV = mirrorVertically ? maxV : minV;
        float finalMaxV = mirrorVertically ? minV : maxV;

        // 3. Bind the loose texture file and use the standard position-texture shader
        RenderSystem.setShaderTexture(0, textureLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        Matrix4f matrix4f = graphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        // 4. Map the layout vertices counter-clockwise
        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y1, (float) blitOffset).setUv(finalMinU, finalMinV);
        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y2, (float) blitOffset).setUv(finalMinU, finalMaxV);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, (float) blitOffset).setUv(finalMaxU, finalMaxV);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y1, (float) blitOffset).setUv(finalMaxU, finalMinV);

        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }


}
package com.goo.curiosities.client.event;

import com.goo.curiosities.client.tooltip.TooltipRenderPipeline;
import com.goo.curiosities.client.tooltip.TooltipRenderPipelineRegistry;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.curio.hand.SuspiciouslyLargeHandle;
import com.goo.curiosities.common.registry.CuriositiesDataComponents;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.common.tooltip.ItemDescriptions;
import com.goo.curiosities.util.AttributeUtil;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.client.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Curiosities.MOD_ID, value = Dist.CLIENT)
public class TooltipEvents {
    @SubscribeEvent
    public static void onGatherTooltips(ItemTooltipEvent event) {
        ItemDescriptions customDescriptions = event.getItemStack().get(CuriositiesDataComponents.ITEM_DESCRIPTIONS.get());

        if (customDescriptions != null && !customDescriptions.isEmpty()) {
            // an effective final counter to keep lines in the correct sequential order
            // 1 because name is first in tooltip
            var indexWrapper = new Object() {
                int value = 1;
            };

            // immediately adds each component to the main tooltip list at the correct index
            customDescriptions.addToTooltip(event.getContext(), comp -> event.getToolTip().add(indexWrapper.value++, comp), event.getFlags());
        }
    }

    @SubscribeEvent
    public static void onGatherSkippedTooltips(GatherSkippedAttributeTooltipsEvent event) {
        Player player = event.getContext().player();
        if (CurioUtil.isWearingCurio(player, CuriositiesItems.SUSPICIOUSLY_LARGE_HANDLE.value())) {
            event.skipId(Objects.requireNonNull(Attributes.ATTACK_SPEED.value().getBaseId()));
            event.skipId(Objects.requireNonNull(Attributes.ATTACK_DAMAGE.value().getBaseId()));
        }
    }

    @SubscribeEvent
    public static void onAddAttributeTooltips(AddAttributeTooltipsEvent event) {
        Player player = event.getContext().player();
        if (!CurioUtil.isWearingCurio(player, CuriositiesItems.SUSPICIOUSLY_LARGE_HANDLE.value())) return;

        // attack speed
        double speedEntityBase = player == null ? 4.0D : player.getAttributeBaseValue(Attributes.ATTACK_SPEED);
        double targetSpeed = SuspiciouslyLargeHandle.BASE_ATTACK_SPEED; // 0.65

        MutableComponent speedText = Attributes.ATTACK_SPEED.value().toBaseComponent(
                targetSpeed,
                speedEntityBase,
                false,
                event.getContext().flag()
        );
        event.addTooltipLines(Component.literal(" ").append(speedText).withStyle(ChatFormatting.DARK_GREEN));

        // attack damage
        double damageEntityBase = player == null ? 1.0D : player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

        // calculate total base damage (base weapon damage + bonus damage from lost speed)
        double bonusDamage = SuspiciouslyLargeHandle.getDamageModification(player);
        double baseWeaponDamage = AttributeUtil.getItemBaseAttackDamage(event.getStack());
        double totalBaseDamage = baseWeaponDamage + bonusDamage;

        MutableComponent damageText = Attributes.ATTACK_DAMAGE.value().toBaseComponent(
                totalBaseDamage,
                damageEntityBase,
                false,
                event.getContext().flag()
        );
        event.addTooltipLines(Component.literal(" ").append(damageText).withStyle(ChatFormatting.DARK_GREEN));
    }


    @SubscribeEvent
    public static void onRenderTooltipPre(RenderTooltipEvent.Pre event) {
        TooltipRenderPipeline pipeline = TooltipRenderPipelineRegistry.getRenderPipeline(event.getItemStack());
        if (pipeline != null) {
            event.setCanceled(true);
            pipeline.run(event.getGraphics(), event.getItemStack(), event.getFont(), event.getComponents(),
                    event.getX(), event.getY(), event.getScreenWidth(), event.getScreenHeight(), event.getTooltipPositioner());
        }
    }


}

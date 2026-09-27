package com.goo.curiosities.common.item;

import com.goo.goo_lib.common.registry.GLAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

/**
 * GooLib has {@link com.goo.goo_lib.common.registry.GLAttributes#FISHING_LUCK} attribute, but it does not allow for per bobber modification of the luck. This class allows that to happen.
 * <br>
 * NOTE: do not use this class for normal fishing luck attributes, just use the base class
 */
public abstract class FishingLuckModifyingCurioItem extends CuriositiesCurioItem {
    public FishingLuckModifyingCurioItem(Properties properties) {
        super(properties);
    }

    public abstract float getFishingLuckBonus(ItemStack stack, Player player, @Nullable FishingHook hook);

    /**
     * {@link FishingLuckModifyingCurioItem} already manages the Fishing Luck internally, but we want to display a matching attribute value here. The attribute isn't actually applied to the wearer, but for all intents and purposes, it achieves the same result
     */
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.entity() instanceof Player player && player.level().isClientSide()) {

            Multimap<Holder<Attribute>, AttributeModifier> map = super.getAttributeModifiers(slotContext, id, stack);
            map.put(GLAttributes.FISHING_LUCK, new AttributeModifier(id, getFishingLuckBonus(stack, player, player.fishing), AttributeModifier.Operation.ADD_VALUE));
            return map;
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }
}

package com.goo.curiosities.common.item.curio.hand;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class SuspiciouslyLargeHandle extends CuriositiesCurioItem {
    public static final float BASE_ATTACK_SPEED = 0.65F, RATIO = 7.5F;

    public SuspiciouslyLargeHandle(Properties properties) {
        super(properties);
    }


    public static float getDamageModification(Player player) {
        Optional<ICuriosItemHandler> handlerOptional = CuriosApi.getCuriosInventory(player);
        if (handlerOptional.isPresent()) {
            ICuriosItemHandler handler = handlerOptional.get();
            if (handler.isEquipped(CuriositiesItems.SUSPICIOUSLY_LARGE_HANDLE.value())) {
                float attackSpeed = (float) player.getAttributeValue(Attributes.ATTACK_SPEED);
                float difference = attackSpeed - BASE_ATTACK_SPEED;
                return difference * RATIO;
            }
        }
        return 0;
    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        if (attributeInstance.getAttribute().is(Attributes.ATTACK_DAMAGE)) {
            if (slotContext.entity() instanceof Player player)
                if (!player.getWeaponItem().isEmpty())
                    return getDamageModification(player);
        }

        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
            AttributeInstance currentAttackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
            if (currentAttackSpeed != null) {
                float speedToModify = (float) (BASE_ATTACK_SPEED - currentAttackSpeed.getValue());
                map.put(Attributes.ATTACK_SPEED, new AttributeModifier(id, speedToModify, AttributeModifier.Operation.ADD_VALUE));
                float damageToModify = speedToModify * -RATIO;
                map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, damageToModify, AttributeModifier.Operation.ADD_VALUE));
                return map;
            }
        }

        return super.getAttributeModifiers(slotContext, id, stack);
    }

}

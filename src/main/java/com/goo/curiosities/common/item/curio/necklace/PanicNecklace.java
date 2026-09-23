package com.goo.curiosities.common.item.curio.necklace;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

public class PanicNecklace extends CuriositiesCurioItem {
    public PanicNecklace(Properties properties) {
        super(properties);
    }

    public static void proc(LivingEntity wearer) {
        wearer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 4));
    }

    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        proc(wearer);
    }
}

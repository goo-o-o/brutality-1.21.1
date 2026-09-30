package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.QuiverCurioItem;
import com.goo.curiosities.util.EntityUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class QuiverOfTheFirstFlame extends QuiverCurioItem {
    public QuiverOfTheFirstFlame(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (source.is(DamageTypes.ARROW)) {
            if (source.getDirectEntity() instanceof AbstractArrow arrow) {
                HolderLookup.RegistryLookup<Enchantment> registry = victim.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                Holder.Reference<Enchantment> flameHolder = registry.getOrThrow(Enchantments.FLAME);

                ItemStack weapon = arrow.getWeaponItem();
                List<LivingEntity> nearby = EntityUtil.getNearbyEnemies(attacker, source.getSourcePosition(), 2);
                if (weapon.getEnchantmentLevel(flameHolder) > 0) {
                    victim.igniteForSeconds(60);
                    nearby.forEach(e -> e.igniteForSeconds(60));
                } else {
                    victim.igniteForSeconds(20);
                    nearby.forEach(e -> e.igniteForSeconds(20));
                }
            }
        }
    }
}

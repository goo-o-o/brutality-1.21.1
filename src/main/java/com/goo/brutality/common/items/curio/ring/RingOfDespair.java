package com.goo.brutality.common.items.curio.ring;

import com.goo.brutality.common.items.BrutalityCurioItem;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.util.EntityUtil;
import com.goo.goo_lib.util.MobEffectUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public class RingOfDespair extends BrutalityCurioItem {
    public RingOfDespair(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(@UnknownNullability Player player, ItemStack curio) {
        List<LivingEntity> nearbyEntities = player.level().getNearbyEntities(LivingEntity.class,
                TargetingConditions.DEFAULT.ignoreInvisibilityTesting().selector(i -> !EntityUtil.isAlly(player, i)), player, player.getBoundingBox().inflate(5));

        for (LivingEntity nearby : nearbyEntities) {
            MobEffectUtil.modifyEffect(nearby, player, BrutalityEffects.DESPAIR,
                    new MobEffectUtil.ModValue(160, true),
                    new MobEffectUtil.ModValue(5, false),
                    null,
                    e -> e.addEffect(new MobEffectInstance(BrutalityEffects.DESPAIR, 160, 4), player),
                    null);
        }

        player.playSound(SoundEvents.SOUL_ESCAPE.value());
        if (player.level() instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            serverLevel.sendParticles(serverPlayer, ParticleTypes.SQUID_INK, false,
                    player.getX(),
                    player.getY(0.5),
                    player.getZ(),
                    15,
                    0.5, 0.5, 0.5, 0.75
            );

        }
    }
}

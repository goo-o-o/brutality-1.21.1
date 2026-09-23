package com.goo.curiosities.common.item.curio.heart;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class FrozenHeart extends CuriositiesCurioItem {
    public FrozenHeart(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 30 == 0) {
            freezeNearby(slotContext.entity(), 20);
        }
    }

    public static void freezeNearby(LivingEntity livingEntity, int duration) {
        if (!(livingEntity.level() instanceof ServerLevel serverLevel)) return;

        List<LivingEntity> potentialTargets = serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(3),
                EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(e -> !e.is(livingEntity) && e.distanceTo(livingEntity) <= 3));

        for (LivingEntity target : potentialTargets) {
            MobEffectInstance frozenEffect = new MobEffectInstance(CuriositiesEffects.FROZEN, duration, 0, false, true, true);
            target.addEffect(frozenEffect, livingEntity);
            ClientboundUpdateMobEffectPacket addPacket = new ClientboundUpdateMobEffectPacket(
                    target.getId(),
                    frozenEffect,
                    true
            );

            serverLevel.getChunkSource().broadcast(target, addPacket);
        }
        if (!potentialTargets.isEmpty())
            serverLevel.playSound(null, livingEntity, CuriositiesSounds.ICE_FREEZE.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
    }
}
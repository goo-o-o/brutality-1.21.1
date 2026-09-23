package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import com.goo.curiosities.util.EntityUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EmergencyMeeting extends CuriositiesCurioItem {
    public EmergencyMeeting(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(player, curio.getItem(), 20 * 60, () -> {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.playNotifySound(
                        CuriositiesSounds.EMERGENCY_MEETING.get(),
                        SoundSource.MASTER,
                        0.75F, 1.0F
                );
            }

            player.level().getNearbyEntities(
                            LivingEntity.class,
                            TargetingConditions.DEFAULT.selector(e -> !EntityUtil.isAlly(e, player)),
                            player,
                            player.getBoundingBox().inflate(7.5F))
                    .forEach(e -> {
                        e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
                        if (e instanceof ServerPlayer serverPlayer) {
                            serverPlayer.playNotifySound(
                                    CuriositiesSounds.EMERGENCY_MEETING.get(),
                                    SoundSource.MASTER,
                                    0.75F, 1.0F
                            );
                        }
                    });
        });
    }
}

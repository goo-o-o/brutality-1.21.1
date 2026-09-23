package com.goo.curiosities.common.item.curio.anklet;

import com.goo.curiosities.common.item.AnkletCurioItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class EnderAnklet extends AnkletCurioItem {
    public EnderAnklet(Properties properties) {
        super(properties);
    }

    @Override
    protected void onWearerDodge(LivingEntity entity, DamageSource source, double roll, DamageContainer container, ItemStack stack) {
        Level level = entity.level();

        if (!level.isClientSide) {
            for (int i = 0; i < 16; i++) {
                double x = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                double y = Mth.clamp(
                        entity.getY() + (double) (entity.getRandom().nextInt(16) - 8),
                        level.getMinBuildHeight(),
                        level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1
                );
                double z = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }

                Vec3 vec3 = entity.position();
                // honestly don't call the event
//                EntityTeleportEvent.ChorusFruit event = EventHooks.onChorusFruitTeleport(entity, d0, d1, d2);
//                if (event.isCanceled()) return itemstack;
                if (entity.randomTeleport(x, y, z, true)) {
                    level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                    SoundSource soundsource;
                    SoundEvent soundevent;
                    if (entity instanceof Fox) {
                        soundevent = SoundEvents.FOX_TELEPORT;
                        soundsource = SoundSource.NEUTRAL;
                    } else {
                        soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        soundsource = SoundSource.PLAYERS;
                    }

                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent, soundsource);
                    entity.resetFallDistance();
                    break;
                }
            }

            if (entity instanceof Player player) {
                player.resetCurrentImpulseContext();
//                player.getCooldowns().addCooldown(this, 20);
            }
        }
    }
}

package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.ClientProxy;
import com.goo.curiosities.common.item.FootstepCurioItem;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.EntityUtil;
import com.goo.goo_lib.client.particle.WaveParticleOption;
import com.goo.goo_lib.common.network.clientbound.ScreenShakePayload;
import com.goo.goo_lib.util.Easing;
import com.goo.goo_lib.util.phys.ShockwaveUtils;
import com.goo.goo_lib.util.screenshake.ScreenShakeUtil;
import com.goo.goo_lib.util.screenshake.ShakeInstance;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class FlameStompers extends FlameThreaders {


    public FlameStompers(Properties properties) {
        super(properties);
        speedBonus = 0.55F;
    }

    @Override
    protected float getWaveSize(LivingEntity livingEntity, int amount) {
        return 4;
    }

    @Override
    public void onFootstep(LivingEntity livingEntity, Vec3 position, FootstepCurioItem curioItem, boolean left, boolean hasValidSurface, int amount, float partialTick) {
        super.onFootstep(livingEntity, position, curioItem, left, hasValidSurface, amount, partialTick);
    }

    @Override
    public void onWearerFall(LivingFallEvent event, ItemStack curio) {
        float distance = event.getDistance();
        if (distance < 4) return;
        LivingEntity livingEntity = event.getEntity();
        float partialTick = ClientProxy.getPartialTick();
        Vec3 position = livingEntity.getPosition(partialTick).add(0, 0.05, 0);
        WaveParticleOption particleOption = new WaveParticleOption(
                CuriositiesParticles.FIRE_WAVE.get(),
                distance,
                90.0F,
                -livingEntity.getPreciseBodyRotation(partialTick),
                0.0F,
                15,
                Easing.EASE_OUT_SINE
        );
        ShakeInstance shakeInstance = ShakeInstance.builder()
                .fadeInTicks(0)
                .fadeOutTicks(10)
                .fadeOutCurve(Easing.EASE_IN_SINE)
                .speed(distance)
                .maxX(distance)
                .maxY(distance)
                .maxRoll(distance)
                .maxPitch(distance)
                .maxYaw(distance)
                .durationTicks(10)
                .position(position, distance)
                .build();

        if (livingEntity.level().isClientSide()) {
            livingEntity.level().addParticle(
                    particleOption,
                    position.x(), position.y(), position.z(),
                    0.0D, 0.0D, 0.0D
            );

            ScreenShakeUtil.addShake(shakeInstance);
        } else {
            ServerLevel serverLevel = ((ServerLevel) livingEntity.level());

            serverLevel.playSound(null,
                    position.x(), position.y(), position.z(),
                    CuriositiesSounds.HEAVY_STOMP.get(), SoundSource.PLAYERS, 3.5F, 1F);

            PacketDistributor.sendToPlayersTrackingEntity(livingEntity, new ScreenShakePayload(shakeInstance));

            ShockwaveUtils.applyWaveEffect(
                    serverLevel,
                    position.x(),
                    position.y(),
                    position.z(),
                    LivingEntity.class,
                    particleOption,
                    e ->
                            !EntityUtil.isAlly(e, livingEntity),
                    e -> {
                        e.igniteForSeconds(5);
                        Vec3 awayVec = livingEntity.getPosition(0).vectorTo(e.getPosition(0));
                        awayVec = awayVec.normalize().add(0, distance * 0.05,0).scale(distance * 0.1);
                        e.push(awayVec);
                        if (e instanceof ServerPlayer serverPlayer) {
                            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                        }
                    });

        }
    }
}

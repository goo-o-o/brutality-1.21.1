package com.goo.brutality.common.item.curio.hand;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalitySounds;
import com.goo.goo_lib.common.network.clientbound.ScreenShakePayload;
import com.goo.goo_lib.util.screenshake.ShakeInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

public class OmegaGauntlet extends BrutalityRageCurioItem {
    public OmegaGauntlet(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 10 == 0) {
            if (slotContext.entity().level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(BrutalityParticles.OMEGA.get(),
                        slotContext.entity().getX(),
                        slotContext.entity().getY(0.5),
                        slotContext.entity().getZ(), 1,
                        0.15, 0.15, 0.15, 0.15
                );
            }
        }
    }

    @Override
    public void onWearerKnockback(LivingEntity attacker, @NotNull LivingEntity victim, ItemStack curio, LivingKnockBackEvent event) {
        if (!attacker.hasEffect(BrutalityEffects.ENRAGED)) return;
        float roll = attacker.getRandom().nextFloat();
        if (roll < attacker.getAttributeValue(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO)) {
            event.setStrength(event.getStrength() * 5F);

            if (attacker.level() instanceof ServerLevel serverLevel) {
                RandomSource random = serverLevel.getRandom();

                serverLevel.sendParticles(BrutalityParticles.ONOMATOPOEIA.get(), victim.getX(), victim.getY(0.5), victim.getZ(), 1, 0.35, 0.2, 0.35, 0.1);

                SoundEvent soundEvent = BrutalitySounds.getRandomSound(BrutalitySounds.PUNCH_SOUNDS, random).get();
                serverLevel.playSound(null, victim.getX(), victim.getY(0.5), victim.getZ(), soundEvent,
                        SoundSource.PLAYERS,
                        1.0F, Mth.nextFloat(random, 0.9F, 1.1F));

                ScreenShakePayload payload = new ScreenShakePayload(ShakeInstance.builder().fadeInTicks(0).fadeOutTicks(5).durationTicks(15).build());
                if (attacker instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayersNear(serverLevel, serverPlayer, victim.getX(), victim.getY(0.5), victim.getZ(), 3, payload);
                    PacketDistributor.sendToPlayer(serverPlayer, payload);
                } else {
                    PacketDistributor.sendToPlayersNear(serverLevel, null, victim.getX(), victim.getY(0.5), victim.getZ(), 3, payload);
                }
            }
        }

    }
}

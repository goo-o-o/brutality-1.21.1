package com.goo.curiosities.common.item.curio.hand;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.goo_lib.common.network.clientbound.ScreenShakePayload;
import com.goo.goo_lib.util.screenshake.ShakeInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

public class OmegaGauntlet extends CuriositiesCurioItem {
    public static float CHANCE = 0.15F;
    public OmegaGauntlet(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 10 == 0) {
            if (slotContext.entity().level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(CuriositiesParticles.OMEGA.get(),
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
        float roll = attacker.getRandom().nextFloat();
        if (roll <= CHANCE) {
            event.setStrength(event.getStrength() * 5F);

            if (attacker.level() instanceof ServerLevel serverLevel) {
                RandomSource random = serverLevel.getRandom();

                serverLevel.sendParticles(CuriositiesParticles.ONOMATOPOEIA.get(), victim.getX(), victim.getY(0.5), victim.getZ(), 1, 0.35, 0.2, 0.35, 0.1);

                serverLevel.playSound(null, victim.getX(), victim.getY(0.5), victim.getZ(), CuriositiesSounds.OMEGA_GAUNTLET_IMPACT,
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

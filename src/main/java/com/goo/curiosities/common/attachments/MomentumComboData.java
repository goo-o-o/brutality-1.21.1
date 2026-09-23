package com.goo.curiosities.common.attachments;

import com.goo.curiosities.common.registry.CuriositiesAttachments;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class MomentumComboData {
    private static final int GRACE_TICKS = 40;
    public static final StreamCodec<RegistryFriendlyByteBuf, MomentumComboData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_LONG,
                    MomentumComboData::lastMinedTick,
                    ByteBufCodecs.VAR_INT,
                    MomentumComboData::combo,
                    MomentumComboData::new
            );

    public static final Codec<MomentumComboData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("lastMinedTick").forGetter(MomentumComboData::lastMinedTick),
                    Codec.INT.fieldOf("combo").forGetter(MomentumComboData::combo)
            ).apply(instance, MomentumComboData::new)
    );


    private final long lastMinedTick;
    private final int combo;

    public MomentumComboData() {
        this.lastMinedTick = 0L;
        this.combo = 0;
    }

    public MomentumComboData(long lastMinedTick, int combo) {
        this.lastMinedTick = lastMinedTick;
        this.combo = combo;
    }

    public MomentumComboData nextCombo(long currentTick) {
        return new MomentumComboData(currentTick, this.combo + 1);
    }

    // Returns a fresh reset state starting at 1 combo
    public MomentumComboData resetCombo(long currentTick) {
        return new MomentumComboData(currentTick, 1);
    }

    public boolean isExpired(long currentTime) {
        // If they have never mined a block yet, it's technically "expired" / unstarted
        if (this.lastMinedTick == 0L) return true;
        return currentTime - this.lastMinedTick > GRACE_TICKS;
    }


    public long lastMinedTick() {
        return lastMinedTick;
    }

    public int combo() {
        return combo;
    }

    public static void addCombo(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        // TODO: add downgrade that also does this
        if (!CurioUtil.isWearingCurio(player, CuriositiesItems.HAND_OF_DESTRUCTION.value())) return;
        long currentTick = player.level().getGameTime();

        MomentumComboData oldData = player.getData(CuriositiesAttachments.MOMENTUM_COMBO);
        MomentumComboData newData;

        if (oldData.isExpired(currentTick)) {
            newData = oldData.resetCombo(currentTick);
        } else {
            newData = oldData.nextCombo(currentTick);
        }

        player.setData(CuriositiesAttachments.MOMENTUM_COMBO, newData);

    }

    public static void resetCombo(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();

        long currentTick = player.level().getGameTime();
        MomentumComboData data = player.getData(CuriositiesAttachments.MOMENTUM_COMBO);

        if (data.isExpired(currentTick) && data.combo() > 0) {
            player.setData(CuriositiesAttachments.MOMENTUM_COMBO, new MomentumComboData());

            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.syncData(CuriositiesAttachments.MOMENTUM_COMBO.get());
            }
        }
    }
}
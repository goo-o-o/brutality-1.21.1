package com.goo.curiosities.common.item.curio.head;

import com.goo.curiosities.common.entity.TimeStopField;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEntities;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class TheWorld extends CuriositiesCurioItem {
    public TheWorld(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), TheWorld.class);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(player, curio.getItem(), 180F, () -> {
            TimeStopField field = new TimeStopField(CuriositiesEntities.TIME_STOP_FIELD.value(), player.level(), 7.5F);
            field.setOwner(player);
            field.setPos(player.position());

            if (!player.level().isClientSide())
                player.level().addFreshEntity(field);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), CuriositiesSounds.TIME_STOP.get(), SoundSource.MASTER, 3.0F, 1.0F);
        });
    }
}

package com.goo.curiosities.common.item.curio.back;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NetheriteJetpack extends CuriositiesCurioItem {
    // set for players that currently have spacebar held
    public static final Set<UUID> ACTIVE_PLAYERS = new HashSet<>();

    public static void setActive(Player player, boolean active) {
        if (active)
            ACTIVE_PLAYERS.add(player.getUUID());
        else
            ACTIVE_PLAYERS.remove(player.getUUID());
    }

    public static boolean isActive(Player player) {
        return ACTIVE_PLAYERS.contains(player.getUUID());
    }

    public NetheriteJetpack(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 15;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) return;

        // check common conditions
        if (!player.isFallFlying()) return;
        if (stack.getDamageValue() >= stack.getMaxDamage()) return;


        // client handles physics
        if (player.level() instanceof ServerLevel serverLevel) {
            if (isActive(player)) {
                serverLevel.sendParticles(
                        ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        player.getX(), player.getY(), player.getZ(),
                        0, 0, 0, 0, 0
                );

                if (player.tickCount % 20 == 0) {
                    stack.hurtAndBreak(1, player, EquipmentSlot.BODY);
                }
            }
        } else {
            double power = 1.0;
            player.push(player.getLookAngle().scale(power * 0.01 + 0.01));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return true;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }
}

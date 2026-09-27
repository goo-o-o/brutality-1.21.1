package com.goo.curiosities.common.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public abstract class AdditionalFishingLootCurio extends CuriositiesCurioItem {

    public AdditionalFishingLootCurio(Properties properties) {
        super(properties);
    }

    /**
     * Chance to roll an additional loot pool, in decimals
     */
    public abstract float getAdditionalChance(Player player, FishingHook hook, ItemStack rodStack);

    /**
     * Additional rolls count, evaluated independently with getAdditionalChance
     */
    public abstract int getAdditionalRolls(Player player, FishingHook hook, ItemStack rodStack);

    public void spawnAdditionalLoot(Player player, FishingHook hook, ItemStack rodStack) {
        int additionalRolls = getAdditionalRolls(player, hook, rodStack);
        float chance = getAdditionalChance(player, hook, rodStack);

        if (player.level() instanceof ServerLevel serverLevel) {
            int fishingLuckBonus = EnchantmentHelper.getFishingLuckBonus(serverLevel, rodStack, player);

            LootParams lootParams = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, hook.position())
                    .withParameter(LootContextParams.TOOL, rodStack)
                    .withParameter(LootContextParams.THIS_ENTITY, hook)
                    .withParameter(LootContextParams.ATTACKING_ENTITY, player)
                    .withLuck((float) fishingLuckBonus + player.getLuck())
                    .create(LootContextParamSets.FISHING);
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);

            for (int i = 0; i < additionalRolls; i++) {
                if (player.getRandom().nextFloat() <= chance) {
                    List<ItemStack> list = lootTable.getRandomItems(lootParams);
                    spawnLoot(list, hook, player);
                }
            }
        }
    }

    private static void spawnLoot(List<ItemStack> list, FishingHook hook, Player player) {
        for (ItemStack itemstack : list) {
            ItemEntity itementity = new ItemEntity(hook.level(), hook.getX(), hook.getY(), hook.getZ(), itemstack);
            double d0 = player.getX() - hook.getX();
            double d1 = player.getY() - hook.getY();
            double d2 = player.getZ() - hook.getZ();
            double d3 = 0.1;
            itementity.setDeltaMovement(d0 * d3, d1 * d3 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * d3);
            hook.level().addFreshEntity(itementity);
            player.level()
                    .addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5, player.getZ() + 0.5, hook.getRandom().nextInt(6) + 1));
            if (itemstack.is(ItemTags.FISHES)) {
                player.awardStat(Stats.FISH_CAUGHT, 1);
            }
        }
    }

}

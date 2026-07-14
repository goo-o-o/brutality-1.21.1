package com.goo.brutality.common.items.curio.head;


import com.goo.brutality.common.items.BrutalityCurioItem;
import com.goo.brutality.common.registry.BrutalityItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class SwallowedPenny extends BrutalityCurioItem {

    public SwallowedPenny(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, DamageContainer container) {
        if (wearer instanceof Player player) {
            if (container.getNewDamage() >= 2 && !player.getCooldowns().isOnCooldown(BrutalityItems.Curio.Coin.SWALLOWED_PENNY.value())) {
                // TODO: Implement
//                List<ItemStack> coinsInInventory = CoinHelper.getCoinsInInventory(player);
//                ItemStack coin = coinsInInventory.get(player.getRandom().nextInt(coinsInInventory.size()));
//                BrutalityCoinItem coinItem = ((BrutalityCoinItem) coin.getItem());
//                if (player.level() instanceof ServerLevel serverLevel) {
//                    coinItem.playCoinTossSounds(player, serverLevel);
//                    player.swing(InteractionHand.MAIN_HAND, true);
//                    VxPhysicsWorld world = VxPhysicsWorld.get(serverLevel.dimension());
//
//                    if (world != null && world.isRunning()) {
//                        world.execute(() -> CoinHelper.spawnAndLaunchCoin(coinItem, player, coin, world));
//
//                    }
//
//                }
                player.getCooldowns().addCooldown(BrutalityItems.Curio.Coin.SWALLOWED_PENNY.value(), 60);
            }
        }
    }

}

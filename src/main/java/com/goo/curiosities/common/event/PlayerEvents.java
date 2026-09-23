package com.goo.curiosities.common.event;

import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.ServerDoubleJumpHandler;
import com.goo.curiosities.common.attachments.MomentumComboData;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.item.curio.back.NetheriteJetpack;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.goo.goo_lib.common.event.custom.EventResult;
import com.goo.goo_lib.common.event.custom.PlayerSwimEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.level.BlockEvent;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * Player-specific events, {@link net.neoforged.neoforge.event.tick.PlayerTickEvent}should be handled in {@link  CommonTickEvents}
 */
@EventBusSubscriber(modid = Curiosities.MOD_ID)
public class PlayerEvents {
    @SubscribeEvent
    public static void onGetPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            boolean hasHandOfDestruction = handler.isEquipped(CuriositiesItems.HAND_OF_DESTRUCTION.value());
            if (handler.isEquipped(CuriositiesItems.GYROSCOPE.value()) || hasHandOfDestruction) {
                if (!player.onGround()) {
                    event.setNewSpeed(event.getOriginalSpeed() * 5); // undo the 80% reduction
                }
            }
            if (handler.isEquipped(CuriositiesItems.SWISS_ARMY_KNIFE.value()) || hasHandOfDestruction) {
                ItemStack heldItem = player.getMainHandItem();
                // do nothing if barehand or holding a non-tool item
                if (!(heldItem.getItem() instanceof DiggerItem digger)) {
                    return;
                }

                BlockState state = event.getState();

                float mult = hasHandOfDestruction ? 1F : 0.5F;
                // if the tool is already suitable, let vanilla handle it at 100% speed
                if (!heldItem.isCorrectToolForDrops(state)) {
                    float calculatedSpeed = digger.getTier().getSpeed() * mult;
                    if (calculatedSpeed > event.getNewSpeed()) {
                        event.setNewSpeed(calculatedSpeed);
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onEntityPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (event.getPlacedBlock().getBlock() instanceof ConcretePowderBlock concretePowderBlock) {
                if (CurioUtil.isWearingCurio(livingEntity, CuriositiesItems.PORTABLE_CONCRETE_MIXER.value(), CuriositiesItems.HAND_OF_CREATION.value())) {
                    Block concrete = concretePowderBlock.concrete;
                    event.getLevel().setBlock(event.getPos(), concrete.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSpawnPhantoms(PlayerSpawnPhantomsEvent event) {
        Player player = event.getEntity();
        if (CurioUtil.isWearingCurio(player, CuriositiesItems.COLD_PILLOW.value())) {
            event.setResult(PlayerSpawnPhantomsEvent.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(CanPlayerSleepEvent event) {
        Player player = event.getEntity();
        boolean shouldCancel = CurioUtil.isWearingCurio(player, CuriositiesItems.ESPRESSO.value()) || player.hasEffect(CuriositiesEffects.CAFFEINATED);
        if (shouldCancel) {
            event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
        }

    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();

        if (CurioUtil.isWearingCurio(player, CuriositiesItems.COLD_PILLOW.value())) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 180 * 20, 2), player);
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 180 * 20, 2), player);
        }

    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        ItemStack heldStack = player.getItemInHand(hand);

        // bottle + overworld + y level
        if (heldStack.is(Items.GLASS_BOTTLE) && level.dimension() == Level.OVERWORLD && player.getY() > 200.0D) {

            level.playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BOTTLE_FILL_DRAGONBREATH,
                    SoundSource.NEUTRAL,
                    1.0F, 1.0F
            );

            if (!level.isClientSide()) {
                ItemStack resultStack = CuriositiesItems.CLOUD_IN_A_BOTTLE.value().getDefaultInstance();

                // similar to bucket logic
                ItemStack updatedHandStack = ItemUtils.createFilledResult(heldStack, player, resultStack);
                player.setItemInHand(hand, updatedHandStack);
            }

            // signal it was a success
            event.setCancellationResult(InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide()).getResult());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerDoubleJumpHandler.onPlayerDisconnect(event.getEntity().getUUID());
        NetheriteJetpack.setActive(event.getEntity(), false);
    }

    @SubscribeEvent
    public static void onPlayerSwim(PlayerSwimEvent event) {
        Player player = event.getEntity();

        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            if (player.isInLava()) {
                if (handler.isEquipped(CuriositiesItems.SURTRS_HORN.value())) {
                    event.setResult(EventResult.FAIL);
                } else if (handler.isEquipped(CuriositiesItems.SALAMANDERS_STRIDERS.value())) {
                    event.setResult(EventResult.SUCCESS);
                }
            } else if (player.isInWater()) {
                if (handler.isEquipped(CuriositiesItems.POSEIDONS_BLESSING.value()) || handler.isEquipped(CuriositiesItems.WAXY_CUTICLE.value())) {
                    event.setResult(EventResult.FAIL);
                }
            } else if (handler.isEquipped(CuriositiesItems.FLIPPERS_OF_ICARUS.value())) {
                event.setResult(EventResult.SUCCESS);
            }
        });

    }


    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {
        // Only run when respawning from death (not returning from the End portal)
        if (event.isWasDeath()) {
            ServerPlayer newPlayer = (ServerPlayer) event.getEntity();

            // Query all equipped curios and force onEquip re-run
            CuriosApi.getCuriosInventory(newPlayer).ifPresent(handler -> {
                handler.findCurios(stack -> stack.getItem() instanceof CuriositiesCurioItem).forEach(slotResult -> {
                    if (slotResult.stack().getItem() instanceof CuriositiesCurioItem curio) {
                        curio.reEvaluatePassives(slotResult.slotContext());
                    }
                });
            });
        }
    }


    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        MomentumComboData.resetCombo(event);
    }

}

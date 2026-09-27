package com.goo.curiosities.mixin;


import com.goo.curiosities.common.item.FishingLuckModifyingCurioItem;
import com.goo.curiosities.common.item.IFishingBobberCurioItem;
import com.goo.curiosities.common.item.IHookedInEntityFishingCurioItem;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {

    @Shadow
    @Nullable
    public abstract Player getPlayerOwner();

    @Shadow
    private int nibble;

    @WrapOperation(
            method = "retrieve",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/ReloadableServerRegistries$Holder;getLootTable(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/world/level/storage/loot/LootTable;"
            )
    )
    private LootTable redirectLootTable(ReloadableServerRegistries.Holder instance, ResourceKey<LootTable> lootTableKey, Operation<LootTable> original) {
        FishingHook hook = (FishingHook) (Object) this;
        Player player = this.getPlayerOwner();

        if (player != null && hook.level().getFluidState(hook.blockPosition()).is(FluidTags.LAVA)) {
            if (CurioUtil.isWearingCurio(player, CuriositiesItems.LAVAPROOF_FISHING_HOOK.value(),
                    CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value())) {
                // replace with piglin bartering
                return original.call(instance, BuiltInLootTables.PIGLIN_BARTERING);
            }
        }

        return original.call(instance, lootTableKey);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void makeLavaProof(CallbackInfo ci) {
        FishingHook hook = (FishingHook) (Object) this;
        Player player = this.getPlayerOwner();

        if (player != null) {
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                if (handler.isEquipped(CuriositiesItems.LAVAPROOF_FISHING_HOOK.value())
                        || handler.isEquipped(CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value())) {
                    if (hook.isInLava()) {
                        hook.clearFire();
                    }
                }

                handler.findCurios(stack -> stack.getItem() instanceof IHookedInEntityFishingCurioItem).forEach(
                        result -> ((IHookedInEntityFishingCurioItem) result.stack().getItem()).onHookedTick(hook, player, result.stack())
                );

            });


        }


    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private boolean allowLavaFloating(FluidState fluidState, TagKey<Fluid> tag, Operation<Boolean> original) {
        boolean isWater = original.call(fluidState, tag);
        if (isWater) return true;


        Player player = this.getPlayerOwner();
        boolean hasLavaHook = CurioUtil.isWearingCurio(player, CuriositiesItems.LAVAPROOF_FISHING_HOOK.value(),
                CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value());

        // treat lava as valid
        return player != null && fluidState.is(FluidTags.LAVA) && hasLavaHook;
    }


    @WrapOperation(
            method = "catchingFish",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
            )
    )
    private boolean allowLavaWaterCheck(BlockState state, Block block, Operation<Boolean> original) {
        if (original.call(state, block)) {
            return true;
        }

        FishingHook hook = (FishingHook) (Object) this;
        Player player = this.getPlayerOwner();
        if (CurioUtil.isWearingCurio(player, CuriositiesItems.LAVAPROOF_FISHING_HOOK.value(),
                CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value())) {
            if (block == Blocks.WATER && player != null) {
                FluidState fluidState = hook.level().getFluidState(hook.blockPosition());
                return fluidState.is(FluidTags.LAVA);
            }
        }

        return false;
    }

    @WrapOperation(
            method = "catchingFish",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
            )
    )
    private int redirectLavaParticles(
            ServerLevel level, ParticleOptions particle, double x, double y, double z,
            int count, double xDist, double yDist, double zDist, double speed,
            Operation<Integer> original
    ) {
        FishingHook hook = (FishingHook) (Object) this;
        Player player = this.getPlayerOwner();
        boolean hasLavaHook = CurioUtil.isWearingCurio(player, CuriositiesItems.LAVAPROOF_FISHING_HOOK.value(), CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value());

        if (player != null && hook.isInLava() && hasLavaHook) {
            if (particle == ParticleTypes.BUBBLE) {
                particle = ParticleTypes.SMOKE;
            } else if (particle == ParticleTypes.FISHING || particle == ParticleTypes.SPLASH) {
                particle = ParticleTypes.FLAME;
            }
        }

        return original.call(level, particle, x, y, z, count, xDist, yDist, zDist, speed);
    }

    @WrapOperation(
            method = "retrieve",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/item/ItemEntity;"
            )
    )
    private ItemEntity spawnLavaFishedAboveLava(
            Level level, double x, double y, double z, ItemStack stack, Operation<ItemEntity> original
    ) {
        FishingHook hook = (FishingHook) (Object) this;
        Player player = this.getPlayerOwner();
        boolean hasLavaHook = CurioUtil.isWearingCurio(player, CuriositiesItems.LAVAPROOF_FISHING_HOOK.value(), CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value());

        if (player != null && hook.isInLava() && hasLavaHook) {
            return original.call(level, x, y + 0.5, z, stack);
        }

        return original.call(level, x, y, z, stack);
    }

    @ModifyArg(
            method = "pullEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"
            ),
            index = 0
    )
    private double modifyPullScale(double originalScale) {
        Player player = this.getPlayerOwner();

        if (player != null) {
            // 50% boost per
            return originalScale * (1 + (0.5 * CuriosApi.getCuriosInventory(player).map(handler ->
                    handler.findCurios(stack -> stack.is(CuriositiesItems.HIGH_TEST_FISHING_LINE) || stack.is(CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value())).size()).orElse(0)));
        }

        return originalScale;
    }


    @Inject(method = "catchingFish", at = @At("HEAD"))
    private void keepFishBiting(CallbackInfo ci) {
        FishingHook hook = (FishingHook) (Object) this;
        Player player = this.getPlayerOwner();

        if (player == null) return;

        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            if (handler.isEquipped(CuriositiesItems.REDSTONE_POWERED_FISHING_REEL.value()) ||
                    handler.isEquipped(CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value())) {

                if (this.nibble > 0) {
                    ItemStack rodStack = ItemStack.EMPTY;
                    InteractionHand hand = InteractionHand.MAIN_HAND;

                    if (player.getMainHandItem().getItem() instanceof FishingRodItem) {
                        rodStack = player.getMainHandItem();
                    } else if (player.getOffhandItem().getItem() instanceof FishingRodItem) {
                        rodStack = player.getOffhandItem();
                        hand = InteractionHand.OFF_HAND;
                    }

                    if (!rodStack.isEmpty()) {
                        int damage = hook.retrieve(rodStack);

                        if (!(handler.isEquipped(CuriositiesItems.SUPREME_MASTER_ANGLERS_BACKPACK_OF_PEERLESS_FISHING.value()) ||
                                handler.isEquipped(CuriositiesItems.REINFORCED_ROD_BLANK.value()))) {
                            ItemStack originalStack = rodStack.copy();
                            rodStack.hurtAndBreak(damage, player, LivingEntity.getSlotForHand(hand));
                            if (rodStack.isEmpty()) {
                                EventHooks.onPlayerDestroyItem(player, originalStack, hand);
                            }
                        }

                        hook.level().playSound(
                                null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL,
                                1.0F, 0.4F / (hook.level().getRandom().nextFloat() * 0.4F + 0.8F)
                        );
                        player.swing(hand, true);
                        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    }
                }

                // if we have auto reel we won't ever need the keep fish biting logic
            } else if (handler.isEquipped(stack -> stack.getItem() instanceof IFishingBobberCurioItem)) {
                if (this.nibble > 0) {
                    if (hook.tickCount % 5 == 0) {
                        if (hook.level() instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.FISHING, hook.getX(), hook.getY(), hook.getZ(), 3, 0.1, 0.1, 0.1, 0);
                        }
                    }
                    // keep nibble locked at a high value so it never runs out
                    this.nibble = 2;
                }
            }

        });
    }

    @WrapOperation(
            method = "retrieve",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/loot/LootParams$Builder;withLuck(F)Lnet/minecraft/world/level/storage/loot/LootParams$Builder;"
            )
    )
    private LootParams.Builder applyPerHookLuck(
            LootParams.Builder builder, float luck, Operation<LootParams.Builder> original
    ) {
        FishingHook hook = (FishingHook) (Object) this;
        Player player = hook.getPlayerOwner();

        if (player != null) {
            float extraLuck = CuriosApi.getCuriosInventory(player).map(handler -> {
                float luckIncrement = 0;
                for (SlotResult result : handler.findCurios(stack -> stack.getItem() instanceof FishingLuckModifyingCurioItem)) {
                    luckIncrement += ((FishingLuckModifyingCurioItem) result.stack().getItem()).getFishingLuckBonus(result.stack(), player, hook);
                }
                return luckIncrement;
            }).orElse(0F);

            return original.call(builder, luck + extraLuck);
        }

        return original.call(builder, luck);
    }

}
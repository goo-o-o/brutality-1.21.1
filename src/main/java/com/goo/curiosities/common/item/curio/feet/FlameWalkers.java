package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.client.registry.CuriositiesParticles;
import com.goo.curiosities.common.item.FootstepCurioItem;
import com.goo.goo_lib.client.particle.FlatParticleOption;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class FlameWalkers extends FootstepCurioItem {
    protected float speedBonus;

    public FlameWalkers(Properties properties) {
        super(properties);
        this.speedBonus = 0.35F;
    }

//    @Override
//    public void curioTick(SlotContext slotContext, ItemStack stack) {
//        LivingEntity livingEntity = slotContext.entity();
//
//        if (livingEntity.onGround()) {
//            Level level = livingEntity.level();
//            BlockPos pos = livingEntity.blockPosition();
//
//            if (level.getBlockState(pos).isAir()) {
//                BlockState fireState = BaseFireBlock.getState(level, pos);
//                if (fireState.canSurvive(level, pos)) {
//                    level.setBlock(pos, fireState, 3);
//                }
//            }
//        }
//    }

    @Override
    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        Holder<Attribute> attribute = attributeInstance.getAttribute();
        if (attribute.is(Attributes.MOVEMENT_SPEED)) {
            if (shouldActivate(slotContext.entity()))
                return total * speedBonus;
        }
        return super.getDynamicAttributeBonus(slotContext, stack, attributeInstance, total, unmodified);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && entity.level().isClientSide()) {
            if (shouldActivate(entity)) {
                Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();

                map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(id, speedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                return map;
            }
        }
        return super.getAttributeModifiers(slotContext, id, stack);
    }

    private boolean shouldActivate(LivingEntity livingEntity) {
        BlockState feetBlockState = livingEntity.getBlockStateOn();
        if (feetBlockState.is(Blocks.MAGMA_BLOCK) || feetBlockState.getBlock() instanceof CampfireBlock && feetBlockState.getValue(CampfireBlock.LIT)) {
            return true;
        }
        return livingEntity.isOnFire();
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.unconditional(MobEffects.FIRE_RESISTANCE, 0)
        );
    }

    @Override
    public void onFootstep(LivingEntity livingEntity, Vec3 position, FootstepCurioItem curioItem, boolean left, boolean hasValidSurface, int amount, float partialTick) {
        // spawn particle on client level
        if (hasValidSurface && livingEntity.level().isClientSide()) {
            FlatParticleOption particleOption = new FlatParticleOption(
                    CuriositiesParticles.MOLTEN_FOOTPRINT.get(),
                    getFootprintSize(livingEntity, curioItem) / 2F,
                    90.0F,
                    -livingEntity.getPreciseBodyRotation(partialTick),
                    0.0F
            );

            livingEntity.level().addParticle(
                    particleOption,
                    position.x(), position.y(), position.z(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }


}

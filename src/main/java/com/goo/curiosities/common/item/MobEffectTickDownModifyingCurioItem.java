package com.goo.curiosities.common.item;

import com.goo.curiosities.util.CurioUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class MobEffectTickDownModifyingCurioItem extends CuriositiesCurioItem {
    // diamond clock - debuffs tick down 25% faster while buffs tick down 25% slower
    // netherite clock - debuffs tick down 35% faster while buffs tick down 35% slower
    // shulker clock - debuffs tick down 50% faster while buffs tick down 50% slower

    private final float chance;

    public MobEffectTickDownModifyingCurioItem(Properties properties, float chance) {
        super(properties);
        this.chance = chance;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CurioUtil.ensureUnique(slotContext.entity(), MobEffectTickDownModifyingCurioItem.class);
    }

    public static int modifyDuration(MobEffectInstance instance, LivingEntity livingEntity, Operation<Integer> original) {
        return CuriosApi.getCuriosInventory(livingEntity)
                .flatMap(handler -> handler.findFirstCurio(stack -> stack.getItem() instanceof MobEffectTickDownModifyingCurioItem))
                .map(slotResult -> {
                    MobEffectTickDownModifyingCurioItem curio = (MobEffectTickDownModifyingCurioItem) slotResult.stack().getItem();
                    if (curio.chance <= 0.0f) return original.call(instance);

                    int interval = Math.max(1, Math.round(1.0f / curio.chance));
                    if (livingEntity.tickCount % interval == 0) {
                        if (instance.getEffect().value().isBeneficial()) {
                            // skip decrement entirely; return current duration without mutating
                            return instance.getDuration();
                        } else {
                            // decrement 2 ticks
                            int postTickDuration = original.call(instance);
                            int newDuration = Math.max(0, postTickDuration - 1);
                            instance.duration = newDuration;
                            return newDuration;
                        }
                    }

                    return original.call(instance);
                })
                .orElseGet(() -> original.call(instance));
    }
}

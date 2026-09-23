package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.util.CurioUtil;
import com.goo.goo_lib.util.DelayedTaskScheduler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class HoneyComb extends CuriositiesCurioItem {
    public HoneyComb(Properties properties) {
        super(properties);
    }

    public static void spawnBees(LivingEntity wearer, LivingEntity attacker, int amount, int ticks) {
        Level level = wearer.level();
        for (int i = 0; i < amount; i++) {
            Bee bee = EntityType.BEE.create(level);
            if (bee == null) continue;

            double offsetX = (level.random.nextDouble() - 0.5D) * 1.5D;
            double offsetZ = (level.random.nextDouble() - 0.5D) * 1.5D;
            bee.moveTo(wearer.getX() + offsetX, wearer.getY() + 1.0D, wearer.getZ() + offsetZ, level.random.nextFloat() * 360F, 0F);

            // set target and turn bee angry
            bee.setTarget(attacker);
            bee.setPersistentAngerTarget(attacker.getUUID());
            bee.setRemainingPersistentAngerTime(ticks);

            level.addFreshEntity(bee);

            DelayedTaskScheduler.queueServerWork(level, ticks, bee::discard);
        }
    }

    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (wearer.level().isClientSide()) return;
        if (!(source.getEntity() instanceof LivingEntity attacker)) return;

        CurioUtil.validateCooldown(wearer, curio.getItem(), 5F, () -> spawnBees(wearer, attacker, 3, 100));
    }
}

package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.entity.HoneyGlobuleEntity;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEntities;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class NectarGland extends CuriositiesCurioItem {
    public NectarGland(Properties properties) {
        super(properties);
    }

    public static void spawnGlobules(LivingEntity wearer, int amount) {
        RandomSource random = wearer.getRandom();
        if (wearer.level().isClientSide()) return;

        wearer.level().playSound(null, wearer.getX(), wearer.getY(0.5), wearer.getZ(), CuriositiesSounds.GOOPY_SLIME, SoundSource.PLAYERS,
                1,
                Mth.nextFloat(random, 0.8F, 1.2F)
        );

        for (int i = 0; i <= amount; i++) {
            HoneyGlobuleEntity honeyGlobuleEntity = new HoneyGlobuleEntity(CuriositiesEntities.HONEY_GLOBULE.value(), wearer.level());
            honeyGlobuleEntity.setPos(wearer.getX(), wearer.getY(0.5), wearer.getZ());

            honeyGlobuleEntity.setDeltaMovement(
                    Mth.nextFloat(random, -0.25F, 0.25F),
                    Mth.nextFloat(random, 0.125F, 0.375F),
                    Mth.nextFloat(random, -0.25F, 0.25F)
            );

            wearer.level().addFreshEntity(honeyGlobuleEntity);
        }
    }

    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {

        CurioUtil.validateCooldown(wearer, curio.getItem(), 2F, () -> spawnGlobules(wearer, wearer.getRandom().nextIntBetweenInclusive(2, 3)));

    }
}

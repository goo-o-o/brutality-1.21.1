package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class RoyalJelly extends CuriositiesCurioItem {
    public RoyalJelly(Properties properties) {
        super(properties);
    }



    @Override
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
        if (wearer.level().isClientSide()) return;
        if (!(source.getEntity() instanceof LivingEntity attacker)) return;

        CurioUtil.validateCooldown(wearer, curio.getItem(), 2.5F, () -> HoneyComb.spawnBees(wearer, attacker, 5, 200));
        NectarGland.spawnGlobules(wearer, wearer.getRandom().nextIntBetweenInclusive(3,5));

    }
}

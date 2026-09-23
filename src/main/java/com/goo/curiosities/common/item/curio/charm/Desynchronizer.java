package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.entity.GlitchField;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.goo.curiosities.common.registry.CuriositiesEntities;
import com.goo.curiosities.util.CurioUtil;
import com.goo.curiosities.util.EntityUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Desynchronizer extends CuriositiesCurioItem {
    public Desynchronizer(Properties properties) {
        super(properties);
    }

    @Override
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {
        CurioUtil.validateCooldown(player, curio.getItem(), 180F, () -> {
            GlitchField field = new GlitchField(CuriositiesEntities.GLITCH_FIELD.value(), player.level(), 7.5F);
            field.setPos(player.position());

            if (!player.level().isClientSide())
                player.level().addFreshEntity(field);

            EntityUtil.getEntitiesInSphere(player.level(), player.position(), 7.5F).forEach(e -> {
                if (e instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(CuriositiesEffects.GLITCHED, 100, 0));
                }
            });
        });
    }
}

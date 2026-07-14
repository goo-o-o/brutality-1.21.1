package com.goo.brutality.common.event;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.mob_effect.ExpirableEffect;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.EffectParticleModificationEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID)
public class MobEffectEvents {

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance instance = event.getEffectInstance();
        Holder<MobEffect> effect = instance.getEffect();

        if (CurioUtil.isWearingCurio(entity, BrutalityItems.Curio.OMNICHROME_RING.value()) && effect == MobEffects.POISON)
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);

    }

    @SubscribeEvent
    public static void modifyEffectParticles(EffectParticleModificationEvent event) {
        if (event.getEffect().is(BrutalityEffects.ASURA_FORM)) {
            event.setVisible(false);
        }
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        assert effectInstance != null;
        if (effectInstance.getEffect().value() instanceof ExpirableEffect expirableEffect) {
            expirableEffect.onEffectExpired(event.getEntity(), effectInstance);
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        assert effectInstance != null;
        if (effectInstance.getEffect().value() instanceof ExpirableEffect expirableEffect) {
            expirableEffect.onEffectRemoved(event.getEntity(), effectInstance);
        }
    }

}
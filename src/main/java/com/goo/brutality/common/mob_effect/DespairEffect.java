package com.goo.brutality.common.mob_effect;

import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.common.registry.BrutalityEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class DespairEffect extends MobEffect {
    public DespairEffect(MobEffectCategory category, int color) {
        super(category, color);
    }


//    public static float modifyLightTexture(Player player, float original) {
//        if (player.hasEffect(BrutalityEffects.DESPAIR)) {
//            MobEffectInstance instance = player.getEffect(BrutalityEffects.DESPAIR);
//            assert instance != null;
//
//            int effectiveLightLevel = 14 - (instance.getAmplifier());
//
//            float lightScale = 1.0F - (effectiveLightLevel / 15.0F);
//            lightScale = Math.clamp(lightScale, 0.0F, 1.0F);
//
//            if (lightScale > 0.0F) {
//                return lightScale;
//            }
//        }
//        return original;
//    }

    public static void modifyDarkness(LivingEntity entity, CallbackInfoReturnable<Float> cir) {
        if (entity.hasEffect(BrutalityEffects.DESPAIR)) {
            double effectiveness = entity.getAttributeValue(BrutalityAttributes.DESPAIR_EFFECTIVENESS);

            MobEffectInstance effectInstance = entity.getEffect(BrutalityEffects.DESPAIR);
            assert effectInstance != null;

            int effectLevel = effectInstance.getAmplifier() + 1;

            float originalDarknessFactor = cir.getReturnValue();
            float finalFactor = (float) (originalDarknessFactor + (effectLevel * 0.1F * effectiveness));

            float modifiedDarkness = Math.min(1.0F, finalFactor);

            cir.setReturnValue(modifiedDarkness);
        }
    }
}

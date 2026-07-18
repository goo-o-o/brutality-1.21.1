package com.goo.brutality.common.items.curio.necklace;

import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityAttributes;
import com.goo.brutality.common.registry.BrutalityEffects;
import com.goo.goo_lib.util.Easing;
import com.goo.goo_lib.util.screenshake.ScreenShakeUtil;
import com.goo.goo_lib.util.screenshake.ShakeInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DemonBeads extends BrutalityRageCurioItem {
    public DemonBeads(Properties properties) {
        super(properties);
    }

    @Override
    public void onTriggerRage(LivingEntity livingEntity, ItemStack curio) {
        if (livingEntity.hasEffect(BrutalityEffects.ENRAGED)) {
            float duration = (float) livingEntity.getAttributeValue(BrutalityAttributes.ENRAGED_DURATION);

            livingEntity.addEffect(new MobEffectInstance(BrutalityEffects.ASURA_FORM, (int) (duration * 20), 0), livingEntity);

            ScreenShakeUtil.addShake(ShakeInstance.builder()
                    .durationTicks(10)
                    .fadeInCurve(Easing.EASE_IN_SINE)
                    .fadeOutCurve(Easing.EASE_IN_SINE)
                    .speed(0.5F)
                    .bounds(10, 10)
                    .build());
        }
    }
}

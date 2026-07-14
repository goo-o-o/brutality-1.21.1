package com.goo.brutality.common.items.curio.necklace;

import com.goo.brutality.common.items.BrutalityRageCurioItem;
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
            MobEffectInstance effectInstance = livingEntity.getEffect(BrutalityEffects.ENRAGED);
            assert effectInstance != null;

            livingEntity.addEffect(new MobEffectInstance(BrutalityEffects.ASURA_FORM, effectInstance.getDuration(), 0), livingEntity);

            ScreenShakeUtil.addShake(new ShakeInstance.Builder()
                    .duration(10)
                    .easeIn(Easing.EASE_IN_SINE)
                    .easeOut(Easing.EASE_IN_SINE)
                    .speed(0.5F)
                    .bounds(10, 10)
                    .build());
        }
    }
}

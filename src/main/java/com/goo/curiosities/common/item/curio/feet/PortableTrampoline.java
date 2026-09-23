package com.goo.curiosities.common.item.curio.feet;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.common.registry.CuriositiesSounds;
import com.goo.curiosities.util.CurioUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public class PortableTrampoline extends CuriositiesCurioItem {
    public PortableTrampoline(Properties properties) {
        super(properties);
    }

    public static float getBounceStrength() {
        return 0.75F;
    }

    public static boolean proc(LivingEntity livingEntity, Block fallOn) {

        if (!shouldBounce(livingEntity, fallOn)) {
            return false;
        }

        actuallyBounce(livingEntity);
        return true;
    }

    @Override
    public void onWearerFall(LivingFallEvent event, ItemStack curio) {
        if (event.getDistance() >= 1)
            event.getEntity().playSound(CuriositiesSounds.BOING.get(), 0.5F, 1.0F);
        event.setCanceled(true);
    }

    private static boolean shouldBounce(LivingEntity livingEntity, Block fallOn) {
        if (!CurioUtil.isWearingCurio(livingEntity, CuriositiesItems.PORTABLE_TRAMPOLINE.value())) return false;
        if (livingEntity.isSuppressingBounce()) return false;
        return fallOn != Blocks.SLIME_BLOCK;
    }

    private static void actuallyBounce(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0) {
            double d0 = entity instanceof LivingEntity ? Mth.clamp(getBounceStrength(), 0, 1) : 0.8;
            entity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }
    }
}

package com.goo.curiosities.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Curio item with added footstep positioning support, works decently enough
 */
public class FootstepCurioItem extends CuriositiesCurioItem {
    public FootstepCurioItem(Properties properties) {
        super(properties);
    }

    /**
     * In meters/blocks
     */
    public float getFootprintSize(LivingEntity livingEntity, FootstepCurioItem curioItem) {
        return 0.25F;
    }

    /**
     * Called whenever a "footprint" is made, on both sides, using payloads to sync location
     * @param left whether it was the left foot or not
     * @param hasValidSurface whether there is a valid support under the footprint
     * @param amount how many curios of this type are equipped
     * @param partialTick 0 on servers
     */
    public void onFootstep(LivingEntity livingEntity, Vec3 position, FootstepCurioItem curioItem, boolean left, boolean hasValidSurface, int amount, float partialTick) {

    }
}

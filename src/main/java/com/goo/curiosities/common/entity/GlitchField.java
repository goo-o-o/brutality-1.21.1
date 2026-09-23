package com.goo.curiosities.common.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class GlitchField extends AbstractFieldEntity {
    public GlitchField(EntityType<?> type, Level level) {
        super(type, level);
    }

    public GlitchField(EntityType<?> type, Level level, float startRadius) {
        super(type, level, startRadius);
    }


    @Override
    protected float calculateRadius() {
        float progress = (float) tickCount / getLifetime();

        return Mth.sin(progress * Mth.PI) * startRadius;
//        return 10;
    }

    public int getLifetime() {
        return 10;
    }


}
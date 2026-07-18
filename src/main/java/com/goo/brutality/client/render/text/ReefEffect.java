package com.goo.brutality.client.render.text;

import com.goo.goo_lib.client.text.effect.TextEffect;
import com.goo.goo_lib.client.text.effect.base.OverlayEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;

public class ReefEffect implements TextEffect<Float>, OverlayEffect<Float> {

    @Override
    public MapCodec<Float> codec() {
        return Codec.FLOAT.optionalFieldOf("speed", 1F);
    }


    @Override
    public RenderType modifyOriginalRenderType(RenderType sourceType, Float config) {
        return OverlayEffect.super.modifyOriginalRenderType(sourceType, config);
    }

    @Override
    public @Nullable RenderType getOverlayRenderType(RenderType sourceType, Float config) {
        return null;
    }
}

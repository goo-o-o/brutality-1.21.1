package com.goo.curiosities.mixin;

import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.util.Colors;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

@Mixin(FishingHookRenderer.class)
public class FishingHookRendererMixin {

    @WrapOperation(
            method = "render*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;stringVertex(FFFLcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FF)V"
            )
    )
    private void redirectStringVertex(
            float x, float y, float z,
            VertexConsumer consumer, PoseStack.Pose pose,
            float stringFraction, float nextStringFraction,
            Operation<Void> original,
            @Local(argsOnly = true) FishingHook entity
    ) {
        Player owner = entity.getPlayerOwner();
        if (owner != null) {
            Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(owner);
            if (curios.isPresent()) {
                ICuriosItemHandler handler = curios.get();
                int colorStart = 0;
                int colorEnd = 0;
                boolean customLine = false;

                if (handler.isEquipped(CuriositiesItems.INFERNAL_FISHING_LINE.value())) {
                    colorStart = Colors.FIRE[0];
                    colorEnd = Colors.FIRE[1];
                    customLine = true;
                } else if (handler.isEquipped(CuriositiesItems.FRIGID_FISHING_LINE.value())) {
                    colorStart = Colors.ICE[1];
                    colorEnd = Colors.ICE[2];
                    customLine = true;
                } else if (handler.isEquipped(CuriositiesItems.SANGUINE_FISHING_LINE.value())) {
                    colorStart = Colors.NETHER[1];
                    colorEnd = Colors.NETHER[3];
                    customLine = true;
                }

                if (customLine) {
                    float f = x * stringFraction;
                    float f1 = y * (stringFraction * stringFraction + stringFraction) * 0.5F + 0.25F;
                    float f2_pos = z * stringFraction;

                    float f3_dir = x * nextStringFraction - f;
                    float f4_dir = y * (nextStringFraction * nextStringFraction + nextStringFraction) * 0.5F + 0.25F - f1;
                    float f5_dir = z * nextStringFraction - f2_pos;
                    float length = Mth.sqrt(f3_dir * f3_dir + f4_dir * f4_dir + f5_dir * f5_dir);

                    if (length > 0.0001F) {
                        f3_dir /= length;
                        f4_dir /= length;
                        f5_dir /= length;
                    }

                    int color = FastColor.ARGB32.lerp(stringFraction, colorStart, colorEnd);

                    consumer.addVertex(pose, f, f1, f2_pos)
                            .setColor(color)
                            .setNormal(pose, f3_dir, f4_dir, f5_dir);

                    return;
                }
            }
        }

        // fallback to vanilla stringVertex execution
        original.call(x, y, z, consumer, pose, stringFraction, nextStringFraction);
    }
}
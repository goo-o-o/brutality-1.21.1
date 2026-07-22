package com.goo.brutality.client.event.render;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.item.curio.function.Pi;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.util.CurioUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = Brutality.MOD_ID, value = Dist.CLIENT)
public class LevelRenderEvents {
    @SubscribeEvent
    public static void onFogRender(ViewportEvent.RenderFog event) {
        if (event.getType() == FogType.LAVA) {
            Entity entity = event.getCamera().getEntity();
            if (entity instanceof LivingEntity living) {
                float farPlaneDistance = event.getFarPlaneDistance();
                if (CurioUtil.isWearingCurio(living, BrutalityItems.Curio.LAVA_LENSES.value()))
                    farPlaneDistance = 50;
                if (CurioUtil.isWearingCurio(living, BrutalityItems.Curio.SURTRS_HORN.value()))
                    farPlaneDistance = 150;

                event.setNearPlaneDistance(-8.0f);
                event.setFarPlaneDistance(farPlaneDistance);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES)
            if (mc.options.getCameraType().isFirstPerson() && mc.player != null) {
                Player player = mc.player;
                PoseStack poseStack = event.getPoseStack();
                Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();

                double posX = Mth.lerp(event.getPartialTick().getGameTimeDeltaPartialTick(true), player.xOld, player.getX()) - cameraPos.x();
                double posY = Mth.lerp(event.getPartialTick().getGameTimeDeltaPartialTick(true), player.yOld, player.getY()) - cameraPos.y();
                double posZ = Mth.lerp(event.getPartialTick().getGameTimeDeltaPartialTick(true), player.zOld, player.getZ()) - cameraPos.z();

                poseStack.pushPose();
                poseStack.translate(posX, posY, posZ);

                Pi.render(mc.player, poseStack, event.getPartialTick().getGameTimeDeltaPartialTick(true), mc.renderBuffers().bufferSource());
                poseStack.popPose();
            }
    }
}

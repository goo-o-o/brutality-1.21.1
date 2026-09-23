package com.goo.curiosities.common.item.curio.head;

import com.goo.curiosities.client.render.OreCache;
import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.client.registry.GLRenderTypes;
import com.goo.goo_lib.client.render.PostEffectRegistry;
import com.goo.goo_lib.client.render.pipeline.ShaderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriPredicate;
import top.theillusivec4.curios.api.CuriosApi;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class MiningGoggles extends CuriositiesCurioItem {
    protected TriPredicate<LivingEntity, BlockState, BlockPos> statePredicate;

    public MiningGoggles(Properties properties) {
        super(properties);
        // ore tracker already checks for all ores only, this is just here for more specific filtering
        this.statePredicate = (livingEntity, blockState, blockPos) -> {
            if (livingEntity.getPosition(0).distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ()) <= 25) {
                // only overworld ores, upgraded versions can do all ores
                return blockState.is(Tags.Blocks.ORES_IN_GROUND_STONE) || blockState.is(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE);
            }
            return false;
        };
    }

    @OnlyIn(Dist.CLIENT)
    public static void render(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        CuriosApi.getCuriosInventory(mc.player).flatMap(handler ->
                handler.findFirstCurio(s -> s.getItem() instanceof MiningGoggles)).ifPresent(slotResult -> {
            MiningGoggles miningGoggles = ((MiningGoggles) slotResult.stack().getItem());

            PoseStack poseStack = event.getPoseStack();
            Vec3 cam = event.getCamera().getPosition();
            MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

            PostEffectRegistry.renderEffectForNextTick(GLRenderTypes.BLOOM_SHADER_LOCATION, ShaderPipeline.PipelineStage.WORLD);
            RenderType bloom = GLRenderTypes.getBloomRenderType(InventoryMenu.BLOCK_ATLAS, GREATER_DEPTH_TEST, NO_CULL);
            VertexConsumer consumer = bufferSource.getBuffer(bloom);

            poseStack.pushPose();
            poseStack.translate(-cam.x, -cam.y, -cam.z);

            for (BlockPos pos : OreCache.getActiveOres()) {
                BlockState state = mc.level.getBlockState(pos);
                if (miningGoggles.statePredicate.test(mc.player, state, pos)) {
                    poseStack.pushPose();
                    poseStack.translate(pos.getX(), pos.getY(), pos.getZ());

                    // scale from center
                    poseStack.translate(0.5, 0.5, 0.5);
                    poseStack.scale(1.002F, 1.002F, 1.002F);
                    poseStack.translate(-0.5, -0.5, -0.5);

                    BakedModel model = mc.getBlockRenderer().getBlockModel(state);

                    mc.getBlockRenderer().getModelRenderer().renderModel(
                            poseStack.last(),
                            consumer,
                            state,
                            model,
                            1.0F, 1.0F, 1.0F, // r, g, b
                            LightTexture.FULL_BRIGHT,
                            OverlayTexture.NO_OVERLAY
                    );

                    poseStack.popPose();
                }
            }

            poseStack.popPose();
            bufferSource.endBatch(bloom);
        });

    }

}

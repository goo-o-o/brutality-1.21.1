package com.goo.curiosities.client.render;

import com.goo.curiosities.common.registry.CuriositiesEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

import java.util.*;

public class ClientGlitchState {
    private static final Map<Integer, GlitchData> ENTITY_STATES = new HashMap<>();
    private static final Random RANDOM = new Random();

    public static class CloneData {
        public float offsetX, offsetY, offsetZ;
        public float r, g, b, a;
    }

    public static class GlitchData {
        public float offsetX = 0.0f;
        public float offsetY = 0.0f;
        public float offsetZ = 0.0f;
        public boolean visible = true;
        public boolean hasPushedPose = false; // tracked per-render frame
        public List<CloneData> clones = new ArrayList<>();
        public int remainingTicks = 0;
    }

    public static <T extends LivingEntity, M extends EntityModel<T>> void preRender(RenderLivingEvent.Pre<T,M> event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(CuriositiesEffects.GLITCHED)) {
            GlitchData data = getGlitchData(entity);

            if (!data.visible) {
                event.setCanceled(true);
                return;
            }

            if (data.offsetX != 0.0f || data.offsetY != 0.0f || data.offsetZ != 0.0f) {
                PoseStack poseStack = event.getPoseStack();
                poseStack.pushPose();
                poseStack.translate(data.offsetX, data.offsetY, data.offsetZ);
                data.hasPushedPose = true;
            }
        }
    }

    public static <T extends LivingEntity, M extends EntityModel<T>> void postRender(RenderLivingEvent.Post<T,M> event) {
        LivingEntity entity = event.getEntity();
        GlitchData data = getGlitchData(entity);

        // safely pop pose only if preRender actually pushed one
        if (data.hasPushedPose) {
            event.getPoseStack().popPose();
            data.hasPushedPose = false;
        }
    }

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.isPaused()) return;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof LivingEntity living) {
                if (living.hasEffect(CuriositiesEffects.GLITCHED)) {
                    ClientGlitchState.tickEntity(living);
                } else {
                    ClientGlitchState.clear(living);
                }
            }
        }
    }

    public static void tickEntity(LivingEntity entity) {
        GlitchData data = ENTITY_STATES.computeIfAbsent(entity.getId(), id -> new GlitchData());

        if (data.remainingTicks > 0) {
            data.remainingTicks--;
            return;
        }

        data.remainingTicks = RANDOM.nextInt(2);
        data.clones.clear();

        data.visible = RANDOM.nextFloat() >= 0.10f;
        if (data.visible && RANDOM.nextFloat() < 0.40f) {
            data.offsetX = (RANDOM.nextFloat() - 0.5f) * 0.6f;
            data.offsetY = (RANDOM.nextFloat() - 0.5f) * 0.2f;
            data.offsetZ = (RANDOM.nextFloat() - 0.5f) * 0.6f;
        } else {
            data.offsetX = 0.0f;
            data.offsetY = 0.0f;
            data.offsetZ = 0.0f;
        }

        int count = 1 + RANDOM.nextInt(3);
        for (int i = 0; i < count; i++) {
            CloneData clone = new CloneData();
            clone.offsetX = (RANDOM.nextFloat() - 0.5f) * 0.8f;
            clone.offsetY = (RANDOM.nextFloat() - 0.5f) * 0.3f;
            clone.offsetZ = (RANDOM.nextFloat() - 0.5f) * 0.8f;

            switch (RANDOM.nextInt(3)) {
                case 0 -> { clone.r = 1.0f; clone.g = 0.1f; clone.b = 0.1f; }
                case 1 -> { clone.r = 0.1f; clone.g = 0.9f; clone.b = 1.0f; }
                case 2 -> { clone.r = 0.9f; clone.g = 0.1f; clone.b = 1.0f; }
            }
            clone.a = 0.35f + RANDOM.nextFloat() * 0.3f;
            data.clones.add(clone);
        }
    }

    public static GlitchData getGlitchData(LivingEntity entity) {
        return ENTITY_STATES.getOrDefault(entity.getId(), new GlitchData());
    }

    public static void clear(LivingEntity entity) {
        ENTITY_STATES.remove(entity.getId());
    }
}
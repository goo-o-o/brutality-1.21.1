package com.goo.brutality.util;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ParticleUtil {

    /**
     * Gets the direct {@link TextureAtlasSprite} within a {@link SpriteSet}
     * @param sprites
     * @param index
     * @param totalSprites
     * @return
     */
    @OnlyIn(Dist.CLIENT)
    public static TextureAtlasSprite getDirectSprite(SpriteSet sprites, int index, int totalSprites) {
        return sprites.get(index, totalSprites - 1);
    }

    public static void burstParticles(Level level, double x, double y, double z, int amount, double speed, ParticleOptions... particleTypes) {
        if (level.isClientSide()) {
            for (int i = 0; i < amount; i++) {
                double mx = (level.random.nextDouble() - 0.5) * speed;
                double my = (level.random.nextDouble() - 0.5) * speed;
                double mz = (level.random.nextDouble() - 0.5) * speed;
                level.addParticle(particleTypes[level.getRandom().nextInt(particleTypes.length)], x, y, z, mx, my, mz);
            }
        } else if (level instanceof ServerLevel serverLevel) {
            int amountPerType = Math.max(1, amount / particleTypes.length);

            for (ParticleOptions type : particleTypes) {
                serverLevel.sendParticles(type, x, y, z, amountPerType, speed, speed, speed, speed);
            }
        }
    }

    public static <T extends ParticleOptions> void addParticles(Level level,
            T type, double posX, double posY, double posZ, int particleCount, double xOffset, double yOffset, double zOffset, double speed
    ) {
        if (level.isClientSide()) {
            for (int i = 0; i < particleCount; i++) {
                double spawnX = posX + Mth.nextDouble(level.getRandom(), -xOffset, xOffset);
                double spawnY = posY + Mth.nextDouble(level.getRandom(), -yOffset, yOffset);
                double spawnZ = posZ + Mth.nextDouble(level.getRandom(), -zOffset, zOffset);
                double mx = (level.random.nextDouble() - 0.5) * speed;
                double my = (level.random.nextDouble() - 0.5) * speed;
                double mz = (level.random.nextDouble() - 0.5) * speed;
                level.addParticle(type, spawnX, spawnY, spawnZ, mx, my, mz);
            }
        }
    }
}

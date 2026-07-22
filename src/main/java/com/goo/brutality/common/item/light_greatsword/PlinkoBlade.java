package com.goo.brutality.common.item.light_greatsword;

import com.goo.brutality.client.registry.BrutalityParticles;
import com.goo.brutality.common.Brutality;
import com.goo.goo_lib.client.particle.ComponentParticleOption;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class PlinkoBlade extends SwordItem {


    public PlinkoBlade(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipLines, TooltipFlag tooltipFlag) {
        float luck = Minecraft.getInstance().player != null ? Minecraft.getInstance().player.getLuck() : 0;
        tooltipLines.add(Component.empty());
        tooltipLines.add(Component.translatable("item." + Brutality.MOD_ID + ".plinko_blade.damage_range",
                Component.literal(String.valueOf(getMin(luck))).withStyle(s -> s.withBold(true).withColor(ChatFormatting.YELLOW)),
                Component.literal(String.valueOf(getMax(luck))).withStyle(s -> s.withBold(true).withColor(ChatFormatting.YELLOW))
        ));
    }


    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        double luck = 0;
        if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
            luck = livingEntity.getAttribute(Attributes.LUCK) != null ? livingEntity.getAttributeValue(Attributes.LUCK) : 0;
        }

        double mult = calculatePlinkoMultiplier(target.getRandom(), luck);
        ComponentParticleOption options = new ComponentParticleOption(BrutalityParticles.MULTIPLIER.value(), getComponentForMultiplier(mult), FastColor.ARGB32.color(0, 0), true);

        target.level().addParticle(options, target.getRandomX(1), target.getRandomY(), target.getRandomZ(1),
                (Math.random() * 2.0 - 1.0) * 0.05F,
                (Math.random()) * 0.05F,
                (Math.random() * 2.0 - 1.0) * 0.05F
        );

        float finalDamage = (float) (damage * mult);
        int particleCount = Math.min(((int) finalDamage * 2), 100);
        if (target.level() instanceof ServerLevel serverLevel) {
            double x = target.getX();
            double y = target.getY(0.5);
            double z = target.getZ();
            particleCount = Math.max(particleCount / 6, 1);
            serverLevel.sendParticles(BrutalityParticles.POKER_CHIP_BLACK.get(), x, y, z, particleCount, 0.1, 0.1, 0.1, 0.5F);
            serverLevel.sendParticles(BrutalityParticles.POKER_CHIP_BLUE.get(), x, y, z, particleCount, 0.1, 0.1, 0.1, 0.5F);
            serverLevel.sendParticles(BrutalityParticles.POKER_CHIP_GREEN.get(), x, y, z, particleCount, 0.1, 0.1, 0.1, 0.5F);
            serverLevel.sendParticles(BrutalityParticles.POKER_CHIP_RED.get(), x, y, z, particleCount, 0.1, 0.1, 0.1, 0.5F);
            serverLevel.sendParticles(BrutalityParticles.POKER_CHIP_RED_INVERSE.get(), x, y, z, particleCount, 0.1, 0.1, 0.1, 0.5F);
            serverLevel.sendParticles(BrutalityParticles.POKER_CHIP_YELLOW.get(), x, y, z, particleCount, 0.1, 0.1, 0.1, 0.5F);
        }

        return finalDamage;
    }

    private Component getComponentForMultiplier(double multiplier) {
        int gray = 0xFF9E9E9E; // < 0.5
        int yellow = 0xFFFFEB3B; // 1.0
        int orange = 0xFFFF9800; // 1.5
        int red = 0xFFF44336; // 2.0+

        int finalColor;

        if (multiplier <= 0.5) {
            finalColor = gray;
        } else if (multiplier <= 1.0) {
            float delta = (float) ((multiplier - 0.5) / 0.5);
            finalColor = FastColor.ARGB32.lerp(delta, gray, yellow);
        } else if (multiplier <= 1.5) {
            float delta = (float) ((multiplier - 1.0) / 0.5);
            finalColor = FastColor.ARGB32.lerp(delta, yellow, orange);
        } else if (multiplier <= 2.0) {
            float delta = (float) ((multiplier - 1.5) / 0.5);
            finalColor = FastColor.ARGB32.lerp(delta, orange, red);
        } else {
            finalColor = red;
        }

        return Component.literal(String.valueOf(multiplier)).withStyle(style -> style.withColor(finalColor).withBold(multiplier > 1.0));
    }

    /**
     * Uses normal distribution to calculate an appropriate multiplier
     */
    private float calculatePlinkoMultiplier(RandomSource random, double luck) {
        double z = random.nextGaussian();

        // 0% luck: mean=1.0, min=0.2, max=3.0
        // 100% luck: mean=2.0, min=1.2, max=4.0
        double mean = 1.0F + luck; // 1.0 → 2.0
        double stdDev = 0.5F;

        double raw = mean + z * stdDev;

        return (float) Math.clamp(raw, getMin(luck), getMax(luck));
    }

    private double getMin(double luck) {
        return 0.2 + luck;
    }

    private double getMax(double luck) {
        return 3 + luck;
    }

}

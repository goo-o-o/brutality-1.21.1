package com.goo.curiosities.common.item.curio.charm;

import com.goo.curiosities.common.item.CuriositiesCurioItem;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.List;

public class VillagerDictionary extends CuriositiesCurioItem {
    public VillagerDictionary(Properties properties) {
        super(properties);
    }

    @Override
    protected List<PassiveMobEffect> definePassives() {
        return List.of(
                PassiveMobEffect.conditional(MobEffects.REGENERATION, 0, VillagerDictionary::isInVillage, 20),
                PassiveMobEffect.conditional(MobEffects.DAMAGE_RESISTANCE, 0, VillagerDictionary::isInVillage, 20)
        );
    }

    private static boolean isInVillage(LivingEntity livingEntity) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            StructureManager structureManager = serverLevel.structureManager();
            BlockPos pos = livingEntity.blockPosition();
            if (structureManager.hasAnyStructureAt(pos)) {
                StructureStart structure = structureManager.getStructureWithPieceAt(pos, StructureTags.VILLAGE);
                return structure.isValid();
            }
        }
        return false;
    }
}

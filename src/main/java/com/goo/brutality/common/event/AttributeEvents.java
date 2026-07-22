package com.goo.brutality.common.event;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.registry.BrutalityAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import java.util.Set;

/**
 * Encompasses events that deal with {@link Attribute}s
 */
@EventBusSubscriber(modid = Brutality.MOD_ID)
public class AttributeEvents {




    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        Set<Holder<Attribute>> playerAttributes = Set.of(
                BrutalityAttributes.MAX_RAGE, BrutalityAttributes.ENRAGED_LEVEL,
                BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, BrutalityAttributes.ENRAGED_DURATION,

                BrutalityAttributes.SPELL_COOLDOWN, BrutalityAttributes.SPELL_DAMAGE, BrutalityAttributes.SPELL_MANA_COST,
                BrutalityAttributes.CAST_TIME,
                BrutalityAttributes.MANA_REGEN, BrutalityAttributes.MAX_MANA,

                BrutalityAttributes.UNIVERSAL_SCHOOL_LEVEL, BrutalityAttributes.DAEMONIC_SCHOOL_LEVEL, BrutalityAttributes.DARKIST_SCHOOL_LEVEL,
                BrutalityAttributes.EVERGREEN_SCHOOL_LEVEL, BrutalityAttributes.VOLTWEAVER_SCHOOL_LEVEL, BrutalityAttributes.COSMIC_SCHOOL_LEVEL,
                BrutalityAttributes.CELESTIA_SCHOOL_LEVEL, BrutalityAttributes.UMBRANCY_SCHOOL_LEVEL, BrutalityAttributes.EXODIC_SCHOOL_LEVEL,
                BrutalityAttributes.BRIMWIELDER_SCHOOL_LEVEL, BrutalityAttributes.VOIDWALKER_SCHOOL_LEVEL,

                BrutalityAttributes.DESPAIR_EFFECTIVENESS,
                BrutalityAttributes.COIN_COOLDOWN
        );


        Set<Holder<Attribute>> entityAttributes = Set.of(
                BrutalityAttributes.OMNIVAMP,

                BrutalityAttributes.DODGE_CHANCE,

                BrutalityAttributes.LETHALITY, BrutalityAttributes.ARMOR_PENETRATION,

                BrutalityAttributes.VISIBILITY, BrutalityAttributes.DAMAGE_TAKEN,

                BrutalityAttributes.GASTRONOMY_DAMAGE_DEALT, BrutalityAttributes.GASTRONOMY_WET_DEBUFF_DAMAGE_TAKEN,
                BrutalityAttributes.GASTRONOMY_DRY_DEBUFF_DAMAGE_TAKEN, BrutalityAttributes.GASTRONOMY_DEBUFF_LEVEL_MODIFIER,
                BrutalityAttributes.GASTRONOMY_DEBUFF_DURATION

        );

        event.getTypes().forEach(entityType -> {
            entityAttributes.forEach(attribute -> {
                if (!event.has(entityType, attribute)) {
                    event.add(entityType, attribute);
                }
            });
        });

        playerAttributes.forEach(attribute -> {
            if (!event.has(EntityType.PLAYER, attribute)) {
                event.add(EntityType.PLAYER, attribute);
            }
        });

    }
}

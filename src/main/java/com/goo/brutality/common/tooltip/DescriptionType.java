package com.goo.brutality.common.tooltip;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Enum for {@link ItemDescriptions} for convenient and automated descriptions
 */
public enum DescriptionType implements StringRepresentable {
    ACTIVE,
    PASSIVE,
    FULL_SET_PASSIVE,
    FULL_SET_ACTIVE,
    ON_HIT,
    ON_TRUE_MELEE_HIT,
    WHEN_THROWN,
    ON_SWING,
    ON_LEFT_CLICKING_ENTITY,
    ON_RIGHT_CLICK,
    ON_RELEASE_RIGHT_CLICK,
    ON_SHIFT_RIGHT_CLICK,
    LORE,
    ON_KILL,
    ON_SHOOT,
    ON_HOLD_RIGHT_CLICK,
    CHARM,
    DASH_ABILITY,
    ON_SUCCESSFUL_DODGE,
    MANA_COST,
    UPON_TRIGGERING_RAGE,
    ON_HEADS,
    ON_TAILS;

    private final String serializedName;

    DescriptionType() {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }
}

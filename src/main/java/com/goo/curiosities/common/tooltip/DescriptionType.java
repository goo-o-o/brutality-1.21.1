package com.goo.curiosities.common.tooltip;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Enum for {@link ItemDescriptions} for convenient and automated descriptions
 */
public enum DescriptionType implements StringRepresentable {
    MULTIPLAYER_ONLY,
    ACTIVE,
    PASSIVE,
    ON_HIT,
    ON_ARROW_HIT,
    ON_TRUE_MELEE_HIT,
    LORE,
    ON_KILL,
    ON_SWING,
    ON_SUCCESSFUL_DODGE;

    private final String serializedName;

    DescriptionType() {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }
}

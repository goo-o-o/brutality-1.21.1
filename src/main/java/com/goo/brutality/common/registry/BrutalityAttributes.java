package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BrutalityAttributes {
    private static String prepend(String name) {
        return "attributes." + Brutality.MOD_ID + "." + name;
    }

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Brutality.MOD_ID);

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    /**
     * Maximum capacity of the Rage meter.
     */
    public static final Holder<Attribute> MAX_RAGE = ATTRIBUTES.register("max_rage", () ->
            new RangedAttribute(prepend("max_rage"), 100F, 0F, 16384F)
                    .setSyncable(true));
    /**
     * The level of the effect of {@link BrutalityEffects#ENRAGED} to apply whenever rage is activated.
     */
    public static final Holder<Attribute> ENRAGED_LEVEL = ATTRIBUTES.register("enraged_level", () ->
            new RangedAttribute(prepend("enraged_level"), 1.0F, 0F, 255F) // cannot surpass vanilla limit
                    .setSyncable(true));
    /**
     * The ratio at which dealt/received damage is converted into Rage points. (e.g., 0.15 = 15%).
     */
    public static final Holder<Attribute> DAMAGE_TO_RAGE_RATIO = ATTRIBUTES.register("damage_to_rage_ratio", () ->
            new PercentageAttribute(prepend("damage_to_rage_ratio"), 0.15F, 0.0F, 16384F));

    /**
     * Duration in seconds of the {@link BrutalityEffects#ENRAGED} mob effect instance to apply whenever {@link BrutalityAttachments#RAGE} is triggered
     */
    public static final Holder<Attribute> ENRAGED_DURATION = ATTRIBUTES.register("enraged_duration", () ->
            new RangedAttribute(prepend("enraged_duration"), 2F, 0.0F, 16384F));


    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // TODO: reimplement attributes
    /**
     * Amount of mana regenerated per second.
     */
    public static final Holder<Attribute> MANA_REGEN = ATTRIBUTES.register("mana_regen", () ->
            new RangedAttribute(prepend("mana_regen"), 10.0D, 0.0F, 16384F)
                    .setSyncable(true));

    /**
     * Maximum capacity of the Mana pool.
     */
    public static final Holder<Attribute> MAX_MANA = ATTRIBUTES.register("max_mana", () ->
            new RangedAttribute(prepend("max_mana"), 100.0, 0.0F, 16384F)
                    .setSyncable(true));

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // TODO: reimplement attributes
    /**
     * Raw multiplier for spell mana cost
     */
    public static final Holder<Attribute> SPELL_MANA_COST = ATTRIBUTES.register("spell_mana_cost", () ->
            new PercentageAttribute(prepend("spell_mana_cost"), 1.0F, 0.0F, 16384F)
                    .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));


    /**
     * Raw multiplier for spell cooldown.
     */
    public static final Holder<Attribute> SPELL_COOLDOWN = ATTRIBUTES.register("spell_cooldown", () ->
            new PercentageAttribute(prepend("spell_cooldown"), 1.0F, 0.0F, 16384F)
                    .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    /**
     * Raw multiplier for spell casting duration.
     */
    public static final Holder<Attribute> CAST_TIME = ATTRIBUTES.register("cast_time", () ->
            new PercentageAttribute(prepend("cast_time"), 1.0F, 0.0F, 16384F)
                    .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    /**
     * Raw multiplier for spell casting duration.
     */
    public static final Holder<Attribute> SPELL_DAMAGE = ATTRIBUTES.register("spell_damage", () ->
            new PercentageAttribute(prepend("spell_damage"), 1.0F, 0.0F, 16384F)
                    .setSyncable(true));

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // TODO: link comment
    /**
     * Attributes for Magic School Levels
     */
    public static final Holder<Attribute> UNIVERSAL_SCHOOL_LEVEL = ATTRIBUTES.register("universal_school_level", () ->
            new RangedAttribute(prepend("universal_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> DAEMONIC_SCHOOL_LEVEL = ATTRIBUTES.register("daemonic_school_level", () ->
            new RangedAttribute(prepend("daemonic_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> DARKIST_SCHOOL_LEVEL = ATTRIBUTES.register("darkist_school_level", () ->
            new RangedAttribute(prepend("darkist_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> EVERGREEN_SCHOOL_LEVEL = ATTRIBUTES.register("evergreen_school_level", () ->
            new RangedAttribute(prepend("evergreen_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> VOLTWEAVER_SCHOOL_LEVEL = ATTRIBUTES.register("voltweaver_school_level", () ->
            new RangedAttribute(prepend("voltweaver_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> COSMIC_SCHOOL_LEVEL = ATTRIBUTES.register("cosmic_school_level", () ->
            new RangedAttribute(prepend("cosmic_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> CELESTIA_SCHOOL_LEVEL = ATTRIBUTES.register("celestia_school_level", () ->
            new RangedAttribute(prepend("celestia_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> UMBRANCY_SCHOOL_LEVEL = ATTRIBUTES.register("umbrancy_school_level", () ->
            new RangedAttribute(prepend("umbrancy_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> EXODIC_SCHOOL_LEVEL = ATTRIBUTES.register("exodic_school_level", () ->
            new RangedAttribute(prepend("exodic_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> BRIMWIELDER_SCHOOL_LEVEL = ATTRIBUTES.register("brimwielder_school_level", () ->
            new RangedAttribute(prepend("brimwielder_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));
    public static final Holder<Attribute> VOIDWALKER_SCHOOL_LEVEL = ATTRIBUTES.register("voidwalker_school_level", () ->
            new RangedAttribute(prepend("voidwalker_school_level"), 0.0, -16384F, 16384F)
                    .setSyncable(true));

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // TODO: reimplement attributes

    /**
     * Ratio of all damage types returned as health.
     */
    public static final Holder<Attribute> OMNIVAMP = ATTRIBUTES.register("omnivamp", () ->
            new PercentageAttribute(prepend("omnivamp"), 0, 0, 16384F)
                    .setSyncable(true));

    /**
     * Chance to evade an incoming instance of damage.
     */
    public static final Holder<Attribute> DODGE_CHANCE = ATTRIBUTES.register("dodge_chance", () ->
            new PercentageAttribute(prepend("dodge_chance"), 0, 0, 1)
                    .setSyncable(true));

    /**
     * Flat armor value negation. Applied after {@link BrutalityAttributes#ARMOR_PENETRATION}.
     */
    public static final Holder<Attribute> LETHALITY = ATTRIBUTES.register("lethality", () ->
            new RangedAttribute(prepend("lethality"), 0, 0, 16384F)
                    .setSyncable(true));

    /**
     * Percentage of total armor ignored when attacking. Calculated before {@link BrutalityAttributes#LETHALITY}.
     */
    public static final Holder<Attribute> ARMOR_PENETRATION = ATTRIBUTES.register("armor_penetration", () ->
            new PercentageAttribute(prepend("armor_penetration"), 0, 0, 1)
                    .setSyncable(true));

    /**
     * Percentage reduction in detection radius for hostile mobs.
     */
    public static final Holder<Attribute> STEALTH = ATTRIBUTES.register("stealth", () ->
            new PercentageAttribute(prepend("stealth"), 0, 0, 1)
                    .setSyncable(true));

    /**
     * Raw multiplier for damage taken.
     */
    public static final Holder<Attribute> DAMAGE_TAKEN = ATTRIBUTES.register("damage_taken", () ->
            new PercentageAttribute(prepend("damage_taken"), 1, 0, 16384F)
                    .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * Raw multiplier of original coin cooldown
     */
    public static final Holder<Attribute> COIN_COOLDOWN = ATTRIBUTES.register("coin_cooldown", () ->
            new PercentageAttribute(prepend("coin_cooldown"), 1, 0.1F, 16384F)
                    .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * Raw multiplier for Gastronomy Damage dealt
     */
    public static final Holder<Attribute> GASTRONOMY_DAMAGE_DEALT = ATTRIBUTES.register("gastronomy_damage_dealt", () ->
            new PercentageAttribute(prepend("gastronomy_damage_dealt"), 0, 0, 1024F)
                    .setSyncable(true));

    /**
     * Raw multiplier for Gastronomy Wet Debuff Damage <span style="color: red">TAKEN</span, should be applied to the victim>
     */
    public static final Holder<Attribute> GASTRONOMY_WET_DEBUFF_DAMAGE_TAKEN = ATTRIBUTES.register("gastronomy_wet_debuff_damage_taken", () ->
            new PercentageAttribute(prepend("gastronomy_wet_debuff_damage_taken"), 0, 0, 1024F)
                    .setSentiment(Attribute.Sentiment.NEGATIVE));

    /**
     * Raw multiplier for Gastronomy Dry Debuff Damage <span style="color: red">TAKEN</span, should be applied to the victim>
     */
    public static final Holder<Attribute> GASTRONOMY_DRY_DEBUFF_DAMAGE_TAKEN = ATTRIBUTES.register("gastronomy_dry_debuff_damage_taken", () ->
            new PercentageAttribute(prepend("gastronomy_dry_debuff_damage_taken"), 0, 0, 1024F)
                    .setSentiment(Attribute.Sentiment.NEGATIVE));

    /**
     * Modifier on the final Gastronomy Debuff Level applied
     */
    public static final Holder<Attribute> GASTRONOMY_DEBUFF_LEVEL_MODIFIER = ATTRIBUTES.register("gastronomy_debuff_level_modifier", () ->
            new RangedAttribute(prepend("gastronomy_debuff_level_modifier"), 0, -1024F, 1024F));

    /**
     * Raw multiplier on the duration of Gastronomy Debuff inflicted
     */
    public static final Holder<Attribute> GASTRONOMY_DEBUFF_DURATION = ATTRIBUTES.register("gastronomy_debuff_duration", () ->
            new PercentageAttribute(prepend("gastronomy_debuff_duration"), 1, 0, 1024F));


}

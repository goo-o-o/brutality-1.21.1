package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import com.goo.brutality.util.Styles;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class BrutalityRarities {

    private static EnumProxy<Rarity> create(String name, UnaryOperator<Style> styleFunction) {
        return new EnumProxy<>(Rarity.class, -1, Brutality.MOD_ID + ":" + name, styleFunction);
    }

    /**
     * All rarities in their order of 'progression'
     * <ol>
     *     <li>Legendary</li>
     *     <li>Fabled</li>
     *     <li>Mythic</li>
     *     <li>Divine</li>
     *     <li>Cataclysmic</li>
     *     <li>Godly</li>
     * </ol>
     * <p>
     * Additional rarities for flavor
     * <ul>
     *     <li>Glacial</li>
     *     <li>Smoldering</li>
     *     <li>Gloomy</li>
     *     <li>Everdark</li>
     *     <li>Stygian</li>
     *     <li>Prismatic</li>
     *     <li>Blazing</li>
     *     <li>Encrypted</li>
     *     <li>Conductive</li>
     *     <li>Abyssal</li>
     *     <li>Coraline</li>
     * </ul>
     */

    public static EnumProxy<Rarity> LEGENDARY = create("legendary", style -> Styles.LEGENDARY_STYLE);
    public static EnumProxy<Rarity> FABLED = create("fabled", style -> Styles.FABLED_STYLE);
    public static EnumProxy<Rarity> MYTHIC = create("mythic", style -> Styles.MYTHIC_STYLE);
    public static EnumProxy<Rarity> DIVINE = create("divine", style -> Styles.DIVINE_STYLE);
    public static EnumProxy<Rarity> CATACLYSMIC = create("cataclysmic", style -> Styles.CATACLYSMIC_STYLE);
    public static EnumProxy<Rarity> GODLY = create("godly", style -> Styles.GODLY_STYLE);
    public static EnumProxy<Rarity> GLACIAL = create("glacial", style -> Styles.GLACIAL_STYLE);
    public static EnumProxy<Rarity> ENCRYPTED = create("encrypted", style -> Styles.ENCRYPTED_STYLE);
    public static EnumProxy<Rarity> CORALINE = create("coraline", style -> Styles.CORALINE_STYLE);
    public static EnumProxy<Rarity> SMOLDERING = create("smoldering", style -> Styles.SMOLDERING_STYLE);
    public static EnumProxy<Rarity> CONDUCTIVE = create("conductive", style -> Styles.CONDUCTIVE_STYLE);
    public static EnumProxy<Rarity> STYGIAN = create("stygian", style -> Styles.STYGIAN_STYLE);
    public static EnumProxy<Rarity> VOIDTOUCHED = create("voidtouched", style -> Styles.VOIDTOUCHED_STYLE);
    public static EnumProxy<Rarity> COSMIC = create("cosmic", style -> Styles.COSMIC_STYLE);
}

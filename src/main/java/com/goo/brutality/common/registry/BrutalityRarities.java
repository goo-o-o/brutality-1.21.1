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

    public static EnumProxy<Rarity> LEGENDARY = create("legendary", style -> Styles.Rarity.LEGENDARY);
    public static EnumProxy<Rarity> FABLED = create("fabled", style -> Styles.Rarity.FABLED);
    public static EnumProxy<Rarity> MYTHIC = create("mythic", style -> Styles.Rarity.MYTHIC);
    public static EnumProxy<Rarity> DIVINE = create("divine", style -> Styles.Rarity.DIVINE);
    public static EnumProxy<Rarity> CATACLYSMIC = create("cataclysmic", style -> Styles.Rarity.CATACLYSMIC);
    public static EnumProxy<Rarity> GODLY = create("godly", style -> Styles.Rarity.GODLY);
    public static EnumProxy<Rarity> GLACIAL = create("glacial", style -> Styles.Special.GLACIAL);
    public static EnumProxy<Rarity> ENCRYPTED = create("encrypted", style -> Styles.Special.ENCRYPTED);
    public static EnumProxy<Rarity> CORALINE = create("coraline", style -> Styles.Special.CORALINE);
    public static EnumProxy<Rarity> SMOLDERING = create("smoldering", style -> Styles.Special.SMOLDERING);
    public static EnumProxy<Rarity> CONDUCTIVE = create("conductive", style -> Styles.Special.CONDUCTIVE);
    public static EnumProxy<Rarity> STYGIAN = create("stygian", style -> Styles.Special.STYGIAN);
    public static EnumProxy<Rarity> VOIDTOUCHED = create("voidtouched", style -> Styles.Special.VOIDTOUCHED);
    public static EnumProxy<Rarity> COSMIC = create("cosmic", style -> Styles.Special.COSMIC);
}

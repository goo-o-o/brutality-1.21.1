package com.goo.brutality.util;

import net.minecraft.util.FastColor;

import java.awt.*;

public class Colors {
    public static final int DIVINE_IMMORTALS_RING_RED = FastColor.ARGB32.color(35, 6, 0);
    public static int BLACK = new Color(0, 0, 0).getRGB();
    public static int WHITE = new Color(255,255,255).getRGB();
    public static int TRANSPARENT = new Color(0, 0, 0,0).getRGB();
    public static int RED = new Color(255, 0, 0).getRGB();

    // godly
    public static Integer[] GODLY = new Integer[]{
            new Color(255, 90, 90).getRGB(),
            new Color(255, 180, 90).getRGB(),
            new Color(255, 255, 90).getRGB(),
            new Color(120, 255, 120).getRGB(),
            new Color(120, 255, 255).getRGB(),
            new Color(120, 120, 255).getRGB(),
            new Color(150, 100, 255).getRGB(),
            new Color(255, 100, 255).getRGB()
    };

    // dimlite
    public static int ELECTRIC_BLUE = new Color(129, 255, 248).getRGB();
    public static int AVERAGE_CYAN = new Color(41, 223, 235).getRGB();
    public static int MATTE_CYAN = new Color(20, 184, 191).getRGB();
    public static int GLOSSY_TEAL = new Color(0, 146, 149).getRGB();
    public static int BETTA_BLUE = new Color(14, 113, 135).getRGB();
    public static int NOBEL_TEAL = new Color(5, 98, 93).getRGB();
    public static int OCEANIC_TEAL = new Color(10, 80, 96).getRGB();
    public static int DAINTREE = new Color(5, 42, 50).getRGB();
    public static int BUNKER = new Color(13, 18, 23).getRGB();

    // hellspec
    public static int INFERNO = new Color(255, 78, 53).getRGB();   // #ff4e35
    public static int BRIGHT_RED = new Color(255, 25, 0).getRGB();    // #ff1900
    public static int ARABIC_RED = new Color(162, 18, 0).getRGB();    // #a21200
    public static int BARN_RED = new Color(119, 9, 0).getRGB();     // #770900
    public static int EXTREME_MAROON = new Color(56, 6, 0).getRGB();      // #380600
    public static int DIESEL = new Color(17, 1, 0).getRGB();      // #110100

    // colors for encrypted
    public static int SPRING_BUD = new Color(169, 255, 0).getRGB();
    public static int PHTHALO_GREEN = new Color(4, 62, 12).getRGB();
    public static int ERIN = new Color(0, 234, 42).getRGB();
    public static int DARK_MOSS_GREEN = new Color(8, 33, 13).getRGB();

    // smoldering
    public static int BRIGHT_YELLOW = new Color(255, 237, 43).getRGB();
    public static int ORANGE = new Color(255, 124, 0).getRGB();
    public static int GRENADIER = new Color(224, 59, 0).getRGB(); // belongs to rage too
    // rage
    public static int RUSSIAN_RED = new Color(208, 1, 0).getRGB();


    // akuma
    public static int TOLEDO = new Color(53, 6, 31).getRGB();
    public static int DEEP_CARMINE_PINK = new Color(125, 13, 55).getRGB();
    public static int VALENTINE = new Color(238, 45, 83).getRGB();


}

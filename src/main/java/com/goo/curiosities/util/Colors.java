package com.goo.curiosities.util;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Colors {

    public static int BLACK = new Color(0, 0, 0).getRGB();
    public static int WHITE = new Color(255, 255, 255).getRGB();
    public static int GRAY = new Color(150, 150, 150).getRGB();
    public static int TRANSPARENT = new Color(0, 0, 0, 0).getRGB();
    public static int RED = new Color(255, 0, 0).getRGB();

    public static int[] RAINBOW = new int[]{
            new Color(255, 90, 90).getRGB(),
            new Color(255, 180, 90).getRGB(),
            new Color(255, 255, 90).getRGB(),
            new Color(120, 255, 120).getRGB(),
            new Color(120, 255, 255).getRGB(),
            new Color(120, 120, 255).getRGB(),
            new Color(150, 100, 255).getRGB(),
            new Color(255, 100, 255).getRGB()
    };
    public static List<Integer> RAINBOW_LIST = Arrays.stream(RAINBOW).boxed().toList();

    public static int[] SCULK = new int[]{
            new Color(129, 255, 248).getRGB(),
            new Color(41, 223, 235).getRGB(),
            new Color(20, 184, 191).getRGB(),
            new Color(0, 146, 149).getRGB(),
            new Color(14, 113, 135).getRGB(),
            new Color(5, 98, 93).getRGB(),
            new Color(10, 80, 96).getRGB(),
            new Color(5, 42, 50).getRGB(),
            new Color(13, 18, 23).getRGB()
    };

    public static int[] NETHER = new int[]{
            new Color(255, 78, 53).getRGB(),
            new Color(255, 25, 0).getRGB(),
            new Color(162, 18, 0).getRGB(),
            new Color(119, 9, 0).getRGB(),
            new Color(56, 6, 0).getRGB(),
            new Color(17, 1, 0).getRGB()
    };

    public static int[] MATRIX = new int[]{
            new Color(169, 255, 0).getRGB(),
            new Color(4, 62, 12).getRGB(),
            new Color(0, 234, 42).getRGB(),
            new Color(8, 33, 13).getRGB()
    };

    public static List<Integer> MATRIX_LIST = Arrays.stream(MATRIX).boxed().toList();

    public static int[] FIRE = new int[]{
            new Color(255, 237, 43).getRGB(),
            new Color(255, 124, 0).getRGB(),
            new Color(224, 59, 0).getRGB()
    };

    public static List<Integer> FIRE_LIST = Arrays.stream(FIRE).boxed().toList();

    public static int[] GOLD_INGOT = new int[]{
            new Color(253, 245, 95).getRGB(),
            new Color(250, 214, 74).getRGB(),
            new Color(233, 177, 21).getRGB(),
            new Color(220, 150, 19).getRGB(),
            new Color(178, 100, 17).getRGB(),
            new Color(149, 72, 10).getRGB(),
            new Color(117, 40, 2).getRGB()
    };
    public static List<Integer> GOLD_INGOT_LIST = Arrays.stream(GOLD_INGOT).boxed().toList();

    public static int[] ENDER = new int[]{
            new Color(228, 114, 248).getRGB(),
            new Color(176, 52, 204).getRGB(),
            new Color(112, 31, 142).getRGB(),
            new Color(70, 24, 98).getRGB(),
            new Color(38, 16, 56).getRGB(),
            new Color(22, 13, 33).getRGB(),
            new Color(11, 8, 18).getRGB(),
    };

    public static List<Integer> ENDER_LIST = Arrays.stream(ENDER).boxed().toList();

    public static int[] SOUL = new int[]{
            new Color(153, 255, 253).getRGB(),
            new Color(78, 231, 237).getRGB(),
            new Color(41, 182, 211).getRGB(),
            new Color(25, 128, 161).getRGB(),
            new Color(19, 83, 112).getRGB(),
            new Color(14, 52, 77).getRGB(),
            new Color(8, 27, 46).getRGB()
    };
    public static List<Integer> SOUL_LIST = Arrays.stream(SOUL).boxed().toList();

    public static int[] ICE = new int[]{
            new Color(255, 255, 255).getRGB(),
            new Color(205, 249, 255).getRGB(),
            new Color(150, 217, 246).getRGB(),
            new Color(126, 156, 199).getRGB(),
            new Color(97, 106, 151).getRGB()
    };

    public static List<Integer> ICE_LIST = Arrays.stream(ICE).boxed().toList();
}

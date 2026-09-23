package com.goo.curiosities.common.registry;

import com.goo.curiosities.common.Curiosities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriositiesTags {

    public static class Items {


        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> ANKLET = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "anklet"));
        public static final TagKey<Item> HEART = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "heart"));
        public static final TagKey<Item> FEET = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "feet"));

        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> WATER_TOOLTIP = tag("water_tooltip");
        public static final TagKey<Item> RAINBOW_TOOLTIP = tag("rainbow_tooltip");
        public static final TagKey<Item> FIRE_TOOLTIP = tag("fire_tooltip");
        public static final TagKey<Item> MATRIX_TOOLTIP = tag("matrix_tooltip");
        public static final TagKey<Item> SNOW_TOOLTIP = tag("snow_tooltip");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(Curiosities.loc(name));
        }

    }
}

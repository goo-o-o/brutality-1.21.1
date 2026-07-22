package com.goo.brutality.common.registry;

import com.goo.brutality.common.Brutality;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class BrutalityTags {

    public static class Items {

        public static final TagKey<Item> GASTRONOMIST_ITEMS = tag("gastronomist_items");

        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> MAGIC_ITEMS = tag("magic_items");
        public static final TagKey<Item> MAGIC_TOMES = tag("magic_tomes");
        public static final TagKey<Item> SPELL_SCROLLS = tag("spell_scrolls");

        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> AUGMENTS = tag("augments");
        public static final TagKey<Item> MAGIC_AUGMENTS = tag("magic_augments");
        public static final TagKey<Item> SEALS = tag("seals");
        public static final TagKey<Item> AUGMENTATION_DEVICE = tag("augmentation_device");

        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> MATH_ITEMS = tag("math_items");

        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> RAGE_ITEMS = tag("rage_items");

        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> ANKLET = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "anklet"));
        public static final TagKey<Item> HEART = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "heart"));
        public static final TagKey<Item> FEET = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "feet"));
        public static final TagKey<Item> FUNCTION = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "function"));

        // ─────────────────────────────────────────────────────────────────────────────

        public static final TagKey<Item> TWO_HANDED = tag("two_handed");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Brutality.MOD_ID, name));
        }

    }
}

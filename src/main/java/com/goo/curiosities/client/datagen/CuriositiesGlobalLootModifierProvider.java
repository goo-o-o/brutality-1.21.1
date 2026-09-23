package com.goo.curiosities.client.datagen;

import com.goo.curiosities.common.registry.CuriositiesItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CuriositiesGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public static final List<LootEntry> LOOT_ENTRIES = new ArrayList<>();

    public CuriositiesGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(output, registries, modid);
    }

    public record LootEntry(
            ResourceLocation lootTable,
            Holder<Item> itemHolder,
            float chance
    ) {
        public String getName() {
            // Replaces colons in registered names (e.g. "curiosities:brain_rot") to keep IDs valid
            String itemName = itemHolder.getRegisteredName().replace(":", "_");
            String pathName = lootTable.getPath().replace("/", "_");
            return itemName + "_from_" + pathName;
        }
    }

    public static void bootstrap() {
        LOOT_ENTRIES.clear();

        // Chest & World Loot
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.END_CITY_TREASURE.location(), CuriositiesItems.OMNIDIRECTIONAL_MOVEMENT_GEAR, 0.25F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.SHIPWRECK_TREASURE.location(), CuriositiesItems.ABYSSAL_NECKLACE, 0.25F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.FISHING_TREASURE.location(), CuriositiesItems.AMPHIBIAN_BOOTS, 0.05F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.SIMPLE_DUNGEON.location(), CuriositiesItems.PHANTOM_FINGER, 0.05F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_RARE.location(), CuriositiesItems.CROWN_OF_TYRANNY, 0.075F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.JUNGLE_TEMPLE.location(), CuriositiesItems.BEAD_OF_LIFE, 0.25F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.PIGLIN_BARTERING.location(), CuriositiesItems.BIG_STEPPA, 0.05F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.NETHER_BRIDGE.location(), CuriositiesItems.BLAZE_ANKLET, 0.05F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.IGLOO_CHEST.location(), CuriositiesItems.BLIZZARD_IN_A_BOTTLE, 0.5F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.JUNGLE_TEMPLE.location(), CuriositiesItems.CLIMBING_CLAWS, 0.5F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.IGLOO_CHEST.location(), CuriositiesItems.CLOAK_OF_TRUE_ICE, 0.5F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.ANCIENT_CITY.location(), CuriositiesItems.COBALT_SHIELD, 0.2F));
        LOOT_ENTRIES.add(new LootEntry(BuiltInLootTables.WOODLAND_MANSION.location(), CuriositiesItems.GLADIATORS_ANKLET, 0.05F));

        // Entity Loot
        LOOT_ENTRIES.add(new LootEntry(EntityType.ZOMBIE.getDefaultLootTable().location(), CuriositiesItems.BRAIN_ROT, 0.02F));
    }

    @Override
    protected void start() {
        bootstrap();

        for (LootEntry entry : LOOT_ENTRIES) {
            this.add(
                    entry.getName(),
                    new AddItemLootModifier(
                            new LootItemCondition[]{
                                    LootTableIdCondition.builder(entry.lootTable()).build()
                            },
                            entry.itemHolder().value(),
                            entry.chance()
                    )
            );
        }
    }
}
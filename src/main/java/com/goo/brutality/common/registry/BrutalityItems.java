package com.goo.brutality.common.registry;

import com.goo.brutality.client.registry.BrutalityKeyMappings;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.explosion.BloodExplosionDamageCalculator;
import com.goo.brutality.common.items.BrutalityCurioItem;
import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.items.curio.belt.BloodboilBomb;
import com.goo.brutality.common.items.curio.belt.GammaSerum;
import com.goo.brutality.common.items.curio.belt.SurtrsHorn;
import com.goo.brutality.common.items.curio.charm.*;
import com.goo.brutality.common.items.curio.feet.FlippersOfIcarus;
import com.goo.brutality.common.items.curio.hand.BloodPulseKnuckles;
import com.goo.brutality.common.items.curio.hand.OmegaGauntlet;
import com.goo.brutality.common.items.curio.head.SwallowedPenny;
import com.goo.brutality.common.items.curio.heart.HeartOfTheHoarder;
import com.goo.brutality.common.items.curio.necklace.DemonBeads;
import com.goo.brutality.common.items.curio.necklace.ToxicosisPendant;
import com.goo.brutality.common.items.curio.ring.OmnichromeRing;
import com.goo.brutality.common.items.curio.ring.RingOfDespair;
import com.goo.brutality.common.items.curio.ring.RingOfRings;
import com.goo.brutality.common.items.light_greatsword.BladeOfTheRuinedKing;
import com.goo.brutality.common.items.light_greatsword.PlinkoBlade;
import com.goo.brutality.common.tooltip.DescriptionType;
import com.goo.brutality.common.tooltip.ItemDescriptions;
import com.goo.brutality.util.AttributeUtil;
import com.goo.brutality.util.SideHelper;
import com.goo.brutality.util.Styles;
import com.goo.goo_lib.common.attribute.AttributeContainer;
import com.goo.goo_lib.common.registry.GLAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static com.goo.brutality.util.TooltipUtil.*;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.*;

public class BrutalityItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Brutality.MOD_ID);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        // for force loading since nested classes are lazy
        Curio.init();
        Curio.Coin.init();
        Curio.Rage.init();

        Weapon.init();
        Weapon.LightGreatsword.init();
    }

    public static class Curio {
        private static void init() {
        }

        public static class Coin {
            private static void init() {
            } // used to force class loading

            public static final Holder<Item> OVERDRAW_POUCH = ITEMS.register("overdraw_pouch",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.COSMIC.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("overdraw_pouch")
                                            .add(DescriptionType.LORE, 1)
                                            .add(DescriptionType.PASSIVE, 2)
                                            .build()
                            ))
            );
            public static final Holder<Item> NUMISMATIC_CATALYST = ITEMS.register("numismatic_catalyst",
                    () -> new NumismaticCatalyst(new Item.Properties().rarity(BrutalityRarities.VOIDTOUCHED.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("numismatic_catalyst")
                                            .add(DescriptionType.PASSIVE, 1)
                                            .build()
                            ))
            );

            public static final Holder<Item> HEART_OF_THE_HOARDER = ITEMS.register("heart_of_the_hoarder",
                    () -> new HeartOfTheHoarder(new Item.Properties().rarity(BrutalityRarities.STYGIAN.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("heart_of_the_hoarder")
                                            .add(DescriptionType.PASSIVE, 1)
                                            .build()
                            ))
            );

            public static final Holder<Item> THE_GLUTTONS_PURSE = ITEMS.register("the_gluttons_purse",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("the_gluttons_purse")
                                            .add(DescriptionType.PASSIVE, 1)
                                            .build()
                            ))
            );

            public static final Holder<Item> MIRRORED_MINT = ITEMS.register("mirrored_mint",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.CORALINE.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("mirrored_mint")
                                            .add(DescriptionType.PASSIVE, 1)
                                            .build()
                            ))
            );

            public static final Holder<Item> MOBIUS_STRIP = ITEMS.register("mobius_strip",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.CONDUCTIVE.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("mobius_strip")
                                            .add(DescriptionType.LORE, 1)
                                            .add(DescriptionType.PASSIVE, 1)
                                            .build()
                            ))
            );

            public static final Holder<Item> REVERSE_COIN = ITEMS.register("reverse_coin",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.ENCRYPTED.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("reverse_coin")
                                            .add(DescriptionType.PASSIVE, 1)
                                            .build()
                            ))
            );

            public static final Holder<Item> SWALLOWED_PENNY = ITEMS.register("swallowed_penny",
                    () -> new SwallowedPenny(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("swallowed_penny")
                                            .add(DescriptionType.PASSIVE, 2, 3)
                                            .build()
                            ))
            );

            public static final Holder<Item> GLOVE_OF_GREED = ITEMS.register("glove_of_greed",
                    () -> new BrutalityCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("glove_of_greed")
                                            .add(DescriptionType.ACTIVE, 2, 4, SideHelper.shieldFromServer(() -> BrutalityKeyMappings.ACTIVE_ABILITY::get))
                                            .build()
                            ))
            );

            public static final Holder<Item> HAND_OF_MIDAS = ITEMS.register("hand_of_midas",
                    () -> new BrutalityCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("hand_of_midas")
                                            .add(DescriptionType.LORE, 1)
                                            .add(DescriptionType.ACTIVE, 1, 4, SideHelper.shieldFromServer(() -> BrutalityKeyMappings.ACTIVE_ABILITY::get))
                                            .build()
                            ))
            );

            public static final Holder<Item> MINT_MASTERS_SIGNET = ITEMS.register("mint_masters_signet",
                    () -> new BrutalityCurioItem(new Item.Properties()).withAttributes(
                            new AttributeContainer(BrutalityAttributes.COIN_COOLDOWN, -0.1, ADD_VALUE)
                    ));
        } // ──────────────────────────────────────────────────────────────────────────

        public static class Rage {
            private static void init() {
            }

            public static final Holder<Item> OMEGA_GAUNTLET = ITEMS.register("omega_gauntlet",
                    () -> new OmegaGauntlet(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("omega_gauntlet")
                                            .add(DescriptionType.ON_HIT, 1,
                                                    () -> List.of(
                                                            attribute(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO),
                                                            percentage(1000),
                                                            effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)
                                                    ))
                                            .build()
                            )).withAttributes(
                            new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 1.5, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.05, ADD_VALUE)
                    )
            );


            public static final Holder<Item> GAMMA_SERUM = ITEMS.register("gamma_serum",
                    () -> new GammaSerum(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("gamma_serum")
                                            .add(DescriptionType.PASSIVE, 1,
                                                    () -> List.of(
                                                            attribute(Attributes.SCALE, 0.5, ADD_MULTIPLIED_TOTAL),
                                                            effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)
                                                    ))
                                            .build()
                            ))
            );

            public static final Holder<Item> STRESS_PILLS = ITEMS.register("stress_pills",
                    () -> new StressPills(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("stress_pills")
                                            .add(DescriptionType.ACTIVE, 1, 40, SideHelper.shieldFromServer(() -> BrutalityKeyMappings.ACTIVE_ABILITY::get),
                                                    () -> List.of(
                                                            RAGE, percentage(50), attribute(BrutalityAttributes.MAX_RAGE)
                                                    ))
                                            .build()
                            ))
            );
            public static final Holder<Item> SEROTONIN_PILLS = ITEMS.register("serotonin_pills",
                    () -> new SerotoninPills(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("serotonin_pills")
                                            .add(DescriptionType.ACTIVE, 3, 40, SideHelper.shieldFromServer(() -> BrutalityKeyMappings.ACTIVE_ABILITY::get),
                                                    () -> List.of(
                                                            effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE),
                                                            RAGE,
                                                            effectWithStyle(BrutalityEffects.TRANQUILITY, 5, Styles.Special.VOIDTOUCHED)
                                                    ))
                                            .build()
                            ))
            );

            public static final Holder<Item> ENDER_DRAGON_STEM_CELLS = ITEMS.register("ender_dragon_stem_cells",
                    () -> new BrutalityRageCurioItem(new Item.Properties()).withAttributes(
                            new AttributeContainer(BrutalityAttributes.MAX_RAGE, 75, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 0.25, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.SCALE, 0.5, ADD_MULTIPLIED_TOTAL)
                    ));

            public static final Holder<Item> WRATH = ITEMS.register("wrath",
                    () -> new Wrath(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("wrath")
                                            .add(DescriptionType.PASSIVE, 1, () ->
                                                    List.of(
                                                            Component.translatable("tooltip." + Brutality.MOD_ID + ".hexing_circle").withStyle(Styles.Special.RAGE),
                                                            attribute(BrutalityAttributes.MAX_RAGE),
                                                            attribute(BrutalityAttributes.ENRAGED_LEVEL)
                                                    )
                                            )
                                            .add(DescriptionType.UPON_TRIGGERING_RAGE, 1, () ->
                                                    List.of(
                                                            Component.translatable("tooltip." + Brutality.MOD_ID + ".hexing_circle").withStyle(Styles.Special.RAGE),
                                                            DAMAGE,
                                                            ENTITIES
                                                    )
                                            )
                                            .build()
                            )).withAttributes(
                            new AttributeContainer(BrutalityAttributes.MAX_RAGE, 50, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 0.1, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.1, ADD_MULTIPLIED_TOTAL)
                    ));


            public static final Holder<Item> FURY_BAND = ITEMS.register("fury_band",
                    () -> new BrutalityRageCurioItem(new Item.Properties()).withAttributes(
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 0.75, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_LEVEL, 1, ADD_VALUE)
                    ));

            public static final Holder<Item> GRUDGE_TOTEM = ITEMS.register("grudge_totem",
                    () -> new GrudgeTotem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("grudge_totem")
                                            .add(DescriptionType.PASSIVE, 2, () ->
                                                    List.of(
                                                            WEARER,
                                                            FATAL,
                                                            DAMAGE,
                                                            effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE),
                                                            RAGE
                                                    )
                                            )
                                            .build()
                            ))
            );

            public static final Holder<Item> ANGER_MANAGEMENT = ITEMS.register("anger_management",
                    () -> new BrutalityRageCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("anger_management")
                                            .add(DescriptionType.PASSIVE, 1, () -> List.of(WEARER, RAGE))
                                            .add(DescriptionType.ACTIVE, 1, SideHelper.shieldFromServer(() -> BrutalityKeyMappings.ACTIVATE_RAGE::get),
                                                    () -> List.of(
                                                            WEARER, RAGE, RAGE))
                                            .build()
                            ))
            );

            public static final Holder<Item> BROKEN_CONTROLLER = ITEMS.register("broken_controller",
                    () -> new BrutalityRageCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("broken_controller")
                                            .add(DescriptionType.LORE, 1)
                                            .add(DescriptionType.PASSIVE, 1)
                                            .build())
                    ).withAttributes(
                            new AttributeContainer(BrutalityAttributes.ENRAGED_LEVEL, 4, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 2, ADD_VALUE)
                    )
            );

            public static final Holder<Item> WIRELESS_CONTROLLER = ITEMS.register("wireless_controller",
                    () -> new BrutalityRageCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("wireless_controller")
                                            .add(DescriptionType.LORE, 1)
                                            .build())
                    ).withAttributes(
                            new AttributeContainer(BrutalityAttributes.ENRAGED_LEVEL, 4, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 2, ADD_VALUE)
                    )
            );


            public static final Holder<Item> RAGE_STONE = ITEMS.register("rage_stone",
                    () -> new BrutalityRageCurioItem(new Item.Properties())
                            .withAttributes(
                                    new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.5, ADD_MULTIPLIED_TOTAL),
                                    new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.3, ADD_MULTIPLIED_TOTAL)
                            )
            );

            public static final Holder<Item> PACK_OF_CIGARETTES = ITEMS.register("pack_of_cigarettes",
                    () -> new BrutalityRageCurioItem(new Item.Properties())
                            .withAttributes(
                                    new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, -0.5, ADD_MULTIPLIED_BASE),
                                    new AttributeContainer(BrutalityAttributes.DAMAGE_TAKEN, -0.25, ADD_VALUE)
                            )
            );

            public static final Holder<Item> BLOODBOIL_BOMB = ITEMS.register("bloodboil_bomb",
                    () -> new BloodboilBomb(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("bloodboil_bomb")
                                            .add(DescriptionType.PASSIVE, 2,
                                                    () -> List.of(
                                                            BloodExplosionDamageCalculator.BLOOD_EXPLOSION,
                                                            attribute(BrutalityAttributes.ENRAGED_LEVEL),
                                                            attribute(BrutalityAttributes.MAX_RAGE),
                                                            BloodExplosionDamageCalculator.BLOOD_EXPLOSION,
                                                            percentage(10),
                                                            DAMAGE
                                                    ))
                                            .add(DescriptionType.UPON_TRIGGERING_RAGE, 1,
                                                    () -> List.of(
                                                            BloodExplosionDamageCalculator.BLOOD_EXPLOSION
                                                    ))
                                            .build()
                            ))
                            .withAttributes(
                                    new AttributeContainer(BrutalityAttributes.MAX_RAGE, 50, ADD_VALUE)
                            )
            );

            public static final Holder<Item> BLOOD_PULSE_KNUCKLES = ITEMS.register("blood_pulse_knuckles",
                    () -> new BloodPulseKnuckles(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("blood_pulse_knuckles")
                                            .add(DescriptionType.ON_HIT, 2,
                                                    () -> List.of(
                                                            BloodExplosionDamageCalculator.BLOOD_EXPLOSION,
                                                            effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE),
                                                            BloodExplosionDamageCalculator.BLOOD_EXPLOSION,
                                                            percentage(10),
                                                            DAMAGE
                                                    ))
                                            .build()
                            ))
                            .withAttributes(
                                    new AttributeContainer(BrutalityAttributes.MAX_RAGE, 50, ADD_VALUE),
                                    new AttributeContainer(Attributes.ATTACK_SPEED, 0.15, ADD_MULTIPLIED_TOTAL),
                                    new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 0.15, ADD_MULTIPLIED_TOTAL)
                            )
            );

            public static final Holder<Item> MECHANICAL_AORTA = ITEMS.register("mechanical_aorta",
                    () -> new MechanicalAorta(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("mechanical_aorta")
                                            .add(DescriptionType.LORE, 1)
                                            .add(DescriptionType.PASSIVE, 1,
                                                    () -> List.of(
                                                            WEARER, DAMAGE, effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)
                                                    ))
                                            .build()
                            )).withAttributes(
                            new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.35, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 0.15, ADD_MULTIPLIED_TOTAL)
                    )
            );

            public static final Holder<Item> TOXICOSIS_PENDANT = ITEMS.register("toxicosis_pendant",
                    () -> new ToxicosisPendant(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("toxicosis_pendant")
                                            .add(DescriptionType.PASSIVE, 1,
                                                    () -> List.of(
                                                            attribute(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.15, ADD_VALUE),
                                                            DEBUFF
                                                    ))
                                            .build()
                            ))
            );


            public static final Holder<Item> MONOCLE_OF_BRUTALITY = ITEMS.register("monocle_of_brutality",
                    () -> new BrutalityRageCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("monocle_of_brutality")
                                            .add(DescriptionType.PASSIVE, 1,
                                                    () -> List.of(
                                                            WEARER,
                                                            ENTITIES,
                                                            effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)
                                                    ))
                                            .build()
                            ))
            );

            public static final Holder<Item> DEMON_BEADS = ITEMS.register("demon_beads",
                    () -> new DemonBeads(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("demon_beads")
                                            .add(DescriptionType.UPON_TRIGGERING_RAGE, 1,
                                                    () -> List.of(
                                                            effectWithStyle(BrutalityEffects.ASURA_FORM, Styles.Special.RAGE),
                                                            attribute(BrutalityAttributes.ENRAGED_DURATION)
                                                    ))
                                            .build()
                            ))
            );


        } // ──────────────────────────────────────────────────────────────────────────

        public static final Holder<Item> XRAY_GOGGLES = ITEMS.register("xray_goggles",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.ENCRYPTED.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("xray_goggles")
                                        .add(DescriptionType.PASSIVE, 1,
                                                () -> List.of(
                                                        WEARER, ENTITIES
                                                ))
                                        .build()
                        ))
        );


        public static final Holder<Item> OMNICHROME_RING = ITEMS.register("omnichrome_ring",
                () -> new OmnichromeRing(new Item.Properties().rarity(BrutalityRarities.GODLY.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("omnichrome_ring")
                                        .add(DescriptionType.PASSIVE, 5,
                                                () -> List.of(
                                                        effectWithStyle(MobEffects.FIRE_RESISTANCE, Styles.Elements.FIRE),
                                                        effectWithStyle(MobEffects.WATER_BREATHING, Styles.Elements.WATER),
                                                        effectWithStyle(MobEffects.DOLPHINS_GRACE, Styles.Elements.WATER),
                                                        effectWithStyle(MobEffects.SLOW_FALLING, Styles.Elements.WIND),
                                                        effectWithStyle(MobEffects.POISON, Styles.PotionEffects.POISON),
                                                        WEARER, entityWithStyle(EntityType.PIGLIN, Styles.Rarity.LEGENDARY),
                                                        WEARER, entityWithStyle(EntityType.ENDERMAN, Styles.Special.COSMIC),
                                                        WEARER, blockWithStyle(Blocks.POWDER_SNOW, Styles.Special.GLACIAL)
                                                ))
                                        .build()
                        ))
        );


        public static final Holder<Item> RING_OF_RINGS = ITEMS.register("ring_of_rings",
                () -> new RingOfRings(new Item.Properties()
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("ring_of_rings")
                                        .add(DescriptionType.LORE, 1)
                                        .add(DescriptionType.PASSIVE, 1,
                                                () -> List.of(
                                                        attribute(Attributes.ARMOR, 1, ADD_VALUE),
                                                        attribute(BrutalityAttributes.LETHALITY, 1, ADD_VALUE)
                                                ))
                                        .build()
                        ))
        );

        public static final Holder<Item> RING_OF_DESPAIR = ITEMS.register("ring_of_despair",
                () -> new RingOfDespair(new Item.Properties()
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("ring_of_despair")
                                        .add(DescriptionType.LORE, 1)
                                        .add(DescriptionType.ACTIVE, 1, 40,
                                                SideHelper.shieldFromServer(() -> BrutalityKeyMappings.ACTIVE_ABILITY::get),
                                                () -> List.of(
                                                        effectWithStyle(BrutalityEffects.DESPAIR, 5, Styles.PotionEffects.WITHER),
                                                        ENTITIES
                                                ))
                                        .build()
                        ))
        );


        public static final Holder<Item> FLIPPERS_OF_ICARUS = ITEMS.register("flippers_of_icarus",
                () -> new FlippersOfIcarus(new Item.Properties().rarity(BrutalityRarities.GODLY.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("flippers_of_icarus")
                                        .add(DescriptionType.PASSIVE, 2,
                                                () -> List.of(
                                                        WEARER,
                                                        blockWithStyle(Blocks.AIR, Styles.Elements.WIND),
                                                        blockWithStyle(Blocks.WATER, Styles.Elements.WATER),
                                                        WEARER,
                                                        blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE)
                                                ))
                                        .build()
                        ))
        );


        public static final Holder<Item> POSEIDONS_BLESSING = ITEMS.register("poseidons_blessing",
                () -> new PoseidonsBlessing(new Item.Properties().rarity(BrutalityRarities.CORALINE.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("poseidons_blessing")
                                        .add(DescriptionType.PASSIVE, 2,
                                                () -> List.of(
                                                        blockWithStyle(Blocks.WATER, Styles.Elements.WATER),
                                                        effectWithStyle(MobEffects.WATER_BREATHING, Styles.Elements.WATER),
                                                        effectWithStyle(MobEffects.CONDUIT_POWER, Styles.Special.CORALINE)))
                                        .build()
                        ))
        );

        public static final Holder<Item> HYDROPHOBIC_NANOCOATING = ITEMS.register("hydrophobic_nanocoating",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.VOIDTOUCHED.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("hydrophobic_nanocoating")
                                        .add(DescriptionType.PASSIVE, 1,
                                                () -> List.of(
                                                        blockWithStyle(Blocks.WATER, Styles.Elements.WATER)))
                                        .build()
                        ))
        );

        public static final Holder<Item> LAVA_LENSES = ITEMS.register("lava_lenses",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("lava_lenses")
                                        .add(DescriptionType.PASSIVE, 2,
                                                () -> List.of(
                                                        WEARER,
                                                        blockWithStyle(Blocks.LAVA, Styles.Elements.FIRE),
                                                        WEARER,
                                                        blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE)))
                                        .build()
                        ))
        );

        public static final Holder<Item> SURTRS_HORN = ITEMS.register("surtrs_horn",
                () -> new SurtrsHorn(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("surtrs_horn")
                                        .add(DescriptionType.PASSIVE, 5,
                                                () -> List.of(
                                                        blockWithStyle(Blocks.LAVA, Styles.Elements.FIRE),
                                                        effectWithStyle(MobEffects.FIRE_RESISTANCE, Styles.Elements.FIRE),
                                                        WEARER,
                                                        blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE),
                                                        WEARER,
                                                        blockWithStyle(Blocks.LAVA, Styles.Elements.FIRE),
                                                        WEARER,
                                                        blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE)
                                                ))
                                        .build()
                        ))
        );

        public static final Holder<Item> PORTABLE_FIRE_EXTINGUISHER = ITEMS.register("portable_fire_extinguisher",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.builder("portable_fire_extinguisher")
                                        .add(DescriptionType.LORE, 1).build()
                        ))
                        .withAttributes(new AttributeContainer(Attributes.BURNING_TIME, -0.5, ADD_MULTIPLIED_TOTAL))
        );


    }

    public static class Weapon {
        // TODO: Make all tiers match Terramity equivalents when available
        public static void init() {
        }

        public static class LightGreatsword {
            public static void init() {
            }

            public static final Holder<Item> PLINKO_BLADE = ITEMS.register("plinko_blade",
                    () -> new PlinkoBlade(Tiers.NETHERITE, new Item.Properties()
                            .attributes(SwordItem.createAttributes(Tiers.NETHERITE, -1, -3.25F))
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("plinko_blade")
                                            .add(DescriptionType.LORE, 1)
                                            .add(DescriptionType.ON_HIT, 1,
                                                    () -> List.of(
                                                            DAMAGE
                                                    ))
                                            .build()
                            ))
            );

            public static final Holder<Item> BLADE_OF_THE_RUINED_KING = ITEMS.register("blade_of_the_ruined_king",
                    () -> new BladeOfTheRuinedKing(Tiers.NETHERITE, new Item.Properties()
                            .attributes(SwordItem.createAttributes(Tiers.NETHERITE, 2, -3.1F)
                                    .withModifierAdded(
                                            GLAttributes.LIFESTEAL,
                                            AttributeUtil.getModifier(GLAttributes.LIFESTEAL, 0.1, AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.HAND))
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.builder("blade_of_the_ruined_king")
                                            .add(DescriptionType.LORE, 1)
                                            .add(DescriptionType.ON_KILL, 1,
                                                    () -> List.of(
                                                            BladeOfTheRuinedKing.MIST_WRAITH
                                                    ))
                                            .add(DescriptionType.PASSIVE, 3,
                                                    () -> List.of(
                                                            BladeOfTheRuinedKing.MIST_WRAITH,
                                                            effectWithStyle(BrutalityEffects.RUINED, Styles.BasicColors.CYAN),
                                                            USER,
                                                            BladeOfTheRuinedKing.MIST_WRAITH,
                                                            BladeOfTheRuinedKing.MIST_WRAITH,
                                                            USER
                                                    ))
                                            .add(DescriptionType.ON_HIT, 3,
                                                    () -> List.of(
                                                            BONUS,
                                                            DAMAGE,
                                                            percentage(10),
                                                            VICTIM,
                                                            CURRENT_HEALTH,
                                                            effectWithStyle(MobEffects.MOVEMENT_SLOWDOWN, Styles.BasicColors.LIGHT_GRAY),
                                                            effectWithStyle(MobEffects.MOVEMENT_SPEED, Styles.PotionEffects.SWIFTNESS)
                                                    )
                                            )
                                            .build()
                            ))
            );
        }
    }

}

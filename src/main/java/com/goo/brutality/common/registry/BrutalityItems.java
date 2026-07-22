package com.goo.brutality.common.registry;

import com.goo.brutality.client.registry.BrutalityKeyNames;
import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.explosion.BloodExplosionDamageCalculator;
import com.goo.brutality.common.item.BrutalityCurioItem;
import com.goo.brutality.common.item.BrutalityFunctionCurioItem;
import com.goo.brutality.common.item.BrutalityRageCurioItem;
import com.goo.brutality.common.item.curio.belt.BloodboilBomb;
import com.goo.brutality.common.item.curio.belt.GammaSerum;
import com.goo.brutality.common.item.curio.belt.SurtrsHorn;
import com.goo.brutality.common.item.curio.charm.*;
import com.goo.brutality.common.item.curio.feet.FlippersOfIcarus;
import com.goo.brutality.common.item.curio.function.*;
import com.goo.brutality.common.item.curio.hand.*;
import com.goo.brutality.common.item.curio.head.SwallowedPenny;
import com.goo.brutality.common.item.curio.heart.HeartOfDarkness;
import com.goo.brutality.common.item.curio.heart.HeartOfTheHoarder;
import com.goo.brutality.common.item.curio.necklace.DemonBeads;
import com.goo.brutality.common.item.curio.necklace.ToxicosisPendant;
import com.goo.brutality.common.item.curio.ring.OmnichromeRing;
import com.goo.brutality.common.item.curio.ring.RingOfDespair;
import com.goo.brutality.common.item.curio.ring.RingOfRings;
import com.goo.brutality.common.item.curio.ring.DivineImmortalsRing;
import com.goo.brutality.common.item.light_greatsword.BladeOfTheRuinedKing;
import com.goo.brutality.common.item.light_greatsword.PlinkoBlade;
import com.goo.brutality.common.tooltip.DescriptionType;
import com.goo.brutality.common.tooltip.ItemDescriptions;
import com.goo.brutality.util.AttributeUtil;
import com.goo.brutality.util.Styles;
import com.goo.brutality.util.TooltipUtil;
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
        Curio.init();
        Curio.Math.init();
        Curio.Coin.init();
        Curio.Rage.init();

        Weapon.init();
        Weapon.LightGreatsword.init();
    }

    public static class Curio {
        private static void init() {
        }

        public static class Math {
            private static void init() {
            }

            public static final Holder<Item> SCIENTIFIC_CALCULATOR = ITEMS.register("scientific_calculator",
                    () -> new ScientificCalculator(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("scientific_calculator")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(WEARER))
                                            .pop()
                                            .build())));

            public static final Holder<Item> COSINE = ITEMS.register("cosine",
                    () -> new Cosine(new Item.Properties()));
            public static final Holder<Item> ADDITION = ITEMS.register("addition",
                    () -> new Addition(new Item.Properties().component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("addition")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .args(() -> List.of(attribute(Attributes.ATTACK_DAMAGE, 0.5F, ADD_VALUE), FOE))
                                    .pop()
                                    .build())));
            public static final Holder<Item> SUBTRACTION = ITEMS.register("subtraction",
                    () -> new BrutalityFunctionCurioItem(new Item.Properties())
                            .withAttributes(
                                    new AttributeContainer(BrutalityAttributes.DAMAGE_TAKEN, -3, ADD_VALUE)
                            ));
            public static final Holder<Item> SINE = ITEMS.register("sine",
                    () -> new Sine(new Item.Properties()));
            public static final Holder<Item> SUM = ITEMS.register("sum",
                    () -> new Sum(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("sum")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(attribute(Attributes.ATTACK_DAMAGE), attribute(Attributes.ATTACK_SPEED)))
                                            .pop()
                                            .build())));
            public static final Holder<Item> PI = ITEMS.register("pi",
                    () -> new Pi(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("pi")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(attribute(Attributes.ATTACK_DAMAGE), attribute(Attributes.ATTACK_SPEED)))
                                            .pop()
                                            .build())));
            public static final Holder<Item> DIVISION = ITEMS.register("division",
                    () -> new Division(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("division")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(DAMAGE))
                                            .pop()
                                            .build())));
            public static final Holder<Item> MULTIPLICATION = ITEMS.register("multiplication",
                    () -> new Multiplication(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("multiplication")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(attribute(Attributes.ATTACK_DAMAGE, 0.01F, ADD_MULTIPLIED_TOTAL), FOE))
                                            .pop()
                                            .build())));
            public static final Holder<Item> INFINITY = ITEMS.register("infinity",
                    () -> new Infinity(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("infinity")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(FOES))
                                            .pop()
                                            .build()))
                            .withAttributes(
                                    new AttributeContainer(Attributes.MAX_HEALTH, Double.NEGATIVE_INFINITY, ADD_MULTIPLIED_TOTAL),
                                    new AttributeContainer(BrutalityAttributes.DAMAGE_TAKEN, Double.POSITIVE_INFINITY, ADD_MULTIPLIED_TOTAL)
                            ));

        }

        public static class Coin {
            private static void init() {
            }

            public static final Holder<Item> OVERDRAW_POUCH = ITEMS.register("overdraw_pouch",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.COSMIC.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("overdraw_pouch")
                                            .add(DescriptionType.LORE).lines(1).pop()
                                            .add(DescriptionType.PASSIVE).lines(2).pop()
                                            .build())));

            public static final Holder<Item> NUMISMATIC_CATALYST = ITEMS.register("numismatic_catalyst",
                    () -> new NumismaticCatalyst(new Item.Properties().rarity(BrutalityRarities.VOIDTOUCHED.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("numismatic_catalyst")
                                            .add(DescriptionType.PASSIVE).lines(1).pop()
                                            .build())));

            public static final Holder<Item> HEART_OF_THE_HOARDER = ITEMS.register("heart_of_the_hoarder",
                    () -> new HeartOfTheHoarder(new Item.Properties().rarity(BrutalityRarities.STYGIAN.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("heart_of_the_hoarder")
                                            .add(DescriptionType.PASSIVE).lines(1).pop()
                                            .build())));

            public static final Holder<Item> THE_GLUTTONS_PURSE = ITEMS.register("the_gluttons_purse",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("the_gluttons_purse")
                                            .add(DescriptionType.PASSIVE).lines(1).pop()
                                            .build())));

            public static final Holder<Item> MIRRORED_MINT = ITEMS.register("mirrored_mint",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.CORALINE.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("mirrored_mint")
                                            .add(DescriptionType.PASSIVE).lines(1).pop()
                                            .build())));

            public static final Holder<Item> MOBIUS_STRIP = ITEMS.register("mobius_strip",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.CONDUCTIVE.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("mobius_strip")
                                            .add(DescriptionType.LORE).lines(1).pop()
                                            .add(DescriptionType.PASSIVE).lines(1).pop()
                                            .build())));

            public static final Holder<Item> REVERSE_COIN = ITEMS.register("reverse_coin",
                    () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.ENCRYPTED.getValue())
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("reverse_coin")
                                            .add(DescriptionType.PASSIVE).lines(1).pop()
                                            .build())));

            public static final Holder<Item> SWALLOWED_PENNY = ITEMS.register("swallowed_penny",
                    () -> new SwallowedPenny(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("swallowed_penny")
                                            .add(DescriptionType.PASSIVE).lines(2).cooldown(3).pop()
                                            .build())));

            public static final Holder<Item> GLOVE_OF_GREED = ITEMS.register("glove_of_greed",
                    () -> new BrutalityCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("glove_of_greed")
                                            .add(DescriptionType.ACTIVE)
                                            .lines(2)
                                            .cooldown(4)
                                            .keybind(BrutalityKeyNames.ACTIVE_ABILITY)
                                            .pop()
                                            .build())));

            public static final Holder<Item> HAND_OF_MIDAS = ITEMS.register("hand_of_midas",
                    () -> new BrutalityCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("hand_of_midas")
                                            .add(DescriptionType.LORE).lines(1).pop()
                                            .add(DescriptionType.ACTIVE)
                                            .lines(1)
                                            .cooldown(4)
                                            .keybind(BrutalityKeyNames.ACTIVE_ABILITY)
                                            .pop()
                                            .build())));

            public static final Holder<Item> MINT_MASTERS_SIGNET = ITEMS.register("mint_masters_signet",
                    () -> new BrutalityCurioItem(new Item.Properties()).withAttributes(
                            new AttributeContainer(BrutalityAttributes.COIN_COOLDOWN, -0.1, ADD_VALUE)
                    ));
        }

        public static class Rage {
            private static void init() {
            }

            public static final Holder<Item> BOILING_BLOOD = ITEMS.register("boiling_blood",
                    () -> new BoilingBlood(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("boiling_blood")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(RAGE, WEARER, blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE), DAMAGE))
                                            .pop()
                                            .build())));

            public static final Holder<Item> INFERNAL_BLOOD = ITEMS.register("infernal_blood",
                    () -> new InfernalBlood(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("infernal_blood")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(3)
                                            .args(() -> List.of(RAGE, WEARER, blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE), DAMAGE,
                                                    attribute(Attributes.ATTACK_DAMAGE, 0.5F, ADD_VALUE), attribute(Attributes.BURNING_TIME),
                                                    attribute(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.25, ADD_MULTIPLIED_TOTAL), percentage(50), HEALTH))
                                            .pop()
                                            .build())));

            public static final Holder<Item> HATE_SIGIL = ITEMS.register("hate_sigil",
                    () -> new BrutalityRageCurioItem(new Item.Properties()).withAttributes(
                            new AttributeContainer(BrutalityAttributes.MAX_RAGE, 100, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.1, ADD_MULTIPLIED_TOTAL)
                    )
            );

            public static final Holder<Item> SPITE_SHARD = ITEMS.register("spite_shard",
                    () -> new SpiteShard(new Item.Properties().component(
                            BrutalityDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("spite_shard")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .args(() -> List.of(RAGE, percentage(50), FOES))
                                    .pop()
                                    .build()
                    )).withAttributes(
                            new AttributeContainer(BrutalityAttributes.MAX_RAGE, 100, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.1, ADD_MULTIPLIED_TOTAL)
                    )
            );

            public static final Holder<Item> RAMPAGE_CLOCK = ITEMS.register("rampage_clock",
                    () -> new RampageClock(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("rampage_clock")
                                            .add(DescriptionType.ON_KILL)
                                            .lines(1)
                                            .args(() -> List.of(effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)))
                                            .pop()
                                            .build())));

            public static final Holder<Item> PAIN_ENZYME = ITEMS.register("pain_enzyme",
                    () -> new PainCatalyst(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("pain_enzyme")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(percentage(100), DAMAGE, RAGE))
                                            .pop()
                                            .build())));

            public static final Holder<Item> FURY_BATTERY = ITEMS.register("fury_battery",
                    () -> new BrutalityRageCurioItem(new Item.Properties()).withAttributes(
                            new AttributeContainer(BrutalityAttributes.MAX_RAGE, 85, ADD_VALUE)
                    )
            );

            public static final Holder<Item> OMEGA_GAUNTLET = ITEMS.register("omega_gauntlet",
                    () -> new OmegaGauntlet(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("omega_gauntlet")
                                            .add(DescriptionType.ON_HIT)
                                            .lines(1)
                                            .args(() -> List.of(attribute(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO), percentage(500), effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)))
                                            .pop()
                                            .build()
                            )).withAttributes(
                            new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 1.5, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.05, ADD_VALUE)
                    )
            );

            public static final Holder<Item> GAMMA_SERUM = ITEMS.register("gamma_serum",
                    () -> new GammaSerum(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("gamma_serum")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(attribute(Attributes.SCALE, 0.5, ADD_MULTIPLIED_TOTAL), effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)))
                                            .pop()
                                            .build())));

            public static final Holder<Item> STRESS_PILLS = ITEMS.register("stress_pills",
                    () -> new StressPills(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("stress_pills")
                                            .add(DescriptionType.ACTIVE)
                                            .lines(1)
                                            .cooldown(40)
                                            .keybind(BrutalityKeyNames.ACTIVE_ABILITY)
                                            .args(() -> List.of(RAGE, percentage(50), attribute(BrutalityAttributes.MAX_RAGE)))
                                            .pop()
                                            .build())));

            public static final Holder<Item> SEROTONIN_PILLS = ITEMS.register("serotonin_pills",
                    () -> new SerotoninPills(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("serotonin_pills")
                                            .add(DescriptionType.ACTIVE)
                                            .lines(3)
                                            .cooldown(40)
                                            .keybind(BrutalityKeyNames.ACTIVE_ABILITY)
                                            .args(() -> List.of(
                                                    effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE),
                                                    RAGE,
                                                    effectWithStyle(BrutalityEffects.TRANQUILITY, 5, Styles.Special.VOIDTOUCHED)
                                            ))
                                            .pop()
                                            .build())));

            public static final Holder<Item> ENDER_DRAGON_STEM_CELLS = ITEMS.register("ender_dragon_stem_cells",
                    () -> new BrutalityRageCurioItem(new Item.Properties()).withAttributes(
                            new AttributeContainer(BrutalityAttributes.MAX_RAGE, 75, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 0.25, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.SCALE, 0.5, ADD_MULTIPLIED_TOTAL)
                    ));

            public static final Holder<Item> WRATH = ITEMS.register("wrath",
                    () -> new Wrath(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("wrath")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(Component.translatable("tooltip." + Brutality.MOD_ID + ".hexing_circle").withStyle(Styles.Special.RAGE), attribute(BrutalityAttributes.MAX_RAGE), attribute(BrutalityAttributes.ENRAGED_LEVEL)))
                                            .pop()
                                            .add(DescriptionType.UPON_TRIGGERING_RAGE)
                                            .lines(1)
                                            .args(() -> List.of(Component.translatable("tooltip." + Brutality.MOD_ID + ".hexing_circle").withStyle(Styles.Special.RAGE), DAMAGE, FOES))
                                            .pop()
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
                                    ItemDescriptions.forItem("grudge_totem")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(2)
                                            .args(() -> List.of(WEARER, FATAL, DAMAGE, effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE), RAGE))
                                            .pop()
                                            .build())));

            public static final Holder<Item> ANGER_MANAGEMENT = ITEMS.register("anger_management",
                    () -> new AngerManagement(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("anger_management")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(attribute(Attributes.ATTACK_DAMAGE, 5, ADD_VALUE), attribute(Attributes.ARMOR, 4, ADD_VALUE), attribute(GLAttributes.CRITICAL_DAMAGE, 0.25, ADD_VALUE), RAGE))
                                            .pop()
                                            .build())));

            public static final Holder<Item> BROKEN_CONTROLLER = ITEMS.register("broken_controller",
                    () -> new BrokenController(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("broken_controller")
                                            .add(DescriptionType.LORE).lines(1).pop()
                                            .add(DescriptionType.PASSIVE).lines(1).pop()
                                            .build())
                    ).withAttributes(
                            new AttributeContainer(BrutalityAttributes.ENRAGED_LEVEL, 4, ADD_VALUE),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 2, ADD_VALUE)
                    )
            );

            public static final Holder<Item> WIRELESS_CONTROLLER = ITEMS.register("wireless_controller",
                    () -> new BrutalityRageCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("wireless_controller")
                                            .add(DescriptionType.LORE).lines(1).pop()
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
                                    new AttributeContainer(BrutalityAttributes.DAMAGE_TAKEN, -0.25, ADD_MULTIPLIED_TOTAL)
                            )
            );

            public static final Holder<Item> BLOODBOIL_BOMB = ITEMS.register("bloodboil_bomb",
                    () -> new BloodboilBomb(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("bloodboil_bomb")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(2)
                                            .args(() -> List.of(BloodExplosionDamageCalculator.BLOOD_EXPLOSION, attribute(BrutalityAttributes.ENRAGED_LEVEL), attribute(BrutalityAttributes.MAX_RAGE), BloodExplosionDamageCalculator.BLOOD_EXPLOSION, percentage(10), DAMAGE))
                                            .pop()
                                            .add(DescriptionType.UPON_TRIGGERING_RAGE)
                                            .lines(1)
                                            .args(() -> List.of(BloodExplosionDamageCalculator.BLOOD_EXPLOSION))
                                            .pop()
                                            .build()
                            ))
                            .withAttributes(
                                    new AttributeContainer(BrutalityAttributes.MAX_RAGE, 50, ADD_VALUE)
                            )
            );

            public static final Holder<Item> BLOOD_PULSE_KNUCKLES = ITEMS.register("blood_pulse_knuckles",
                    () -> new BloodPulseKnuckles(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("blood_pulse_knuckles")
                                            .add(DescriptionType.ON_HIT)
                                            .lines(2)
                                            .args(() -> List.of(BloodExplosionDamageCalculator.BLOOD_EXPLOSION, effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE), BloodExplosionDamageCalculator.BLOOD_EXPLOSION, percentage(10), DAMAGE))
                                            .pop()
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
                                    ItemDescriptions.forItem("mechanical_aorta")
                                            .add(DescriptionType.LORE).lines(1).pop()
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(WEARER, DAMAGE, effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)))
                                            .pop()
                                            .build()
                            )).withAttributes(
                            new AttributeContainer(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.35, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(BrutalityAttributes.ENRAGED_DURATION, 0.15, ADD_MULTIPLIED_TOTAL)
                    )
            );

            public static final Holder<Item> TOXICOSIS_PENDANT = ITEMS.register("toxicosis_pendant",
                    () -> new ToxicosisPendant(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("toxicosis_pendant")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(attribute(BrutalityAttributes.DAMAGE_TO_RAGE_RATIO, 0.15, ADD_VALUE), DEBUFF))
                                            .pop()
                                            .build())));

            public static final Holder<Item> MONOCLE_OF_BRUTALITY = ITEMS.register("monocle_of_brutality",
                    () -> new BrutalityRageCurioItem(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("monocle_of_brutality")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(1)
                                            .args(() -> List.of(WEARER, ENTITIES, effectWithStyle(BrutalityEffects.ENRAGED, Styles.Special.RAGE)))
                                            .pop()
                                            .build())));

            public static final Holder<Item> DEMON_BEADS = ITEMS.register("demon_beads",
                    () -> new DemonBeads(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("demon_beads")
                                            .add(DescriptionType.UPON_TRIGGERING_RAGE)
                                            .lines(1)
                                            .args(() -> List.of(effectWithStyle(BrutalityEffects.ASURA_FORM, Styles.Special.RAGE), attribute(BrutalityAttributes.ENRAGED_DURATION)))
                                            .pop()
                                            .build())));

            public static final Holder<Item> HEART_OF_DARKNESS = ITEMS.register("heart_of_darkness",
                    () -> new HeartOfDarkness(new Item.Properties()
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("heart_of_darkness")
                                            .add(DescriptionType.PASSIVE)
                                            .lines(2)
                                            .args(() -> List.of(RAGE, percentage(2), attribute(BrutalityAttributes.MAX_RAGE), RAGE))
                                            .pop()
                                            .build())));
        }

        public static final Holder<Item> ANTI_CHEAT = ITEMS.register("anti_cheat",
                () -> new BrutalityCurioItem(new Item.Properties()
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("anti_cheat")
                                        .add(DescriptionType.MULTIPLAYER_ONLY)
                                        .pop()
                                        .add(DescriptionType.LORE)
                                        .lines(1)
                                        .pop()
                                        .add(DescriptionType.PASSIVE)
                                        .lines(1)
                                        .args(() -> List.of(
                                                WEARER, FOES
                                        ))
                                        .pop()
                                        .build())));

        public static final Holder<Item> HALF_EMPTY_GLASS = ITEMS.register("half_empty_glass",
                () -> new HalfEmptyGlass(new Item.Properties()
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("half_empty_glass")
                                        .add(DescriptionType.LORE)
                                        .lines(1)
                                        .pop()
                                        .add(DescriptionType.ON_HIT)
                                        .lines(1)
                                        .args(() -> List.of(
                                                effectWithStyle(BrutalityEffects.DESPAIR, Styles.Special.VOIDTOUCHED)
                                        ))
                                        .pop()
                                        .build())));

        public static final Holder<Item> HALF_FULL_GLASS = ITEMS.register("half_full_glass",
                () -> new BrutalityCurioItem(new Item.Properties()
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("half_full_glass")
                                        .add(DescriptionType.LORE)
                                        .lines(1)
                                        .pop()
                                        .build()))
                        .withAttributes(
                                new AttributeContainer(BrutalityAttributes.DESPAIR_EFFECTIVENESS, -0.15, ADD_VALUE)
                        ));


        public static final Holder<Item> DIVINE_IMMORTALS_RING = ITEMS.register("divine_immortals_ring",
                () -> new DivineImmortalsRing(new Item.Properties().rarity(BrutalityRarities.STYGIAN.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("divine_immortals_ring")
                                        .add(DescriptionType.LORE)
                                        .lines(1)
                                        .pop()
                                        .add(DescriptionType.PASSIVE)
                                        .lines(3)
                                        .args(() -> List.of(WEARER,
                                                TooltipUtil.ITEMS,
                                                DivineImmortalsRing.SUBSPACE.get(),
                                                DivineImmortalsRing.SUBSPACE.get(),
                                                TooltipUtil.ITEMS,
                                                TooltipUtil.ITEMS,
                                                DivineImmortalsRing.SUBSPACE.get()
                                        ))
                                        .keybind(BrutalityKeyNames.DIVINE_IMMORTALS_RING)
                                        .pop()
                                        .build())));


        public static final Holder<Item> XRAY_GOGGLES = ITEMS.register("xray_goggles",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.ENCRYPTED.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("xray_goggles")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(1)
                                        .args(() -> List.of(WEARER, ENTITIES))
                                        .pop()
                                        .build())));

        public static final Holder<Item> OMNICHROME_RING = ITEMS.register("omnichrome_ring",
                () -> new OmnichromeRing(new Item.Properties().rarity(BrutalityRarities.GODLY.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("omnichrome_ring")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(5)
                                        .args(() -> List.of(
                                                effectWithStyle(MobEffects.FIRE_RESISTANCE, Styles.Elements.FIRE),
                                                effectWithStyle(MobEffects.WATER_BREATHING, Styles.Elements.WATER),
                                                effectWithStyle(MobEffects.DOLPHINS_GRACE, Styles.Elements.WATER),
                                                effectWithStyle(MobEffects.SLOW_FALLING, Styles.Elements.WIND),
                                                effectWithStyle(MobEffects.POISON, Styles.PotionEffects.POISON),
                                                WEARER, entityWithStyle(EntityType.PIGLIN, Styles.Rarity.LEGENDARY),
                                                WEARER, entityWithStyle(EntityType.ENDERMAN, Styles.Special.COSMIC),
                                                WEARER, blockWithStyle(Blocks.POWDER_SNOW, Styles.Special.GLACIAL)
                                        ))
                                        .pop()
                                        .build())));

        public static final Holder<Item> RING_OF_RINGS = ITEMS.register("ring_of_rings",
                () -> new RingOfRings(new Item.Properties()
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("ring_of_rings")
                                        .add(DescriptionType.LORE).lines(1).pop()
                                        .add(DescriptionType.PASSIVE)
                                        .lines(1)
                                        .args(() -> List.of(attribute(Attributes.ARMOR, 1, ADD_VALUE), attribute(BrutalityAttributes.LETHALITY, 1, ADD_VALUE)))
                                        .pop()
                                        .build())));

        public static final Holder<Item> RING_OF_DESPAIR = ITEMS.register("ring_of_despair",
                () -> new RingOfDespair(new Item.Properties()
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("ring_of_despair")
                                        .add(DescriptionType.LORE).lines(1).pop()
                                        .add(DescriptionType.ACTIVE)
                                        .lines(1)
                                        .cooldown(40)
                                        .keybind(BrutalityKeyNames.ACTIVE_ABILITY)
                                        .args(() -> List.of(effectWithStyle(BrutalityEffects.DESPAIR, 5, Styles.PotionEffects.WITHER), FOES))
                                        .pop()
                                        .build())));

        public static final Holder<Item> FIERY_FLUID = ITEMS.register("fiery_fluid",
                () -> new FieryFluid(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("fiery_fluid")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(1)
                                        .args(() -> List.of(attribute(Attributes.ATTACK_DAMAGE, 0.5F, ADD_VALUE), attribute(Attributes.BURNING_TIME)))
                                        .pop()
                                        .build())));

        public static final Holder<Item> MOLTEN_MACRONUTRIENTS = ITEMS.register("molten_macronutrients",
                () -> new MoltenMacronutrients(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("molten_macronutrients")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(1)
                                        .args(() -> List.of(percentage(5), WEARER, blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE)))
                                        .pop()
                                        .build())));

        public static final Holder<Item> BLOOD_STONE = ITEMS.register("blood_stone",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.STYGIAN.getValue())).withAttributes(
                        new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.3, ADD_MULTIPLIED_TOTAL),
                        new AttributeContainer(GLAttributes.LIFESTEAL, 0.1, ADD_VALUE)));


        public static final Holder<Item> BLOOD_PACT = ITEMS.register("blood_pact",
                () -> new MoltenMacronutrients(new Item.Properties().rarity(BrutalityRarities.STYGIAN.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("blood_pact")
                                        .add(DescriptionType.LORE).lines(1).pop()
                                        .build()))
                        .withAttributes(new AttributeContainer(Attributes.MAX_HEALTH, 3, ADD_MULTIPLIED_TOTAL),
                                new AttributeContainer(BrutalityAttributes.DAMAGE_TAKEN, 4, ADD_MULTIPLIED_TOTAL),
                                new AttributeContainer(BrutalityAttributes.OMNIVAMP, 0.15, ADD_VALUE)
                        )
        );

        public static final Holder<Item> FLIPPERS_OF_ICARUS = ITEMS.register("flippers_of_icarus",
                () -> new FlippersOfIcarus(new Item.Properties().rarity(BrutalityRarities.GODLY.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("flippers_of_icarus")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(2)
                                        .args(() -> List.of(WEARER, blockWithStyle(Blocks.AIR, Styles.Elements.WIND), blockWithStyle(Blocks.WATER, Styles.Elements.WATER), WEARER, blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE)))
                                        .pop()
                                        .build())));

        public static final Holder<Item> POSEIDONS_BLESSING = ITEMS.register("poseidons_blessing",
                () -> new PoseidonsBlessing(new Item.Properties().rarity(BrutalityRarities.CORALINE.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("poseidons_blessing")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(2)
                                        .args(() -> List.of(blockWithStyle(Blocks.WATER, Styles.Elements.WATER), effectWithStyle(MobEffects.WATER_BREATHING, Styles.Elements.WATER), effectWithStyle(MobEffects.CONDUIT_POWER, Styles.Special.CORALINE)))
                                        .pop()
                                        .build())));

        public static final Holder<Item> HYDROPHOBIC_NANOCOATING = ITEMS.register("hydrophobic_nanocoating",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.VOIDTOUCHED.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("hydrophobic_nanocoating")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(1)
                                        .args(() -> List.of(blockWithStyle(Blocks.WATER, Styles.Elements.WATER)))
                                        .pop()
                                        .build())));

        public static final Holder<Item> LAVA_LENSES = ITEMS.register("lava_lenses",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("lava_lenses")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(2)
                                        .args(() -> List.of(WEARER, blockWithStyle(Blocks.LAVA, Styles.Elements.FIRE), WEARER, blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE)))
                                        .pop()
                                        .build())));

        public static final Holder<Item> SURTRS_HORN = ITEMS.register("surtrs_horn",
                () -> new SurtrsHorn(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("surtrs_horn")
                                        .add(DescriptionType.PASSIVE)
                                        .lines(5)
                                        .args(() -> List.of(
                                                blockWithStyle(Blocks.LAVA, Styles.Elements.FIRE),
                                                effectWithStyle(MobEffects.FIRE_RESISTANCE, Styles.Elements.FIRE),
                                                WEARER, blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE),
                                                WEARER, blockWithStyle(Blocks.LAVA, Styles.Elements.FIRE),
                                                WEARER, blockWithStyle(Blocks.FIRE, Styles.Elements.FIRE)
                                        ))
                                        .pop()
                                        .build())));

        public static final Holder<Item> PORTABLE_FIRE_EXTINGUISHER = ITEMS.register("portable_fire_extinguisher",
                () -> new BrutalityCurioItem(new Item.Properties().rarity(BrutalityRarities.SMOLDERING.getValue())
                        .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                ItemDescriptions.forItem("portable_fire_extinguisher")
                                        .add(DescriptionType.LORE).lines(1).pop()
                                        .build()))
                        .withAttributes(new AttributeContainer(Attributes.BURNING_TIME, -0.5, ADD_MULTIPLIED_TOTAL)));
    }

    public static class Weapon {
        public static void init() {
        }

        public static class LightGreatsword {
            public static void init() {
            }

            public static final Holder<Item> PLINKO_BLADE = ITEMS.register("plinko_blade",
                    () -> new PlinkoBlade(Tiers.NETHERITE, new Item.Properties()
                            .attributes(SwordItem.createAttributes(Tiers.NETHERITE, -1, -3.25F))
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("plinko_blade")
                                            .add(DescriptionType.LORE).lines(1).pop()
                                            .add(DescriptionType.ON_HIT)
                                            .lines(1)
                                            .args(() -> List.of(DAMAGE))
                                            .pop()
                                            .build())));

            public static final Holder<Item> BLADE_OF_THE_RUINED_KING = ITEMS.register("blade_of_the_ruined_king",
                    () -> new BladeOfTheRuinedKing(Tiers.NETHERITE, new Item.Properties()
                            .attributes(SwordItem.createAttributes(Tiers.NETHERITE, 2, -3.1F)
                                    .withModifierAdded(
                                            GLAttributes.LIFESTEAL,
                                            AttributeUtil.getModifier(GLAttributes.LIFESTEAL, 0.1, AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.HAND))
                            .component(BrutalityDataComponents.ITEM_DESCRIPTIONS,
                                    ItemDescriptions.forItem("blade_of_the_ruined_king")
                                            .add(DescriptionType.LORE).lines(1).pop()
                                            .add(DescriptionType.ON_KILL)
                                            .lines(1)
                                            .args(() -> List.of(BladeOfTheRuinedKing.MIST_WRAITH))
                                            .pop()
                                            .add(DescriptionType.PASSIVE)
                                            .lines(3)
                                            .args(() -> List.of(BladeOfTheRuinedKing.MIST_WRAITH, effectWithStyle(BrutalityEffects.RUINED, Styles.BasicColors.CYAN), USER, BladeOfTheRuinedKing.MIST_WRAITH, BladeOfTheRuinedKing.MIST_WRAITH, USER))
                                            .pop()
                                            .add(DescriptionType.ON_HIT)
                                            .lines(3)
                                            .args(() -> List.of(BONUS, DAMAGE, percentage(10), VICTIM, CURRENT_HEALTH, effectWithStyle(MobEffects.MOVEMENT_SLOWDOWN, Styles.BasicColors.LIGHT_GRAY), effectWithStyle(MobEffects.MOVEMENT_SPEED, Styles.PotionEffects.SWIFTNESS)))
                                            .pop()
                                            .build())));
        }
    }
}
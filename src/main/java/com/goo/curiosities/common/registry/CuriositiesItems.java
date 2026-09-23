package com.goo.curiosities.common.registry;

import com.goo.curiosities.client.registry.CuriositiesKeymappings;
import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.item.*;
import com.goo.curiosities.common.item.curio.anklet.*;
import com.goo.curiosities.common.item.curio.back.CensorshipCloak;
import com.goo.curiosities.common.item.curio.back.CloakOfTrueIce;
import com.goo.curiosities.common.item.curio.back.NetheriteJetpack;
import com.goo.curiosities.common.item.curio.belt.InnerTube;
import com.goo.curiosities.common.item.curio.belt.MiniatureAnchor;
import com.goo.curiosities.common.item.curio.belt.OldGuillotine;
import com.goo.curiosities.common.item.curio.belt.SurtrsHorn;
import com.goo.curiosities.common.item.curio.body.IronGut;
import com.goo.curiosities.common.item.curio.body.NanoMachines;
import com.goo.curiosities.common.item.curio.bracelet.PerfectCell;
import com.goo.curiosities.common.item.curio.charm.*;
import com.goo.curiosities.common.item.curio.charm.bottles.BlizzardInABottle;
import com.goo.curiosities.common.item.curio.charm.bottles.CloudInABottle;
import com.goo.curiosities.common.item.curio.charm.bottles.SandstormInABottle;
import com.goo.curiosities.common.item.curio.charm.bottles.TsunamiInABottle;
import com.goo.curiosities.common.item.curio.feet.*;
import com.goo.curiosities.common.item.curio.hand.*;
import com.goo.curiosities.common.item.curio.head.*;
import com.goo.curiosities.common.item.curio.heart.*;
import com.goo.curiosities.common.item.curio.necklace.*;
import com.goo.curiosities.common.item.curio.ring.OmnichromeRing;
import com.goo.curiosities.common.item.curio.ring.RingOfRings;
import com.goo.curiosities.common.item.curio.ring.RoadrunnersRing;
import com.goo.curiosities.common.tooltip.DescriptionType;
import com.goo.curiosities.common.tooltip.ItemDescriptions;
import com.goo.goo_lib.common.attribute.AttributeContainer;
import com.goo.goo_lib.common.registry.GLAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.*;

public class CuriositiesItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Curiosities.MOD_ID);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static final Holder<Item> MOVEMENT_GODS_TRACERS = ITEMS.register("movement_gods_tracers",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("movement_gods_tracers")
                                    .add(DescriptionType.LORE)
                                    .lines(1)
                                    .pop()
                                    .add(DescriptionType.PASSIVE)
                                    .lines(4)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.AIR_DRAG_MODIFIER, 2.5, ADD_VALUE),
                            new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.4, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> MICRO_THRUSTERS = ITEMS.register("micro_thrusters",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("micro_thrusters")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1).pop().build()))
                    .withAttributes(new AttributeContainer(GLAttributes.AIR_DRAG_MODIFIER, 2.5, ADD_VALUE)));

    public static final Holder<Item> OMNIDIRECTIONAL_MOVEMENT_GEAR = ITEMS.register("omnidirectional_movement_gear",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("omnidirectional_movement_gear")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1).pop().build())));

    public static final Holder<Item> KINETIC_COMPENSATOR = ITEMS.register("kinetic_compensator",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("kinetic_compensator")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1).pop().build())));

    public static final Holder<Item> VECTOR_STABILIZER = ITEMS.register("vector_stabilizer",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("vector_stabilizer")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1).pop().build())));

    public static final Holder<Item> PLATED_STEELCAPS = ITEMS.register("plated_steelcaps",
            () -> new PlatedSteelcaps(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("plated_steelcaps")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1).pop().build())));

    public static final Holder<Item> SEISMIC_STOMPERS = ITEMS.register("seismic_stompers",
            () -> new SeismicStompers(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("seismic_stompers")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1).pop().build())));

    public static final Holder<Item> SLIPSTREAM_TRACERS = ITEMS.register("slipstream_tracers",
            () -> new SlipstreamTracers(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("slipstream_tracers")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1).pop().build())));

    public static final Holder<Item> FLAME_WALKERS = ITEMS.register("flame_walkers",
            () -> new FlameWalkers(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("flame_walkers")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2).pop().build())));

    public static final Holder<Item> FLAME_THREADERS = ITEMS.register("flame_threaders",
            () -> new FlameThreaders(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("flame_threaders")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(3).pop().build())));

    public static final Holder<Item> FLAME_STOMPERS = ITEMS.register("flame_stompers",
            () -> new FlameStompers(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("flame_stompers")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(4).pop().build())));

    public static final Holder<Item> WAY_OF_THE_WIND = ITEMS.register("way_of_the_wind",
            () -> new WayOfTheWind(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("way_of_the_wind")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> NETHERITE_JETPACK = ITEMS.register("netherite_jetpack",
            () -> new NetheriteJetpack(new Item.Properties().durability(300)
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("netherite_jetpack")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .keybind("key.jump")
                                    .pop().build())));

    public static final Holder<Item> DESYNCHRONIZER = ITEMS.register("desynchronizer",
            () -> new Desynchronizer(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("desynchronizer")
                                    .add(DescriptionType.ACTIVE)
                                    .lines(1)
                                    .cooldown(120)
                                    .pop().build())));

    public static final Holder<Item> CORRUPTED_DIAMOND = ITEMS.register("corrupted_diamond",
            () -> new Desynchronizer(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("corrupted_diamond")
                                    .add(DescriptionType.ACTIVE)
                                    .lines(1)
                                    .cooldown(120)
                                    .pop().build())));

    public static final Holder<Item> INNER_TUBE = ITEMS.register("inner_tube",
            () -> new InnerTube(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("inner_tube")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));

    public static final Holder<Item> SALAMANDERS_STRIDERS = ITEMS.register("salamanders_striders",
            () -> new SalamandersStriders(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("salamanders_striders")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));

    public static final Holder<Item> AMPHIBIAN_BOOTS = ITEMS.register("amphibian_boots",
            () -> new AmphibianBoots(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("amphibian_boots")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> CONSTRUCTION_BOOTS = ITEMS.register("construction_boots",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("construction_boots")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> HONEY_BOOTS = ITEMS.register("honey_boots",
            () -> new HoneyBoots(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("honey_boots")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.JUMP_STRENGTH, Double.NEGATIVE_INFINITY, ADD_VALUE)
                    ));
    public static final Holder<Item> CLIMBING_CLAWS = ITEMS.register("climbing_claws",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.WALL_CLIMBING, 1, ADD_VALUE)
                    ));
    public static final Holder<Item> SHOE_SPIKES = ITEMS.register("shoe_spikes",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.WALL_CLIMBING, 1, ADD_VALUE)
                    ));
    public static final Holder<Item> TIGER_CLIMBING_GEAR = ITEMS.register("tiger_climbing_gear",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.WALL_CLIMBING, 1, ADD_VALUE),
                            new AttributeContainer(GLAttributes.CLIMBING_SPEED_MODIFIER, 0.25, ADD_VALUE)
                    ));

    public static final Holder<Item> ESPRESSO = ITEMS.register("espresso",
            () -> new Espresso(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("espresso")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop()
                                    .add(DescriptionType.ACTIVE)
                                    .lines(1)
                                    .cooldown(180)
                                    .pop()
                                    .build())));

    public static final Holder<Item> COLD_PILLOW = ITEMS.register("cold_pillow",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("cold_pillow")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(3)
                                    .pop()
                                    .build())));


    public static final Holder<Item> LAVA_WALKERS = ITEMS.register("lava_walkers",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("lava_walkers")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> WATER_WALKERS = ITEMS.register("water_walkers",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("water_walkers")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> HIGH_HEALS = ITEMS.register("high_heals",
            () -> new HighHeals(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("high_heals")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));


    public static final Holder<Item> ICE_SKATES = ITEMS.register("ice_skates",
            () -> new IceSkates(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("ice_skates")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> BOOTS_OF_SWIFTNESS = ITEMS.register("boots_of_swiftness",
            () -> new BootsOfSwiftness(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("boots_of_swiftness")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.SNEAKING_SPEED, 0.15, ADD_VALUE)
                    ));


    public static final Holder<Item> CLOAK_OF_TRUE_ICE = ITEMS.register("cloak_of_true_ice",
            () -> new CloakOfTrueIce(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("cloak_of_true_ice")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())).withAttributes(
                    new AttributeContainer(GLAttributes.AIR_DRAG_MODIFIER, -0.5F, ADD_VALUE)
            ));

    public static final Holder<Item> INERTIA_BOOSTER = ITEMS.register("inertia_booster",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.FRICTION_MODIFIER, -0.25F, ADD_VALUE),
                            new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.15, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> ELBOW_GREASE = ITEMS.register("elbow_grease",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.AIR_DRAG_MODIFIER, -0.25F, ADD_VALUE),
                            new AttributeContainer(Attributes.ATTACK_SPEED, 0.15, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> EARTHEN_BLESSING = ITEMS.register("earthen_blessing",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.FRICTION_MODIFIER, 0.25F, ADD_VALUE),
                            new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, 0.25, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.GRAVITY, 0.25, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> PETROLEUM_JELLY = ITEMS.register("petroleum_jelly",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.FRICTION_MODIFIER, -0.25F, ADD_VALUE),
                            new AttributeContainer(Attributes.MAX_HEALTH, 3, ADD_VALUE)
                    ));

    public static final Holder<Item> ZEPHYR_IN_A_BOTTLE = ITEMS.register("zephyr_in_a_bottle",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.AIR_DRAG_MODIFIER, -0.25F, ADD_VALUE),
                            new AttributeContainer(Attributes.JUMP_STRENGTH, 0.25F, ADD_VALUE),
                            new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.5F, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.GRAVITY, -0.1, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> LIGHT_SWITCH = ITEMS.register("light_switch",
            () -> new LightSwitch(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("light_switch")
                                    .add(DescriptionType.LORE)
                                    .lines(1)
                                    .pop()
                                    .add(DescriptionType.ACTIVE)
                                    .keybind(CuriositiesKeymappings.ACTIVE_ABILITY)
                                    .cooldown(20)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> EMERGENCY_MEETING = ITEMS.register("emergency_meeting",
            () -> new EmergencyMeeting(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("emergency_meeting")
                                    .add(DescriptionType.ACTIVE)
                                    .keybind(CuriositiesKeymappings.ACTIVE_ABILITY)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> ESCAPE_KEY = ITEMS.register("escape_key",
            () -> new EscapeKey(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("escape_key")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> VINDICATOR_STEROIDS = ITEMS.register("vindicator_steroids",
            () -> new VindicatorSteroids(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("vindicator_steroids")
                                    .add(DescriptionType.ACTIVE)
                                    .keybind(CuriositiesKeymappings.ACTIVE_ABILITY)
                                    .cooldown(45)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> MIRACLE_CURE = ITEMS.register("miracle_cure",
            () -> new MiracleCure(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("miracle_cure")
                                    .add(DescriptionType.ACTIVE)
                                    .keybind(CuriositiesKeymappings.ACTIVE_ABILITY)
                                    .cooldown(60)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> GLASS_HEART = ITEMS.register("glass_heart",
            () -> new GlassHeart(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(Attributes.ARMOR, -1, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(GLAttributes.CRITICAL_DAMAGE, 0.15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.5, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> NINJA_HEART = ITEMS.register("ninja_heart",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.CRITICAL_DAMAGE, 0.15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(GLAttributes.STEALTH, 0.25, ADD_VALUE),
                            new AttributeContainer(Attributes.MAX_HEALTH, 3, ADD_VALUE)
                    ));
    public static final Holder<Item> OGRES_HEART = ITEMS.register("ogres_heart",
            () -> new OgresHeart(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("ogres_heart")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.MAX_HEALTH, 2, ADD_VALUE),
                            new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, 0.15, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> WARMOGS_HEART = ITEMS.register("warmogs_heart",
            () -> new WarmogsHeart(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("warmogs_heart")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.MAX_HEALTH, 6, ADD_VALUE),
                            new AttributeContainer(Attributes.MAX_HEALTH, 0.1, ADD_MULTIPLIED_TOTAL)
                    ));

    // TODO: dragon soul, roar scaring nearby mobs away and providing a short absorption buff

    public static final Holder<Item> BROKEN_CLOCK = ITEMS.register("broken_clock",
            () -> new MobEffectTickDownModifyingCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("broken_clock")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), 0.25F));

    public static final Holder<Item> INOCULUM = ITEMS.register("inoculum",
            () -> new MobEffectNullifyingCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("inoculum")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), 0.15F));

    public static final Holder<Item> THE_VACCINE = ITEMS.register("the_vaccine",
            () -> new MobEffectNullifyingCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("the_vaccine")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), 0.3F));

    public static final Holder<Item> DOSETTE_BOX = ITEMS.register("dosette_box",
            () -> new MobEffectDurationModifyingCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("dosette_box")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), 0.25F, true));


    public static final Holder<Item> JURY_NULLIFIER = ITEMS.register("jury_nullifier",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.CRITICAL_DAMAGE, 15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.ATTACK_DAMAGE, -0.9, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> DOCTORS_PRESCRIPTION = ITEMS.register("doctors_prescription",
            () -> new DoctorsPrescription(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("doctors_prescription")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> SOLDIERS_SYRINGE = ITEMS.register("soldiers_syringe",
            () -> new SoldiersSyringe(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("soldiers_syringe")
                                    .add(DescriptionType.ON_TRUE_MELEE_HIT)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.ATTACK_SPEED, 0.2, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> WAXY_CUTICLE = ITEMS.register("waxy_cuticle",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("waxy_cuticle")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> HONEY_COMB = ITEMS.register("honey_comb",
            () -> new HoneyComb(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("honey_comb")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .cooldown(5)
                                    .pop().build())));

    public static final Holder<Item> ROYAL_JELLY = ITEMS.register("royal_jelly",
            () -> new RoyalJelly(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("royal_jelly")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(3)
                                    .cooldown(2.5F)
                                    .pop().build())));

    public static final Holder<Item> EXTENDO_GRIP = ITEMS.register("extendo_grip",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 1, ADD_VALUE)));

    public static final Holder<Item> ARCHITECT_GIZMO_PACK = ITEMS.register("architect_gizmo_pack",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 2, ADD_VALUE),
                            new AttributeContainer(GLAttributes.RIGHT_CLICK_DELAY, -0.5, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> BLOCK_LAYER = ITEMS.register("block_layer",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(new AttributeContainer(GLAttributes.RIGHT_CLICK_DELAY, -1, ADD_VALUE)));

    public static final Holder<Item> PORTABLE_CONCRETE_MIXER = ITEMS.register("portable_concrete_mixer",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("portable_concrete_mixer")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(new AttributeContainer(GLAttributes.RIGHT_CLICK_DELAY, -1, ADD_VALUE)));

    public static final Holder<Item> SAFETY_HARNESS = ITEMS.register("safety_harness",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.WALL_CLIMBING, 1, ADD_VALUE),
                            new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 1, ADD_VALUE),
                            new AttributeContainer(Attributes.SAFE_FALL_DISTANCE, 0.25F, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.ATTACK_DAMAGE, -0.2F, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> TOOLBELT = ITEMS.register("toolbelt",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 1, ADD_VALUE)));

    public static final Holder<Item> HAND_OF_CREATION = ITEMS.register("hand_of_creation",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("hand_of_creation")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.RIGHT_CLICK_DELAY, Double.NEGATIVE_INFINITY, ADD_VALUE),
                            new AttributeContainer(GLAttributes.WALL_CLIMBING, 1, ADD_VALUE),
                            new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 3, ADD_VALUE)
                    ));

    public static final Holder<Item> ANCIENT_CHISEL = ITEMS.register("ancient_chisel",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(new AttributeContainer(Attributes.BLOCK_BREAK_SPEED, 0.25F, ADD_MULTIPLIED_BASE)));

    public static final Holder<Item> GYROSCOPE = ITEMS.register("gyroscope",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("gyroscope")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> SWISS_ARMY_KNIFE = ITEMS.register("swiss_army_knife",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("swiss_army_knife")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> DIGGING_CLAWS = ITEMS.register("digging_claws",
            () -> new HeldItemPredicateBlockBreakSpeedModifyingCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("digging_claws")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), ItemTags.PICKAXES));

    public static final Holder<Item> LUMBERJACKS_SHIRT = ITEMS.register("lumberjacks_shirt",
            () -> new HeldItemPredicateBlockBreakSpeedModifyingCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("lumberjacks_shirt")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), ItemTags.AXES));

    public static final Holder<Item> COMICALLY_LARGE_SHOVEL_HEAD = ITEMS.register("comically_large_shovel_head",
            () -> new HeldItemPredicateBlockBreakSpeedModifyingCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("comically_large_shovel_head")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), ItemTags.SHOVELS));

    public static final Holder<Item> BEDROCK_NECKLACE = ITEMS.register("bedrock_necklace",
            () -> new BedrockNecklace(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("bedrock_necklace")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> HAND_OF_DESTRUCTION = ITEMS.register("hand_of_destruction",
            () -> new HandOfDestruction(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("hand_of_destruction")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(4)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 2, ADD_VALUE)
                    )
    );

    public static final Holder<Item> NECTAR_GLAND = ITEMS.register("nectar_gland",
            () -> new NectarGland(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("nectar_gland")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .cooldown(2)
                                    .pop().build())));

    public static final Holder<Item> MAGNET = ITEMS.register("magnet",
            () -> new MagnetCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("magnet")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), 5, 0.35F));

    public static final Holder<Item> ELECTROMAGNET = ITEMS.register("electromagnet",
            () -> new MagnetCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("electromagnet")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()), 7, 0.5F));

    public static final Holder<Item> SUSPICIOUSLY_LARGE_HANDLE = ITEMS.register("suspiciously_large_handle",
            () -> new SuspiciouslyLargeHandle(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("suspiciously_large_handle")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));

    public static final Holder<Item> OLD_GUILLOTINE = ITEMS.register("old_guillotine",
            () -> new OldGuillotine(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("old_guillotine")
                                    .add(DescriptionType.ON_TRUE_MELEE_HIT)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> PINCUSHION = ITEMS.register("pincushion",
            () -> new Pincushion(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("pincushion")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> BEAD_OF_LIFE = ITEMS.register("bead_of_life",
            () -> new BeadOfLife(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("bead_of_life")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> HEAD_CUSHION = ITEMS.register("head_cushion",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("head_cushion")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> EYE_OF_THE_DRAGON = ITEMS.register("eye_of_the_dragon",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.CRITICAL_DAMAGE, 0.25, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 0.15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.ATTACK_SPEED, 0.15, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(GLAttributes.HEALING_RECEIVED, 0.25, ADD_VALUE)
                    ));

    public static final Holder<Item> PORTABLE_TRAMPOLINE = ITEMS.register("portable_trampoline",
            () -> new PortableTrampoline(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("portable_trampoline")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> PERFECT_CELL = ITEMS.register("perfect_cell",
            () -> new PerfectCell(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("perfect_cell")
                                    .add(DescriptionType.LORE)
                                    .lines(1).pop().add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> SCOUTS_BADGE = ITEMS.register("scouts_badge",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("scouts_badge")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> GLOBETROTTERS_BADGE = ITEMS.register("globetrotters_badge",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("globetrotters_badge")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));


    public static final Holder<Item> ANTI_CHEAT = ITEMS.register("anti_cheat",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("anti_cheat")
                                    .add(DescriptionType.MULTIPLAYER_ONLY)
                                    .pop().add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));


    public static final Holder<Item> PHANTOM_FINGER = ITEMS.register("phantom_finger",
            () -> new PhantomFinger(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 2, ADD_VALUE),
                            new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 2, ADD_VALUE)
                    ));

    public static final Holder<Item> CLOAK_OF_INVISIBILITY = ITEMS.register("cloak_of_invisibility",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("cloak_of_invisibility")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(new AttributeContainer(GLAttributes.STEALTH, 1, ADD_VALUE)));

    public static final Holder<Item> PLUNDER_CHEST = ITEMS.register("plunder_chest",
            () -> new PlunderChest(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("plunder_chest")
                                    .add(DescriptionType.ON_TRUE_MELEE_HIT)
                                    .lines(1)
                                    .cooldown(10)
                                    .pop()
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop()
                                    .build())));

    public static final Holder<Item> CROWN_OF_TYRANNY = ITEMS.register("crown_of_tyranny",
            () -> new CrownOfTyranny(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("crown_of_tyranny")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> LUCKY_INSOLES = ITEMS.register("lucky_insoles",
            () -> new LuckyInsoles(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("lucky_insoles")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> RING_OF_RINGS = ITEMS.register("ring_of_rings",
            () -> new RingOfRings(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("ring_of_rings")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> OMNICHROME_RING = ITEMS.register("omnichrome_ring",
            () -> new OmnichromeRing(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("omnichrome_ring")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(5)
                                    .pop().build())));


    public static final Holder<Item> FLIPPERS_OF_ICARUS = ITEMS.register("flippers_of_icarus",
            () -> new FlippersOfIcarus(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("flippers_of_icarus")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));

    public static final Holder<Item> POSEIDONS_BLESSING = ITEMS.register("poseidons_blessing",
            () -> new PoseidonsBlessing(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("poseidons_blessing")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));


    public static final Holder<Item> LAVA_LENSES = ITEMS.register("lava_lenses",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("lava_lenses")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));

    public static final Holder<Item> SURTRS_HORN = ITEMS.register("surtrs_horn",
            () -> new SurtrsHorn(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("surtrs_horn")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(5)
                                    .pop().build())));

    public static final Holder<Item> PORTABLE_FIRE_EXTINGUISHER = ITEMS.register("portable_fire_extinguisher",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("portable_fire_extinguisher")
                                    .add(DescriptionType.LORE).lines(1).pop().build()))
                    .withAttributes(new AttributeContainer(Attributes.BURNING_TIME, -0.5, ADD_MULTIPLIED_TOTAL)));

    public static final Holder<Item> ABYSSAL_NECKLACE = ITEMS.register("abyssal_necklace",
            () -> new AbyssalNecklace(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("abyssal_necklace")
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop().build())));

    public static final Holder<Item> DUELING_GLOVE = ITEMS.register("dueling_glove",
            () -> new DuelingGlove(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("dueling_glove")
                                    .add(DescriptionType.PASSIVE).lines(2)
                                    .pop()
                                    .build())));

    public static final Holder<Item> DRAGON_HEART = ITEMS.register("dragon_heart",
            () -> new DragonHeart(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("dragon_heart")
                                    .add(DescriptionType.LORE).lines(1)
                                    .pop()
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(new AttributeContainer(Attributes.MAX_HEALTH, 6, ADD_VALUE)));

    public static final Holder<Item> FROZEN_HEART = ITEMS.register("frozen_heart",
            () -> new FrozenHeart(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("frozen_heart")
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop()
                                    .build())));

    public static final Holder<Item> HEART_OF_GOLD = ITEMS.register("heart_of_gold",
            () -> new HeartOfGold(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("heart_of_gold")
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop()
                                    .build())));

    public static final Holder<Item> SECOND_HEART = ITEMS.register("second_heart",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(new AttributeContainer(Attributes.MAX_HEALTH, 8, ADD_VALUE)));

    public static final Holder<Item> NANO_MACHINES = ITEMS.register("nano_machines",
            () -> new NanoMachines(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("nano_machines")
                                    .add(DescriptionType.ACTIVE)
                                    .keybind(CuriositiesKeymappings.ACTIVE_ABILITY)
                                    .cooldown(90)
                                    .lines(1)
                                    .pop().build())));


    public static final Holder<Item> RESPLENDENT_FEATHER = ITEMS.register("resplendent_feather",
            () -> new ResplendentFeather(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("resplendent_feather")
                                    .add(DescriptionType.LORE).lines(1).pop()
                                    .add(DescriptionType.PASSIVE).lines(2)
                                    .pop()
                                    .build())));

    public static final Holder<Item> BRAIN_ROT = ITEMS.register("brain_rot",
            () -> new BrainRot(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("brain_rot")
                                    .add(DescriptionType.ON_TRUE_MELEE_HIT).lines(1)
                                    .pop()
                                    .build())));


    public static final Holder<Item> ROADRUNNERS_RING = ITEMS.register("roadrunners_ring",
            () -> new RoadrunnersRing(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("roadrunners_ring")
                                    .add(DescriptionType.LORE).lines(1).pop()
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(new AttributeContainer(Attributes.MOVEMENT_SPEED, 15 * 0.2, ADD_MULTIPLIED_TOTAL)));


    public static final Holder<Item> MINIATURE_ANCHOR = ITEMS.register("miniature_anchor",
            () -> new MiniatureAnchor(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("miniature_anchor")
                                    .add(DescriptionType.PASSIVE).lines(2)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, 0.25, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(NeoForgeMod.SWIM_SPEED, -0.5, ADD_MULTIPLIED_TOTAL)
                    ));


    public static final Holder<Item> OMEGA_GAUNTLET = ITEMS.register("omega_gauntlet",
            () -> new OmegaGauntlet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("omega_gauntlet")
                                    .add(DescriptionType.ON_HIT)
                                    .lines(1)
                                    .pop().build()
                    )).withAttributes(
                    new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 1.5, ADD_VALUE)
            )
    );


    public static final Holder<Item> ENDER_DRAGON_STEM_CELLS = ITEMS.register("ender_dragon_stem_cells",
            () -> new CuriositiesCurioItem(new Item.Properties()).withAttributes(
                    new AttributeContainer(Attributes.ATTACK_SPEED, 0.15, ADD_MULTIPLIED_TOTAL),
                    new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.15, ADD_MULTIPLIED_TOTAL),
                    new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 0.15, ADD_MULTIPLIED_TOTAL),
                    new AttributeContainer(Attributes.MAX_HEALTH, 0.15, ADD_MULTIPLIED_TOTAL),
                    new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, 0.15, ADD_MULTIPLIED_TOTAL),
                    new AttributeContainer(Attributes.SCALE, 0.5, ADD_MULTIPLIED_TOTAL)
            ));


    public static final Holder<Item> LUCKY_BOOKMARK = ITEMS.register("lucky_bookmark",
            () -> new CuriositiesCurioItem(new Item.Properties()).withAttributes(
                    new AttributeContainer(Attributes.LUCK, 1, ADD_VALUE)
            ));


    public static final Holder<Item> CENSORSHIP_CLOAK = ITEMS.register("censorship_cloak",
            () -> new CensorshipCloak(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("censorship_cloak")
                                    .add(DescriptionType.MULTIPLAYER_ONLY)
                                    .pop().add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> REDACTING_TAPE = ITEMS.register("redacting_tape",
            () -> new RedactingTape(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("redacting_tape")
                                    .add(DescriptionType.MULTIPLAYER_ONLY)
                                    .pop().add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> INCOGNITO_PENDANT = ITEMS.register("incognito_pendant",
            () -> new IncognitoPendant(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("incognito_pendant")
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop().build())));

    public static final Holder<Item> VENDETTA_MASK = ITEMS.register("vendetta_mask",
            () -> new VendettaMask(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("vendetta_mask")
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(new AttributeContainer(GLAttributes.STEALTH, 0.75, ADD_VALUE)));

    public static final Holder<Item> REGAL_EMERALD = ITEMS.register("regal_emerald",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("regal_emerald")
                                    .add(DescriptionType.PASSIVE).lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(new AttributeContainer(GLAttributes.VILLAGER_REPUTATION, 100, ADD_VALUE)));

    public static final Holder<Item> SUSPICIOUS_LOOKING_ROCK = ITEMS.register("suspicious_looking_rock",
            () -> new SuspiciousLookingRock(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("suspicious_looking_rock")
                                    .add(DescriptionType.LORE).lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.ARMOR, 0.05, ADD_VALUE),
                            new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.025, ADD_VALUE)
                    ));

    public static final Holder<Item> BASKETBALL_ANKLET = ITEMS.register("basketball_anklet",
            () -> new AnkletCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.1, ADD_VALUE),
                            new AttributeContainer(Attributes.JUMP_STRENGTH, 1, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> BIG_STEPPA = ITEMS.register("big_steppa",
            () -> new AnkletCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("big_steppa")
                                    .add(DescriptionType.LORE).lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.1, ADD_VALUE),
                            new AttributeContainer(Attributes.STEP_HEIGHT, 1.5, ADD_VALUE)
                    ));

    public static final Holder<Item> GLADIATORS_ANKLET = ITEMS.register("gladiators_anklet",
            () -> new GladiatorsAnklet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("gladiators_anklet")
                                    .add(DescriptionType.ON_SUCCESSFUL_DODGE)
                                    .lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.1, ADD_VALUE),
                            new AttributeContainer(Attributes.ARMOR, 2, ADD_VALUE)
                    ));

    public static final Holder<Item> IRONCLAD_ANKLET = ITEMS.register("ironclad_anklet",
            () -> new IroncladAnklet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("ironclad_anklet")
                                    .add(DescriptionType.ON_SUCCESSFUL_DODGE)
                                    .lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.1, ADD_VALUE),
                            new AttributeContainer(Attributes.MOVEMENT_SPEED, -0.1, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.ARMOR, 0.1, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> FIERY_ANKLET = ITEMS.register("fiery_anklet",
            () -> new FieryAnklet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("fiery_anklet")
                                    .add(DescriptionType.ON_SUCCESSFUL_DODGE)
                                    .lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.1, ADD_VALUE)
                    ));
    public static final Holder<Item> ANKLE_MONITOR = ITEMS.register("ankle_monitor",
            () -> new AnkleMonitor(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("ankle_monitor")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.35, ADD_VALUE),
                            new AttributeContainer(GLAttributes.VILLAGER_REPUTATION, -1, ADD_MULTIPLIED_TOTAL)
                    ));
    public static final Holder<Item> REDSTONE_ANKLET = ITEMS.register("redstone_anklet",
            () -> new AnkletCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.1, ADD_VALUE),
                            new AttributeContainer(Attributes.ATTACK_SPEED, 0.15, ADD_MULTIPLIED_TOTAL)
                    ));
    public static final Holder<Item> BLAZE_ANKLET = ITEMS.register("blaze_anklet",
            () -> new BlazeAnklet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("blaze_anklet")
                                    .add(DescriptionType.ON_SUCCESSFUL_DODGE)
                                    .lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.125, ADD_VALUE)
                    ));
    public static final Holder<Item> GUARDIAN_ANKLET = ITEMS.register("guardian_anklet",
            () -> new GuardianAnklet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("guardian_anklet")
                                    .add(DescriptionType.ON_SUCCESSFUL_DODGE)
                                    .lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.125, ADD_VALUE)
                    ));
    public static final Holder<Item> ENDER_ANKLET = ITEMS.register("ender_anklet",
            () -> new EnderAnklet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("ender_anklet")
                                    .add(DescriptionType.ON_SUCCESSFUL_DODGE)
                                    .lines(1)
                                    .pop()
                                    .build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.15, ADD_VALUE)
                    ));
    public static final Holder<Item> EMPTY_ANKLET = ITEMS.register("empty_anklet",
            () -> new AnkletCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.DODGE_CHANCE, 0.05, ADD_VALUE)
                    ));

    public static final Holder<Item> MINING_GOGGLES = ITEMS.register("mining_goggles",
            () -> new MiningGoggles(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("mining_goggles")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop()
                                    .build())));
    public static final Holder<Item> MOLTEN_MINERS_GOGGLES = ITEMS.register("molten_miners_goggles",
            () -> new MoltenMinersGoggles(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("molten_miners_goggles")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop()
                                    .build())));

    public static final Holder<Item> XRAY_GOGGLES = ITEMS.register("xray_goggles",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("xray_goggles")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> OMNISCIENT_GLASSES = ITEMS.register("omniscient_glasses",
            () -> new OmniscientGlasses(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("omniscient_glasses")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));

    public static final Holder<Item> CLOUD_IN_A_BOTTLE = ITEMS.register("cloud_in_a_bottle",
            () -> new CloudInABottle(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("cloud_in_a_bottle")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> TSUNAMI_IN_A_BOTTLE = ITEMS.register("tsunami_in_a_bottle",
            () -> new TsunamiInABottle(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("tsunami_in_a_bottle")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> BLIZZARD_IN_A_BOTTLE = ITEMS.register("blizzard_in_a_bottle",
            () -> new BlizzardInABottle(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("blizzard_in_a_bottle")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> SANDSTORM_IN_A_BOTTLE = ITEMS.register("sandstorm_in_a_bottle",
            () -> new SandstormInABottle(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("sandstorm_in_a_bottle")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> SHINY_RED_BALLOON = ITEMS.register("shiny_red_balloon",
            () -> new CuriositiesCurioItem(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("shiny_red_balloon")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(new AttributeContainer(Attributes.JUMP_STRENGTH, 0.33, ADD_MULTIPLIED_TOTAL)));

    public static final Holder<Item> OBSIDIAN_SKULL = ITEMS.register("obsidian_skull",
            () -> new ObsidianSkull(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("obsidian_skull")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(new AttributeContainer(Attributes.BURNING_TIME, -0.5, ADD_MULTIPLIED_TOTAL)));

    public static final Holder<Item> COBALT_SHIELD = ITEMS.register("cobalt_shield",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, Double.POSITIVE_INFINITY, ADD_VALUE),
                            new AttributeContainer(Attributes.ARMOR, 2, ADD_VALUE)
                    ));

    public static final Holder<Item> OBSIDIAN_SHIELD = ITEMS.register("obsidian_shield",
            () -> new ObsidianSkull(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("obsidian_shield")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, Double.POSITIVE_INFINITY, ADD_VALUE),
                            new AttributeContainer(Attributes.BURNING_TIME, -0.5, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> LUCKY_HORSESHOE = ITEMS.register("lucky_horseshoe",
            () -> new CuriositiesCurioItem(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(Attributes.LUCK, 1, ADD_VALUE),
                            new AttributeContainer(Attributes.SAFE_FALL_DISTANCE, Double.POSITIVE_INFINITY, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.FALL_DAMAGE_MULTIPLIER, Double.NEGATIVE_INFINITY, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> OBSIDIAN_HORSESHOE = ITEMS.register("obsidian_horseshoe",
            () -> new ObsidianSkull(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("obsidian_horseshoe")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(Attributes.LUCK, 1, ADD_VALUE),
                            new AttributeContainer(Attributes.SAFE_FALL_DISTANCE, Double.POSITIVE_INFINITY, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.FALL_DAMAGE_MULTIPLIER, Double.NEGATIVE_INFINITY, ADD_MULTIPLIED_TOTAL),
                            new AttributeContainer(Attributes.BURNING_TIME, -0.5, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> CROSS_NECKLACE = ITEMS.register("cross_necklace",
            () -> new CrossNecklace(new Item.Properties())
                    .withAttributes(
                            new AttributeContainer(GLAttributes.INVULNERABILITY_TICKS, 1, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> PANIC_NECKLACE = ITEMS.register("panic_necklace",
            () -> new PanicNecklace(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("panic_necklace")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> CROSSED_HEART_NECKLACE = ITEMS.register("crossed_heart_necklace",
            () -> new CrossedHeartNecklace(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("crossed_heart_necklace")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build()))
                    .withAttributes(
                            new AttributeContainer(GLAttributes.INVULNERABILITY_TICKS, 1, ADD_MULTIPLIED_TOTAL)
                    ));

    public static final Holder<Item> IRON_GUT = ITEMS.register("iron_gut",
            () -> new IronGut(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("iron_gut")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(2)
                                    .pop().build())));

    public static final Holder<Item> THE_WORLD = ITEMS.register("the_world",
            () -> new TheWorld(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("the_world")
                                    .add(DescriptionType.LORE)
                                    .lines(1)
                                    .pop()
                                    .add(DescriptionType.ACTIVE)
                                    .lines(1)
                                    .cooldown(180)
                                    .pop().build())));

    public static final Holder<Item> VILLAGER_DICTIONARY = ITEMS.register("villager_dictionary",
            () -> new VillagerDictionary(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("villager_dictionary")
                                    .add(DescriptionType.LORE)
                                    .lines(1)
                                    .pop()
                                    .add(DescriptionType.PASSIVE)
                                    .lines(3)
                                    .pop().build()))
                    .withAttributes(new AttributeContainer(GLAttributes.VILLAGER_REPUTATION, 50, ADD_VALUE)));

    public static final Holder<Item> GOLDEN_FORK = ITEMS.register("golden_fork",
            () -> new GoldenFork(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("golden_fork")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> PRISTINE_NAPKIN = ITEMS.register("pristine_napkin",
            () -> new PristineNapkin(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("pristine_napkin")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> EMPTY_PLATE = ITEMS.register("empty_plate",
            () -> new EmptyPlate(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("empty_plate")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(1)
                                    .pop().build())));

    public static final Holder<Item> GOURMANDS_DINING_SET = ITEMS.register("gourmands_dining_set",
            () -> new GourmandsDiningSet(new Item.Properties()
                    .component(CuriositiesDataComponents.ITEM_DESCRIPTIONS,
                            ItemDescriptions.forItem("gourmands_dining_set")
                                    .add(DescriptionType.PASSIVE)
                                    .lines(3)
                                    .pop().build())));

}

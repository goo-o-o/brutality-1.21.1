package com.goo.brutality.client.datagen;


import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.items.BrutalityRageCurioItem;
import com.goo.brutality.common.registry.BrutalityItems;
import com.goo.brutality.common.registry.BrutalityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

;

public class BrutalityItemTagProvider extends ItemTagsProvider {


    public BrutalityItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        addCurioTags();
        addAugmentTags();
        addMathTags();
        addMagicTags();
        addRageTags();
        addGastronomistTags();

//        this.tag(ItemTags.CANDLES).add(BrutalityBlocks.MANA_CANDLE.asItem());

        tag(BrutalityTags.Items.TWO_HANDED)
                .add(BrutalityItems.Weapon.LightGreatsword.PLINKO_BLADE.value())
                .add(BrutalityItems.Weapon.LightGreatsword.BLADE_OF_THE_RUINED_KING.value());
    }

    private void addAnkletTags() {
        this.tag(BrutalityTags.Items.ANKLET);
//                .add(
//                BrutalityItems.DAVYS_ANKLET,
//                BrutalityItems.ANKLET_OF_THE_IMPRISONED,
//                BrutalityItems.DEBUG_ANKLET,
//                BrutalityItems.ANKLE_MONITOR,
//                BrutalityItems.REDSTONE_ANKLET,
//                BrutalityItems.DEVILS_ANKLET,
//                BrutalityItems.BASKETBALL_ANKLET,
//                BrutalityItems.EMERALD_ANKLET,
//                BrutalityItems.RUBY_ANKLET,
//                BrutalityItems.TOPAZ_ANKLET,
//                BrutalityItems.SAPPHIRE_ANKLET,
//                BrutalityItems.ONYX_ANKLET,
//                BrutalityItems.ULTRA_DODGE_ANKLET,
//                BrutalityItems.GUNDALFS_ANKLET,
//                BrutalityItems.TRIAL_ANKLET,
//                BrutalityItems.SUPER_DODGE_ANKLET,
//                BrutalityItems.GNOME_KINGS_ANKLET,
//                BrutalityItems.SHARPNESS_ANKLET,
//                BrutalityItems.ANKLENT,
//                BrutalityItems.EMPTY_ANKLET,
//                BrutalityItems.BIG_STEPPA,
//                BrutalityItems.FIERY_ANKLET,
//                BrutalityItems.WINDSWEPT_ANKLET,
//                BrutalityItems.CONDUCTITE_ANKLET,
//                BrutalityItems.SACRED_SPEED_ANKLET,
//                BrutalityItems.VIRENTIUM_ANKLET,
//                BrutalityItems.COSMIC_ANKLET,
//                BrutalityItems.VOID_ANKLET,
//                BrutalityItems.NYXIUM_ANKLET,
//                BrutalityItems.GLADIATORS_ANKLET,
//                BrutalityItems.IRONCLAD_ANKLET,
//                BrutalityItems.BLOOD_CLOT_ANKLET);
    }

    private void addCharmTags() {
        this.tag(CuriosTags.CHARM).add(
//                BrutalityItems.PLUNDER_CHEST,
//                BrutalityItems.SUSPICIOUS_ROCK,
//                BrutalityItems.CENSORSHIP,
//                BrutalityItems.REDACTING_TAPE,
//                BrutalityItems.RESPLENDENT_FEATHER,
//                BrutalityItems.CELESTIAL_STARBOARD,
//                BrutalityItems.YATA_NO_KAGAMI,
//                BrutalityItems.PORTABLE_QUANTUM_THINGAMABOB,
//                BrutalityItems.DAEMONIUM_WHETSTONE,
//                BrutalityItems.OMNIDIRECTIONAL_MOVEMENT_GEAR,
//                BrutalityItems.ERROR_404,
//                BrutalityItems.KINETIC_COMPENSATOR,
//                BrutalityItems.WAY_OF_THE_WIND,
//                BrutalityItems.SOLDIERS_SYRINGE,
//                BrutalityItems.INCOGNITO_MODE,
//                BrutalityItems.EARTHEN_BLESSING,
//                BrutalityGastronomyItems.ESSENTIAL_OILS,
//                BrutalityItems.OLD_GUILLOTINE,
//                BrutalityItems.PINCUSHION,
//                BrutalityItems.DEADSHOT_BROOCH,
//                BrutalityItems.ENERGY_FOCUSER,
//                BrutalityGastronomyItems.CHOCOLATE_BAR,
//                BrutalityItems.DAEMONIUM_SEWING_KIT,
//                BrutalityItems.KNUCKLE_WRAPS,
//                BrutalityItems.PENCIL_SHARPENER,
//                BrutalityItems.TARGET_CUBE,
//                BrutalityItems.ESCAPE_KEY,
//                BrutalityItems.BLOOD_PACK,
//                BrutalityItems.EMERGENCY_MEETING,
//                BrutalityItems.PAPER_AIRPLANE,
//                BrutalityItems.MIRACLE_CURE,
//                BrutalityItems.LUCKY_INSOLES,
//                BrutalityItems.DIVINE_IMMOLATION,
//                BrutalityItems.LIGHT_SWITCH,
//                BrutalityItems.VINDICATOR_STEROIDS,
//                BrutalityItems.WIRE_CUTTERS,
//                BrutalityItems.SILVER_RESPAWN_CARD,
//                BrutalityItems.DIAMOND_RESPAWN_CARD,
//                BrutalityItems.EVIL_KING_RESPAWN_CARD,
//                BrutalityItems.SILVER_BOOSTER_PACK,
//                BrutalityItems.DIAMOND_BOOSTER_PACK,
//                BrutalityItems.EVIL_KING_BOOSTER_PACK,
//                BrutalityItems.PORTABLE_TRAMPOLINE,
//                BrutalityItems.ELBOW_GREASE,
//                BrutalityItems.QUANTUM_LUBRICANT,
//                BrutalityItems.AEROPHOBIC_NANOCOATING,
//                BrutalityItems.INERTIA_BOOSTER,
//                BrutalityItems.PETROLEUM_JELLY,
//                BrutalityItems.ZEPHYR_IN_A_BOTTLE,
//                BrutalityItems.BLOOD_STONE,
//                BrutalityItems.RAGE_STONE,
//                BrutalityItems.RAGE_BAIT,
//                BrutalityItems.PAIN_CATALYST,
                BrutalityItems.Curio.Rage.GRUDGE_TOTEM.value(),
//                BrutalityItems.RAMPAGE_CLOCK,
//                BrutalityItems.SPITE_SHARD,
                BrutalityItems.Curio.Rage.MECHANICAL_AORTA.value(),
//                BrutalityItems.HATE_SIGIL,
//                BrutalityItems.FURY_BATTERY,
//                BrutalityItems.SEROTONIN_PILLS,
                BrutalityItems.Curio.Rage.WRATH.value(),
//                BrutalityItems.STRESS_PILLS,
//                BrutalityItems.ENDER_DRAGON_STEM_CELLS,
//                BrutalityItems.BOILING_BLOOD,
//                BrutalityItems.PACK_OF_CIGARETTES,
                BrutalityItems.Curio.Coin.MIRRORED_MINT.value(),
                BrutalityItems.Curio.Coin.REVERSE_COIN.value(),
//                BrutalityItems.PRIDE,
//                BrutalityItems.ENVY,
//                BrutalityItems.GLUTTONY,
//                BrutalityItems.LUST,
//                BrutalityItems.SLOTH,
//                BrutalityItems.GREED,
//                BrutalityItems.BROKEN_CLOCK,
//                BrutalityItems.SHATTERED_CLOCK,
//                BrutalityItems.SUNDERED_CLOCK,
//                BrutalityItems.TIMEKEEPERS_CLOCK,
//                BrutalityItems.THE_CLOCK_OF_FROZEN_TIME,
//                BrutalityItems.BEAD_OF_LIFE,
//                BrutalityItems.LUCKY_BOOKMARK,
//                BrutalityItems.DIVERGENT_RECURSOR,
//                BrutalityItems.CONVERGENT_RECURSOR,
//                BrutalityItems.INFINITE_RECURSOR,
//                BrutalityItems.SOUL_STONE,
//                BrutalityItems.SCRIBES_INDEX,
//                BrutalityItems.PRISMATIC_ORB,
//                BrutalityItems.ONYX_IDOL,
//                BrutalityItems.ECHO_CHAMBER,
//                BrutalityItems.CONSERVATIVE_CONCOCTION,
//                BrutalityItems.EMERGENCY_FLASK,
//                BrutalityItems.BLACK_HOLE_ORB,
//                BrutalityItems.FORBIDDEN_ORB,
//                BrutalityItems.BLOOD_ORB,
//                BrutalityItems.PROFANUM_REACTOR,
//                BrutalityItems.MANA_INFUSED_WHETSTONE,
//                BrutalityItems.DECK_OF_CARDS,
//                BrutalityGastronomyItems.PEPPER_SHAKER,
//                BrutalityGastronomyItems.SALT_SHAKER,
//                BrutalityGastronomyItems.HOT_SAUCE,
//                BrutalityGastronomyItems.STRAWBERRY_SMOOTHIE,
//                BrutalityGastronomyItems.BLUEBERRY_SMOOTHIE,
//                BrutalityGastronomyItems.MANGO_SMOOTHIE,
//                BrutalityGastronomyItems.BANANA_SMOOTHIE,
//                BrutalityGastronomyItems.MIXED_BERRY_SMOOTHIE,
//                BrutalityGastronomyItems.TOMATO_SAUCE,
//                BrutalityGastronomyItems.CHEESE_SAUCE,
//                BrutalityGastronomyItems.PIZZA_SLOP,
//                BrutalityGastronomyItems.ITALIAN_CLASSIC,
//                BrutalityGastronomyItems.UVOGRE_STEAK,
//                BrutalityGastronomyItems.SHADOWFLAME_SEARED_STEAK,
//                BrutalityGastronomyItems.OLIVE_OIL,
//                BrutalityGastronomyItems.EXTRA_VIRGIN_OLIVE_OIL,
//                BrutalityGastronomyItems.SALT_AND_PEPPER,
//                BrutalityGastronomyItems.FRIDGE,
//                BrutalityGastronomyItems.SMART_FRIDGE,
//                BrutalityGastronomyItems.INDUSTRIAL_FREEZER,
//                BrutalityGastronomyItems.PICKLE_JAR,
//                BrutalityGastronomyItems.MORTAR_AND_PESTLE,
//                BrutalityGastronomyItems.THE_SMOKEHOUSE,
//                BrutalityGastronomyItems.CONVECTION_SMOKER,
//                BrutalityGastronomyItems.APPLE_CIDER_VINEGAR,
//                BrutalityGastronomyItems.SMOKED_PAPRIKA,
//                BrutalityGastronomyItems.BBQ_RUB,
//                BrutalityGastronomyItems.BBQ_SAUCE,
//                BrutalityGastronomyItems.COMMERCIALIZED_BBQ_SEASONING,
//                BrutalityGastronomyItems.RAINBOW_SPRINKLES,
//                BrutalityGastronomyItems.SMOKE_STONE,
//                BrutalityGastronomyItems.SUGAR_GLAZE,
//                BrutalityGastronomyItems.BAMBOO_STEAMER,
//                BrutalityGastronomyItems.SEARED_SUGAR_BROOCH,
//                BrutalityGastronomyItems.DUNKED_DONUT,
//                BrutalityGastronomyItems.LOLLIPOP_OF_ETERNITY,
//                BrutalityItems.SELF_REPAIR_NEXUS,
//                BrutalityItems.BLOOD_CHALICE,
//                BrutalityItems.HEMOGRAFT_NEEDLE,
//                BrutalityItems.BLOODSTAINED_MIRROR,
//                BrutalityItems.VAMPIRIC_TALISMAN,
//                BrutalityItems.PI,
//                BrutalityItems.EULERS_NUMBER,
//                BrutalityItems.SINE,
//                BrutalityItems.COSINE,
//                BrutalityItems.ADDITION_CHARM,
//                BrutalityItems.SUBTRACTION,
//                BrutalityItems.FRACTION,
//                BrutalityItems.FLOOR,
//                BrutalityItems.CEIL,
//                BrutalityItems.MULTIPLICATION,
//                BrutalityItems.DIVISION,
//                BrutalityItems.SUM,
//                BrutalityItems.HYPERBOLIC_FEATHER,
//                BrutalityItems.MIRROR_OF_DIVERGENCE,
                BrutalityItems.Curio.POSEIDONS_BLESSING.value(),
                BrutalityItems.Curio.HYDROPHOBIC_NANOCOATING.value(),
                BrutalityItems.Curio.Coin.NUMISMATIC_CATALYST.value()
//                BrutalityItems.CRYPTO_WALLET,
//                BrutalityItems.PORTABLE_MINING_RIG,
//                BrutalityItems.CARTON_OF_PRISM_SOLUTION_MILK);
        );
    }

    private void addBeltTags() {
        this.tag(CuriosTags.BELT).add(
//                BrutalityItems.SCIENTIFIC_CALCULATOR,
//                BrutalityItems.BRUTESKIN_BELT,
//                BrutalityItems.MINIATURE_ANCHOR,
                BrutalityItems.Curio.Coin.OVERDRAW_POUCH.value(),
                BrutalityItems.Curio.Coin.THE_GLUTTONS_PURSE.value(),
                BrutalityItems.Curio.SURTRS_HORN.value(),
                BrutalityItems.Curio.Rage.BLOODBOIL_BOMB.value(),
//                BrutalityItems.WARPSLICE_SCABBARD,
                BrutalityItems.Curio.Coin.MOBIUS_STRIP.value()
//                BrutalityItems.POOL_FLOAT
        );
    }

    private void addHandTags() {
        this.tag(CuriosTags.HANDS).add(
//                BrutalityGastronomyItems.GOLDEN_DELIGHT,
                BrutalityItems.Curio.Coin.HAND_OF_MIDAS.value(),
                BrutalityItems.Curio.Coin.GLOVE_OF_GREED.value(),
//                BrutalityItems.NANOMACHINES,
//                BrutalityItems.AQUEOUS_TUNER,
//                BrutalityItems.DUELING_GLOVE,
//                BrutalityGastronomyItems.BUTTER_GAUNTLETS,
//                BrutalityItems.GOOD_BOOK,
//                BrutalityItems.THE_OATH,
//                BrutalityItems.EYE_OF_THE_DRAGON,
//                BrutalityItems.JURY_NULLIFIER,
//                BrutalityItems.STYGIAN_CHAIN,
//                BrutalityItems.PHANTOM_FINGER,
                BrutalityItems.Curio.Rage.ANGER_MANAGEMENT.value(),
                BrutalityItems.Curio.Rage.BLOOD_PULSE_KNUCKLES.value(),
//                BrutalityItems.CROWBAR,
//                BrutalityItems.DUMBBELL,
//                BrutalityItems.HANDCUFFS,
//                BrutalityItems.BLOOD_PULSE_GAUNTLETS,
                BrutalityItems.Curio.Rage.BROKEN_CONTROLLER.value(),
                BrutalityItems.Curio.Rage.WIRELESS_CONTROLLER.value()
//                BrutalityItems.OMEGA_GAUNTLET,
//                BrutalityItems.PERFECT_CELL,
//                BrutalityItems.SUSPICIOUSLY_LARGE_HANDLE,
//                BrutalityGastronomyItems.ICE_CREAM_SANDWICH,
//                BrutalityItems.DELICATE_JEWEL,
//                BrutalityItems.GLOBETROTTERS_BADGE,
//                BrutalityItems.SCOUTS_BADGE,
//                BrutalityItems.APPRENTICES_MANUAL_TO_BASIC_MULTICASTING,
//                BrutalityItems.WIZARDS_GUIDEBOOK_TO_ADVANCED_MULTICASTING,
//                BrutalityItems.ARCHMAGES_THESIS_TO_MASTERFUL_MULTICASTING,
//                BrutalityItems.PARAGON_OF_THE_FIRST_MAGE,
//                BrutalityItems.FIRE_EXTINGUISHER
        );
    }

    private void addHeadTags() {
        this.tag(CuriosTags.HEAD).add(
//                BrutalityItems.MAGICIANS_TOP_HAT,
//                BrutalityItems.HEAD_CUSHION,
                BrutalityItems.Curio.Coin.SWALLOWED_PENNY.value(),
                BrutalityItems.Curio.LAVA_LENSES.value(),
                BrutalityItems.Curio.Rage.MONOCLE_OF_BRUTALITY.value()
//                BrutalityItems.SERAPHIM_HALO,
//                BrutalityItems.GOLDEN_HEADBAND,
//                BrutalityItems.WOOLY_BLINDFOLD,
//                BrutalityItems.TRIAL_GUARDIAN_EYEBROWS,
//                BrutalityItems.TRIAL_GUARDIAN_HANDS,
//                BrutalityItems.SOLAR_SYSTEM,
//                BrutalityItems.MASK_OF_MADNESS,
//                BrutalityItems.CROWN_OF_DOMINATION,
//                BrutalityItems.FACE_PIE,
//                BrutalityItems.FALLEN_ANGELS_HALO,
//                BrutalityItems.AIR_JORDAN_EARRINGS,
//                BrutalityItems.VAMPIRE_FANG,
//                BrutalityItems.PROGENITORS_EARRINGS,
//                BrutalityItems.HELL_SPECS,
//                BrutalityItems.LENS_MAKERS_GLASSES,
//                BrutalityItems.SANGUINE_SPECTACLES,
//                BrutalityItems.SCOPE_GOGGLES,
//                BrutalityItems.CROWN_OF_TYRANNY,
//                BrutalityItems.CRITICAL_THINKING,
//                BrutalityItems.BRAIN_ROT,
//                BrutalityItems.EYE_FOR_VIOLENCE,
//                BrutalityItems.SAD_UVOGRE,
//                BrutalityItems.SOLAR_LENS,
//                BrutalityItems.LUNAR_LENS
        );
    }

    private void addHeartTags() {
        this.tag(BrutalityTags.Items.HEART).add(
//                BrutalityItems.RUNE_OF_DELTA,
//                BrutalityItems.OVERCLOCKED_CORE,
//                BrutalityItems.DRAGONHEART,
//                BrutalityItems.UVOGRE_HEART,
                BrutalityItems.Curio.Coin.HEART_OF_THE_HOARDER.value()
//                BrutalityItems.ZOMBIE_HEART,
//                BrutalityItems.FROZEN_HEART,
//                BrutalityItems.SECOND_HEART,
//                BrutalityItems.HEART_OF_WRATH,
//                BrutalityItems.BROKEN_HEART,
//                BrutalityItems.GLASS_HEART,
//                BrutalityItems.HEART_OF_GOLD,
//                BrutalityItems.NINJA_HEART,
//                BrutalityGastronomyItems.BOX_OF_CHOCOLATES,
//                BrutalityItems.BRUTAL_HEART,
//                TerramityModItems.CRYSTAL_HEART
        );
    }

    private void addNecklaceTags() {
        this.tag(CuriosTags.NECKLACE).add(
//                BrutalityItems.ABYSSAL_NECKLACE,
//                BrutalityItems.BLOOD_HOWL_PENDANT,
//                BrutalityItems.HELLSPEC_TIE,
//                BrutalityItems.KNIGHTS_PENDANT,
//                BrutalityItems.GAMBLERS_CHAIN,
//                BrutalityItems.HEMOMATIC_LOCKET,
//                BrutalityItems.BLACK_MATTER_NECKLACE,
//                BrutalityItems.FUZZY_DICE,
//                BrutalityGastronomyItems.CARAMEL_CRUNCH_MEDALLION,
                BrutalityItems.Curio.Rage.TOXICOSIS_PENDANT.value(),
                BrutalityItems.Curio.Rage.DEMON_BEADS.value()
        );
    }

    private void addRingTags() {
        this.tag(CuriosTags.RING).add(
                BrutalityItems.Curio.Rage.FURY_BAND.value(),
//                BrutalityItems.MICROBLADE_BAND,
//                BrutalityItems.RING_OF_MANA,
//                BrutalityItems.RING_OF_MANA_PLUS,
                BrutalityItems.Curio.Coin.MINT_MASTERS_SIGNET.value(),
//                BrutalityItems.SANGUINE_SIGNET,
//                BrutalityItems.AQUA_RULER,
                BrutalityItems.Curio.RING_OF_RINGS.value(),
                BrutalityItems.Curio.RING_OF_DESPAIR.value(),
                BrutalityItems.Curio.OMNICHROME_RING.value()
//                BrutalityGastronomyItems.ROCK_CANDY_RING,
//                BrutalityItems.ROAD_RUNNERS_RING
        );
    }

    private void addFeetTags() {
        this.tag(BrutalityTags.Items.FEET).add(
//                BrutalityItems.FLAME_WALKER,
//                BrutalityItems.FLAME_STOMPER,
//                BrutalityItems.SEISMIC_STOMPERS,
//                BrutalityItems.SALAMANDERS_STRIDERS,
//                BrutalityItems.AMPHIBIAN_BOOTS,
//                BrutalityItems.LAVA_WALKERS,
//                BrutalityItems.WATER_WALKERS,
//                BrutalityItems.VOID_STEPPERS,
//                BrutalityItems.UMBRAL_TIPTOES,
//                BrutalityItems.PLATED_STEELCAPS,
//                BrutalityItems.SLIPSTREAM_TRACERS,
                BrutalityItems.Curio.FLIPPERS_OF_ICARUS.value()
//                BrutalityItems.CONSTRUCTION_BOOTS,
//                BrutalityItems.VECTOR_STABILIZER,
//                BrutalityItems.ICE_SKATES,
//                BrutalityItems.HIGH_HEALS
        );
    }

    private void addRageTags() {
        IntrinsicTagAppender<Item> tagBuilder = tag(BrutalityTags.Items.RAGE_ITEMS);

        for (ResourceLocation key : BuiltInRegistries.ITEM.keySet()) {
            if (key.getNamespace().equals(Brutality.MOD_ID)) {
                Item item = BuiltInRegistries.ITEM.get(key);
                if (item instanceof BrutalityRageCurioItem) {
                    tagBuilder.add(item);
                }
            }
        }
    }

    private void addGastronomistTags() {
        this.tag(BrutalityTags.Items.GASTRONOMIST_ITEMS);
//                .add(
//                BrutalityGastronomyItems.GOLDEN_DELIGHT,
//                BrutalityItems.SPATULA_HAMMER,
//                BrutalityItems.THE_GOLDEN_SPATULA_HAMMER,
//                BrutalityItems.IRON_KNIFE,
//                BrutalityItems.GOLD_KNIFE,
//                BrutalityItems.DIAMOND_KNIFE,
//                BrutalityItems.VOID_KNIFE,
//                BrutalityItems.MELONCHOLY_SWORD,
//                BrutalityItems.APPLE_CORE_LANCE,
//                BrutalityItems.CHOPSTICK_STAFF,
//                BrutalityItems.BAMBOO_STAFF,
//                BrutalityItems.FRYING_PAN,
//                BrutalityItems.POTATO_MASHER,
//                BrutalityGastronomyItems.WHISK_HAMMER,
//                BrutalityGastronomyItems.STRAWBERRY_SMOOTHIE,
//                BrutalityGastronomyItems.BLUEBERRY_SMOOTHIE,
//                BrutalityGastronomyItems.MANGO_SMOOTHIE,
//                BrutalityGastronomyItems.BANANA_SMOOTHIE,
//                BrutalityGastronomyItems.MIXED_BERRY_SMOOTHIE,
//                BrutalityGastronomyItems.APPLE_CIDER_VINEGAR,
//                BrutalityGastronomyItems.SMOKED_PAPRIKA,
//                BrutalityGastronomyItems.BBQ_RUB,
//                BrutalityGastronomyItems.BBQ_SAUCE,
//                BrutalityGastronomyItems.COMMERCIALIZED_BBQ_SEASONING,
//                BrutalityGastronomyItems.PEPPER_SHAKER,
//                BrutalityGastronomyItems.SALT_SHAKER,
//                BrutalityGastronomyItems.SALT_AND_PEPPER,
//                BrutalityGastronomyItems.SMOKE_STONE,
//                BrutalityGastronomyItems.THE_SMOKEHOUSE,
//                BrutalityGastronomyItems.BAMBOO_STEAMER,
//                BrutalityGastronomyItems.CONVECTION_SMOKER,
//                BrutalityGastronomyItems.SUGAR_GLAZE,
//                BrutalityGastronomyItems.RAINBOW_SPRINKLES,
//                BrutalityGastronomyItems.ROCK_CANDY_RING,
//                BrutalityGastronomyItems.SEARED_SUGAR_BROOCH,
//                BrutalityGastronomyItems.DUNKED_DONUT,
//                BrutalityGastronomyItems.CARAMEL_CRUNCH_MEDALLION,
//                BrutalityGastronomyItems.LOLLIPOP_OF_ETERNITY,
//                BrutalityGastronomyItems.ICE_CREAM_SANDWICH,
//                BrutalityGastronomyItems.MORTAR_AND_PESTLE,
//                BrutalityGastronomyItems.BUTTER_GAUNTLETS,
//                BrutalityGastronomyItems.TOMATO_SAUCE,
//                BrutalityGastronomyItems.CHEESE_SAUCE,
//                BrutalityGastronomyItems.PIZZA_SLOP,
//                BrutalityGastronomyItems.ITALIAN_CLASSIC,
//                BrutalityGastronomyItems.UVOGRE_STEAK,
//                BrutalityGastronomyItems.SHADOWFLAME_SEARED_STEAK,
//                BrutalityGastronomyItems.HOT_SAUCE,
//                BrutalityGastronomyItems.OLIVE_OIL,
//                BrutalityGastronomyItems.EXTRA_VIRGIN_OLIVE_OIL,
//                BrutalityGastronomyItems.PICKLE_JAR,
//                BrutalityGastronomyItems.FRIDGE,
//                BrutalityGastronomyItems.SMART_FRIDGE,
//                BrutalityGastronomyItems.INDUSTRIAL_FREEZER
//                );
    }

    private void addCurioTags() {
        addAnkletTags();
        addBeltTags();
        addCharmTags();
        addHandTags();
        addHeadTags();
        addHeartTags();
        addNecklaceTags();
        addRingTags();
        addFeetTags();
    }

    private void addMathTags() {
        this.tag(BrutalityTags.Items.MATH_ITEMS);
//                .add(
//                BrutalityItems.PI,
//                BrutalityItems.EULERS_NUMBER,
//                BrutalityItems.ADDITION_CHARM,
//                BrutalityItems.SUBTRACTION,
//                BrutalityItems.FRACTION,
//                BrutalityItems.CEIL,
//                BrutalityItems.FLOOR,
//                BrutalityItems.DIVISION,
//                BrutalityItems.SUM,
//                BrutalityItems.SINE,
//                BrutalityItems.COSINE);
    }

    private void addAugmentTags() {
        this.tag(BrutalityTags.Items.AUGMENTS).addTag(BrutalityTags.Items.MAGIC_AUGMENTS).addTag(BrutalityTags.Items.AUGMENTATION_DEVICE).addTag(BrutalityTags.Items.SEALS);

        this.tag(BrutalityTags.Items.AUGMENTATION_DEVICE);
//                .add(
//                BrutalityItems.DIMLITE_AUGMENTATION_DEVICE,
//                BrutalityItems.COSMILITE_AUGMENTATION_DEVICE,
//                BrutalityItems.VIRENTIUM_AUGMENTATION_DEVICE,
//                BrutalityItems.MOLTEN_AUGMENTATION_DEVICE,
//                BrutalityItems.COBALT_AUGMENTATION_DEVICE,
//                BrutalityItems.VOID_AUGMENTATION_DEVICE,
//                BrutalityItems.HELLSPEC_AUGMENTATION_DEVICE,
//                BrutalityItems.CONDUCTITE_AUGMENTATION_DEVICE,
//                BrutalityItems.NYXIUM_AUGMENTATION_DEVICE,
//                BrutalityItems.EXODIUM_AUGMENTATION_DEVICE,
//                BrutalityItems.REVERIUM_AUGMENTATION_DEVICE,
//                BrutalityItems.ADAMANTITE_AUGMENTATION_DEVICE);

        this.tag(BrutalityTags.Items.SEALS);
//                .add(
//                BrutalityItems.BLACK_SEAL,
//                BrutalityItems.BLUE_SEAL,
//                BrutalityItems.GREEN_SEAL,
//                BrutalityItems.ORANGE_SEAL,
//                BrutalityItems.PINK_SEAL,
//                BrutalityItems.PURPLE_SEAL,
//                BrutalityItems.RED_SEAL,
//                BrutalityItems.CYAN_SEAL,
//                BrutalityItems.YELLOW_SEAL,
//                BrutalityItems.BOMB_SEAL,
//                BrutalityItems.COSMIC_SEAL,
//                BrutalityItems.GLASS_SEAL,
//                BrutalityItems.QUANTITE_SEAL,
//                BrutalityItems.VOID_SEAL);
    }

    private void addMagicTags() {
        this.tag(BrutalityTags.Items.MAGIC_ITEMS).addTag(BrutalityTags.Items.SPELL_SCROLLS).addTag(BrutalityTags.Items.MAGIC_AUGMENTS).addTag(BrutalityTags.Items.MAGIC_TOMES);

        this.tag(BrutalityTags.Items.MAGIC_TOMES);
//                .add(
//                BrutalityItems.DAEMONIC_TOME,
//                BrutalityItems.EVERGREEN_TOME,
//                BrutalityItems.DARKIST_TOME,
//                BrutalityItems.COSMIC_TOME,
//                BrutalityItems.VOIDWALKER_TOME,
//                BrutalityItems.BRIMWIELDER_TOME,
//                BrutalityItems.CELESTIA_TOME,
//                BrutalityItems.EXODIC_TOME,
//                BrutalityItems.UMBRAL_TOME);

        this.tag(BrutalityTags.Items.MAGIC_AUGMENTS);
//                .add(
//                BrutalityItems.DRAGON_SINEW_BINDING,
//                BrutalityItems.QUICKSILVER_SPINE,
//                BrutalityItems.QUICKSILVER_INK,
//                BrutalityItems.ARCHANGELS_TEARS,
//                BrutalityItems.SOUL_INFUSED_INK,
//                BrutalityItems.VOID_TOUCHED_INK,
//                BrutalityItems.FEATHER_OF_THE_FIRST_WIND,
//                BrutalityItems.PROFANED_INK,
//                BrutalityItems.FORBIDDEN_MANUSCRIPT,
//                BrutalityItems.UVOGRE_VELLUM,
//                BrutalityItems.SOLID_SPELL_DRIVE,
//                BrutalityItems.IRIDESCENT_BOOKMARK,
//                BrutalityItems.VEGAS_VELLUM,
//                BrutalityItems.RUNE_OF_THE_ROYAL_FLUSH);

        this.tag(BrutalityTags.Items.SPELL_SCROLLS);
//                .add(
//                BrutalityItems.DAEMONIC_SPELL_SCROLL,
//                BrutalityItems.EVERGREEN_SPELL_SCROLL,
//                BrutalityItems.DARKIST_SPELL_SCROLL,
//                BrutalityItems.COSMIC_SPELL_SCROLL,
//                BrutalityItems.VOIDWALKER_SPELL_SCROLL,
//                BrutalityItems.BRIMWIELDER_SPELL_SCROLL,
//                BrutalityItems.VOLTWEAVER_SPELL_SCROLL,
//                BrutalityItems.CELESTIA_SPELL_SCROLL,
//                BrutalityItems.EXODIC_SPELL_SCROLL,
//                BrutalityItems.UMBRAL_SPELL_SCROLL);
    }
}
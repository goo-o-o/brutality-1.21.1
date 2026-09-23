package com.goo.curiosities.client.datagen;


import com.goo.curiosities.common.item.AnkletCurioItem;
import com.goo.curiosities.common.registry.CuriositiesItems;
import com.goo.curiosities.common.registry.CuriositiesTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

;

public class CuriositiesItemTagsProvider extends ItemTagsProvider {


    public CuriositiesItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        addAnkletTags();
        addBeltTags();
        addCharmTags();
        addBodyTags();
        addBackTags();
        addHandTags();
        addBraceletTags();
        addHeadTags();
        addHeartTags();
        addNecklaceTags();
        addRingTags();
        addFeetTags();


        tag(ItemTags.DURABILITY_ENCHANTABLE).add(CuriositiesItems.NETHERITE_JETPACK.value());
        tag(CuriositiesTags.Items.RAINBOW_TOOLTIP).add(
                CuriositiesItems.OMNICHROME_RING.value()
        );
        tag(CuriositiesTags.Items.MATRIX_TOOLTIP).add(
                CuriositiesItems.OMNIDIRECTIONAL_MOVEMENT_GEAR.value(),
                CuriositiesItems.DESYNCHRONIZER.value(),
                CuriositiesItems.XRAY_GOGGLES.value(),
                CuriositiesItems.CORRUPTED_DIAMOND.value()
        );
        tag(CuriositiesTags.Items.FIRE_TOOLTIP).add(
                CuriositiesItems.PORTABLE_FIRE_EXTINGUISHER.value(),
                CuriositiesItems.BLAZE_ANKLET.value(),
                CuriositiesItems.SURTRS_HORN.value(),
                CuriositiesItems.LAVA_LENSES.value(),
                CuriositiesItems.SALAMANDERS_STRIDERS.value(),
                CuriositiesItems.LAVA_WALKERS.value(),
                CuriositiesItems.FIERY_ANKLET.value(),
                CuriositiesItems.MOLTEN_MINERS_GOGGLES.value(),
                CuriositiesItems.RESPLENDENT_FEATHER.value(),
                CuriositiesItems.FLAME_STOMPERS.value(),
                CuriositiesItems.FLAME_THREADERS.value(),
                CuriositiesItems.FLAME_WALKERS.value()
        );
        tag(CuriositiesTags.Items.WATER_TOOLTIP).add(
                CuriositiesItems.WATER_WALKERS.value(),
                CuriositiesItems.GUARDIAN_ANKLET.value(),
                CuriositiesItems.AMPHIBIAN_BOOTS.value(),
                CuriositiesItems.POSEIDONS_BLESSING.value(),
                CuriositiesItems.INNER_TUBE.value()
        );
        tag(CuriositiesTags.Items.SNOW_TOOLTIP).add(
                CuriositiesItems.CLOAK_OF_TRUE_ICE.value(),
                CuriositiesItems.FROZEN_HEART.value(),
                CuriositiesItems.COLD_PILLOW.value(),
                CuriositiesItems.ICE_SKATES.value()
        );
    }

    private void addAnkletTags() {
        IntrinsicTagAppender<Item> ankletTag = this.tag(CuriositiesTags.Items.ANKLET);

        CuriositiesItems.ITEMS.getEntries().stream()
                .map(DeferredHolder::get)
                .filter(AnkletCurioItem.class::isInstance)
                .forEach(ankletTag::add);
    }

    private void addBodyTags() {
        this.tag(CuriosTags.BODY).add(
                CuriositiesItems.LUMBERJACKS_SHIRT.value(),
                CuriositiesItems.SAFETY_HARNESS.value(),
                CuriositiesItems.NANO_MACHINES.value(),
                CuriositiesItems.IRON_GUT.value()
        );
    }

    private void addBackTags() {
        this.tag(CuriosTags.BACK).add(
                CuriositiesItems.NETHERITE_JETPACK.value(),
                CuriositiesItems.ARCHITECT_GIZMO_PACK.value(),
                CuriositiesItems.PORTABLE_CONCRETE_MIXER.value(),
                CuriositiesItems.CLOAK_OF_INVISIBILITY.value(),
                CuriositiesItems.CENSORSHIP_CLOAK.value()
        );
    }

    private void addCharmTags() {
        this.tag(CuriosTags.CHARM).add(
                CuriositiesItems.COMICALLY_LARGE_SHOVEL_HEAD.value(),
                CuriositiesItems.ROYAL_JELLY.value(),
                CuriositiesItems.CORRUPTED_DIAMOND.value(),
                CuriositiesItems.DESYNCHRONIZER.value(),
                CuriositiesItems.VILLAGER_DICTIONARY.value(),
                CuriositiesItems.BLIZZARD_IN_A_BOTTLE.value(),
                CuriositiesItems.SANDSTORM_IN_A_BOTTLE.value(),
                CuriositiesItems.CLOUD_IN_A_BOTTLE.value(),
                CuriositiesItems.TSUNAMI_IN_A_BOTTLE.value(),
                CuriositiesItems.REGAL_EMERALD.value(),
                CuriositiesItems.SOLDIERS_SYRINGE.value(),
                CuriositiesItems.BROKEN_CLOCK.value(),
                CuriositiesItems.PLUNDER_CHEST.value(),
                CuriositiesItems.DOCTORS_PRESCRIPTION.value(),
                CuriositiesItems.WAY_OF_THE_WIND.value(),
                CuriositiesItems.REDACTING_TAPE.value(),
                CuriositiesItems.INCOGNITO_PENDANT.value(),
                CuriositiesItems.RESPLENDENT_FEATHER.value(),
                CuriositiesItems.KINETIC_COMPENSATOR.value(),
                CuriositiesItems.OMNIDIRECTIONAL_MOVEMENT_GEAR.value(),
                CuriositiesItems.EARTHEN_BLESSING.value(),
                CuriositiesItems.ESCAPE_KEY.value(),
                CuriositiesItems.EMERGENCY_MEETING.value(),
                CuriositiesItems.MIRACLE_CURE.value(),
                CuriositiesItems.VINDICATOR_STEROIDS.value(),
                CuriositiesItems.ELBOW_GREASE.value(),
                CuriositiesItems.CLOAK_OF_TRUE_ICE.value(),
                CuriositiesItems.PETROLEUM_JELLY.value(),
                CuriositiesItems.ZEPHYR_IN_A_BOTTLE.value(),
                CuriositiesItems.LIGHT_SWITCH.value(),
                CuriositiesItems.SUSPICIOUS_LOOKING_ROCK.value(),
                CuriositiesItems.ENDER_DRAGON_STEM_CELLS.value(),
                CuriositiesItems.LUCKY_BOOKMARK.value(),
                CuriositiesItems.POSEIDONS_BLESSING.value(),
                CuriositiesItems.SCOUTS_BADGE.value(),
                CuriositiesItems.GLOBETROTTERS_BADGE.value(),
                CuriositiesItems.PINCUSHION.value(),
                CuriositiesItems.BEAD_OF_LIFE.value(),
                CuriositiesItems.INOCULUM.value(),
                CuriositiesItems.THE_VACCINE.value(),
                CuriositiesItems.DOSETTE_BOX.value(),
                CuriositiesItems.EMPTY_PLATE.value(),
                CuriositiesItems.GOURMANDS_DINING_SET.value(),
                CuriositiesItems.WAXY_CUTICLE.value()
        );
    }

    private void addBeltTags() {
        this.tag(CuriosTags.BELT).add(
                CuriositiesItems.MAGNET.value(),
                CuriositiesItems.ELECTROMAGNET.value(),
                CuriositiesItems.TIGER_CLIMBING_GEAR.value(),
                CuriositiesItems.NECTAR_GLAND.value(),
                CuriositiesItems.MICRO_THRUSTERS.value(),
                CuriositiesItems.HONEY_COMB.value(),
                CuriositiesItems.MINIATURE_ANCHOR.value(),
                CuriositiesItems.OLD_GUILLOTINE.value(),
                CuriositiesItems.SURTRS_HORN.value(),
                CuriositiesItems.PORTABLE_FIRE_EXTINGUISHER.value(),
                CuriositiesItems.TOOLBELT.value(),
                CuriositiesItems.INNER_TUBE.value()
        );
    }

    private void addBraceletTags() {
        this.tag(CuriosTags.BRACELET).add(
                CuriositiesItems.PERFECT_CELL.value(),
                CuriositiesItems.GYROSCOPE.value(),
                CuriositiesItems.COBALT_SHIELD.value(),
                CuriositiesItems.OBSIDIAN_SHIELD.value()
        );
    }

    private void addHandTags() {
        this.tag(CuriosTags.HANDS).add(
                CuriositiesItems.HAND_OF_CREATION.value(),
                CuriositiesItems.HAND_OF_DESTRUCTION.value(),
                CuriositiesItems.ANCIENT_CHISEL.value(),
                CuriositiesItems.EXTENDO_GRIP.value(),
                CuriositiesItems.SWISS_ARMY_KNIFE.value(),
                CuriositiesItems.DIGGING_CLAWS.value(),
                CuriositiesItems.BLOCK_LAYER.value(),
                CuriositiesItems.SHINY_RED_BALLOON.value(),
                CuriositiesItems.ESPRESSO.value(),
                CuriositiesItems.CLIMBING_CLAWS.value(),
                CuriositiesItems.GOLDEN_FORK.value(),
                CuriositiesItems.DUELING_GLOVE.value(),
                CuriositiesItems.PHANTOM_FINGER.value(),
                CuriositiesItems.SUSPICIOUSLY_LARGE_HANDLE.value(),
                CuriositiesItems.JURY_NULLIFIER.value(),
                CuriositiesItems.OMEGA_GAUNTLET.value()
        );
    }

    private void addHeadTags() {
        this.tag(CuriosTags.HEAD).add(
                CuriositiesItems.VENDETTA_MASK.value(),
                CuriositiesItems.THE_WORLD.value(),
                CuriositiesItems.OMNISCIENT_GLASSES.value(),
                CuriositiesItems.XRAY_GOGGLES.value(),
                CuriositiesItems.COLD_PILLOW.value(),
                CuriositiesItems.OBSIDIAN_SKULL.value(),
                CuriositiesItems.EYE_OF_THE_DRAGON.value(),
                CuriositiesItems.CROWN_OF_TYRANNY.value(),
                CuriositiesItems.HEAD_CUSHION.value(),
                CuriositiesItems.LAVA_LENSES.value(),
                CuriositiesItems.MINING_GOGGLES.value(),
                CuriositiesItems.MOLTEN_MINERS_GOGGLES.value(),
                CuriositiesItems.BRAIN_ROT.value()
        );
    }

    private void addHeartTags() {
        this.tag(CuriositiesTags.Items.HEART).add(
                CuriositiesItems.DRAGON_HEART.value(),
                CuriositiesItems.FROZEN_HEART.value(),
                CuriositiesItems.HEART_OF_GOLD.value(),
                CuriositiesItems.SECOND_HEART.value(),
                CuriositiesItems.NINJA_HEART.value(),
                CuriositiesItems.OGRES_HEART.value(),
                CuriositiesItems.WARMOGS_HEART.value(),
                CuriositiesItems.GLASS_HEART.value()
        );
    }

    private void addNecklaceTags() {
        this.tag(CuriosTags.NECKLACE).add(
                CuriositiesItems.BEDROCK_NECKLACE.value(),
                CuriositiesItems.PANIC_NECKLACE.value(),
                CuriositiesItems.CROSS_NECKLACE.value(),
                CuriositiesItems.CROSSED_HEART_NECKLACE.value(),
                CuriositiesItems.PRISTINE_NAPKIN.value(),
                CuriositiesItems.ABYSSAL_NECKLACE.value(),
                CuriositiesItems.ANTI_CHEAT.value()
        );
    }

    private void addRingTags() {
        this.tag(CuriosTags.RING).add(
                CuriositiesItems.OMNICHROME_RING.value(),
                CuriositiesItems.RING_OF_RINGS.value(),
                CuriositiesItems.ROADRUNNERS_RING.value()
        );
    }

    private void addFeetTags() {
        this.tag(CuriositiesTags.Items.FEET).add(
                CuriositiesItems.FLAME_WALKERS.value(),
                CuriositiesItems.FLAME_THREADERS.value(),
                CuriositiesItems.FLAME_STOMPERS.value(),
                CuriositiesItems.SEISMIC_STOMPERS.value(),
                CuriositiesItems.SLIPSTREAM_TRACERS.value(),
                CuriositiesItems.LUCKY_INSOLES.value(),
                CuriositiesItems.AMPHIBIAN_BOOTS.value(),
                CuriositiesItems.SALAMANDERS_STRIDERS.value(),
                CuriositiesItems.CONSTRUCTION_BOOTS.value(),
                CuriositiesItems.HONEY_BOOTS.value(),
                CuriositiesItems.SHOE_SPIKES.value(),
                CuriositiesItems.LAVA_WALKERS.value(),
                CuriositiesItems.WATER_WALKERS.value(),
                CuriositiesItems.VECTOR_STABILIZER.value(),
                CuriositiesItems.PLATED_STEELCAPS.value(),
                CuriositiesItems.MOVEMENT_GODS_TRACERS.value(),
                CuriositiesItems.BOOTS_OF_SWIFTNESS.value(),
                CuriositiesItems.ICE_SKATES.value(),
                CuriositiesItems.HIGH_HEALS.value(),
                CuriositiesItems.INERTIA_BOOSTER.value(),
                CuriositiesItems.PORTABLE_TRAMPOLINE.value(),
                CuriositiesItems.FLIPPERS_OF_ICARUS.value(),
                CuriositiesItems.LUCKY_HORSESHOE.value(),
                CuriositiesItems.OBSIDIAN_HORSESHOE.value()
        );
    }

}
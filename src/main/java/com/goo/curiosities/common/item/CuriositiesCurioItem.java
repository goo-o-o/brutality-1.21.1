package com.goo.curiosities.common.item;

import com.goo.curiosities.client.registry.CuriositiesKeymappings;
import com.goo.curiosities.common.networking.serverbound.TriggerActiveAbilityPayload;
import com.goo.goo_lib.common.attribute.AttributeContainer;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffect;
import com.goo.goo_lib.common.mob_effect.passive.PassiveMobEffectEngine;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;


public class CuriositiesCurioItem extends Item implements ICurioItem {
    public CuriositiesCurioItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }


    /**
     * Defines a list of {@link PassiveMobEffect}s to apply when wearing this Curio
     */
    protected List<PassiveMobEffect> definePassives() {
        return List.of();
    }

    /**
     * Forces a fresh evaluation of all passives for this curio.
     * Useful on equip, respawn, or dimension changes.
     */
    public void reEvaluatePassives(SlotContext slotContext) {
        LivingEntity entity = slotContext.entity();
        if (entity.level().isClientSide()) return;

        for (PassiveMobEffect passive : definePassives()) {
            boolean active = passive.condition().test(entity);
            PassiveMobEffectEngine.setPassiveState(entity, passive, active);
        }
    }

    public void purgePassives(LivingEntity entity) {
        for (PassiveMobEffect passive : definePassives()) {
            PassiveMobEffectEngine.setPassiveState(entity, passive, false);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        reEvaluatePassives(slotContext);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        for (PassiveMobEffect passive : definePassives()) {
            if (entity.tickCount % passive.checkInterval() == 0) {
                boolean currentlyActive = passive.condition().test(entity);
                boolean stateSwitched = PassiveMobEffectEngine.setPassiveState(entity, passive, currentlyActive);

//            if (stateSwitched) {
//                entity.sendSystemMessage(Component.literal(
//                        "State switched for " + BuiltInRegistries.ITEM.getKey(this) + " | currentActive: " + currentlyActive
//                ));
//            }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        purgePassives(slotContext.entity());
    }

    private List<AttributeContainer> attributeContainers = List.of();

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (attributeContainers.isEmpty()) {
            return ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
        }

        Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();

        for (AttributeContainer holder : this.attributeContainers) {
            AttributeModifier modifier = new AttributeModifier(id, holder.value(), holder.operation());
            map.put(holder.attribute(), modifier);
        }

        return map;
    }


    /**
     * Fluently assigns attribute templates to this item.
     * Typically called during registry initialization.
     *
     * @param attributes One or more {@link AttributeContainer} templates.
     * @return This item instance for chaining.
     */
    public CuriositiesCurioItem withAttributes(AttributeContainer... attributes) {
        attributeContainers = List.of(attributes);
        return this;
    }


    public void onWearerMobEffectAdded(LivingEntity wearer, ItemStack curio, MobEffectEvent.Added event) {

    }

    public void onWearerMobEffectApplicable(LivingEntity wearer, ItemStack curio, MobEffectEvent.Applicable event) {

    }

    public void onWearerMobEffectRemoved(LivingEntity wearer, ItemStack curio, MobEffectEvent.Remove event) {

    }

    public void onWearerMobEffectExpired(LivingEntity wearer, ItemStack curio, MobEffectEvent.Expired event) {

    }

    /**
     * Triggers when the Wearer knocks an entity back
     *
     * @param attacker
     * @param victim
     * @param curio
     * @param event
     */
    public void onWearerKnockback(LivingEntity attacker, @NotNull LivingEntity victim, ItemStack curio, LivingKnockBackEvent event) {
    }

    /**
     * Triggers when the Wearer gets knocked back
     *
     * @param victim
     * @param attacker nullable
     * @param curio
     * @param event
     */
    public void onWearerKnockedBack(LivingEntity victim, @Nullable LivingEntity attacker, ItemStack curio, LivingKnockBackEvent event) {
    }

    /**
     * Triggered when the Wearer critically strikes
     * @param victim
     * @param attacker
     * @param curio
     * @param event
     */
    public void onWearerCrit(LivingEntity victim, @Nullable LivingEntity attacker, ItemStack curio, CriticalHitEvent event) {
    }


    /**
     * Triggers when the entity wearing this item heals.
     *
     * @param healer The entity being healed.
     * @param curio  The curio being worn.
     */
    public void onWearerHeal(LivingEntity healer, ItemStack curio, LivingHealEvent event) {
    }


    /**
     * Triggers when the entity wearing this item takes damage.
     * <p>
     * This is useful for "Thorns" effects, damage reduction, or proccing
     * defensive buffs upon being struck.
     * </p>
     *
     * @param wearer The entity wearing the gear.
     * @param curio  The curio being worn.
     * @param source The source of the damage (e.g., arrow, explosion, or melee).
     * @param event
     */
    public void onWearerHurt(LivingEntity wearer, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
    }

    public void onWearerIncomingHurt(LivingEntity wearer, ItemStack curio, LivingIncomingDamageEvent event) {
    }

    /**
     * Triggers when the entity wearing this item deals damage to another entity.
     * <p>
     * Use this for offensive procs, such as lifesteal, applying debuffs to
     * the victim, or custom knockback logic.
     * </p>
     *
     * @param attacker The entity wearing the gear (the attacker).
     * @param victim   The entity being struck by the attacker.
     * @param curio    The curio being worn.
     * @param source   The source of the damage dealt.
     * @param event
     */
    public void onWearerHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDamageEvent.Pre event) {
    }


    /**
     * Melee-sensitive version of {@link CuriositiesCurioItem#onWearerHit}
     * Triggers when the entity wearing this item deals damage to another entity.
     * <p>
     * Use this for offensive procs, such as lifesteal, applying debuffs to
     * the victim, or custom knockback logic.
     * </p>
     *
     * @param attacker The entity wearing the gear (the attacker).
     * @param victim   The entity being struck by the attacker.
     * @param curio    The curio being worn.
     * @param event
     */
    public void onWearerMeleeHit(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource damageSource, LivingDamageEvent.Pre event) {
    }

    /**
     * Triggers when the entity wearing this item deals kills another entity.
     * <p>
     * Use this for offensive procs, such as lifesteal, applying debuffs to
     * the victim, or custom knockback logic.
     * </p>
     *
     * @param attacker The entity wearing the gear (the attacker).
     * @param victim   The entity being struck by the attacker.
     * @param curio    The curio being worn.
     * @param source   The source of the damage dealt.
     */
    public void onWearerKill(LivingEntity attacker, Entity victim, ItemStack curio, DamageSource source, LivingDeathEvent event) {
    }

    /**
     * Triggers when the entity wearing this item dies.
     *
     * @param victim   The deceased.
     * @param attacker The cause of the death if present.
     * @param curio    The curio being worn.
     * @param source   The source of the damage dealt.
     */
    public void onWearerDeath(LivingEntity victim, Entity attacker, ItemStack curio, DamageSource source, LivingDeathEvent event) {
    }

    /**
     * Triggers when the entity wearing this item falls
     * Unlike others, this is a void method, edit the event directly, this allows you to cancel the event if needed
     *
     * @param event The event containing the required information
     */
    public void onWearerFall(LivingFallEvent event, ItemStack curio) {
    }

    /**
     * Triggers when the player presses {@link CuriositiesKeymappings.Mappings#ACTIVE_ABILITY}, ran on both sides via different hooks, technically client side runs first
     *
     * @param player
     * @param curio
     */
    public void onWearerPressActiveAbility(Player player, ItemStack curio) {

    }

    protected double getDynamicAttributeBonus(SlotContext slotContext, ItemStack stack, AttributeInstance attributeInstance, double total, double unmodified) {
        return 0.0F;
    }

    /**
     * This is where the relevant methods (e.g. {@link CuriositiesCurioItem#onWearerKill(LivingEntity, Entity, ItemStack, DamageSource, LivingDeathEvent)} will be called from
     */
    public static class Hooks {

        public static void onPressActiveAbilityKeymapping(Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerPressActiveAbility(player, result.stack())));

            PacketDistributor.sendToServer(new TriggerActiveAbilityPayload());
        }

        /**
         * Modifies the given dynamic attributeInstance value by calculating additional bonuses
         * from all equipped curios of type {@link CuriositiesCurioItem}. Each applicable item can
         * contribute a specific bonus to the provided attributeInstance value.
         * <p>
         * Do not get an attributeInstance value while modifying the value, for example,
         * do not get the {@link net.minecraft.world.entity.ai.attributes.Attributes#MAX_HEALTH} of an entity if you plan to modify the bonus of {@code MAX_HEALTH}. Doing so will result in a {@link StackOverflowError}, surround with an if statement. Look at {@link net.goo.brutality.common.item.curios.anklet.AnkletOfTheImprisoned} for an example
         *
         * @param livingEntity      The {@link LivingEntity} whose attributeInstance value is being modified. This entity
         *                          is expected to wear any applicable curios.
         * @param attributeInstance The {@link AttributeInstance} being modified. Each curio may contribute a dynamic
         *                          bonus to this attributeInstance.
         * @param amount            The initial value of the attributeInstance that is to be modified.
         * @return The total modified attributeInstance value after applying bonuses from all applicable curios.
         */
        public static double modifyDynamicAttributeValues(LivingEntity livingEntity, AttributeInstance attributeInstance, double amount) {
            double total = amount;
            Optional<ICuriosItemHandler> opt = CuriosApi.getCuriosInventory(livingEntity);
            if (opt.isPresent()) {
                // Find all data that are BrutalityCurioItems
                for (SlotResult result : opt.get().findCurios(s -> s.getItem() instanceof CuriositiesCurioItem)) {

                    CuriositiesCurioItem item = (CuriositiesCurioItem) result.stack().getItem();

                    // Add the item's specific bonus to our running total
                    total += item.getDynamicAttributeBonus(result.slotContext(), result.stack(), attributeInstance, total, amount);
                }
            }
            return total;
        }


        public static void applyOnWearerMobEffectAdded(LivingEntity wearer, MobEffectEvent.Added event) {
            CuriosApi.getCuriosInventory(wearer).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerMobEffectAdded(wearer, result.stack(), event)));
        }


        public static void applyOnWearerMobEffectApplicable(LivingEntity wearer, MobEffectEvent.Applicable event) {
            CuriosApi.getCuriosInventory(wearer).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerMobEffectApplicable(wearer, result.stack(), event)));
        }

        public static void applyOnWearerMobEffectExpired(LivingEntity wearer, MobEffectEvent.Expired event) {
            CuriosApi.getCuriosInventory(wearer).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerMobEffectExpired(wearer, result.stack(), event)));
        }

        public static void applyOnWearerMobEffectRemoved(LivingEntity wearer, MobEffectEvent.Remove event) {
            CuriosApi.getCuriosInventory(wearer).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerMobEffectRemoved(wearer, result.stack(), event)));
        }

        /**
         * Called when the Wearer knocks back an entity
         */
        public static void applyOnWearerKnockback(@NotNull LivingEntity attacker, LivingEntity victim, LivingKnockBackEvent event) {
            CuriosApi.getCuriosInventory(attacker).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerKnockback(attacker, victim, result.stack(), event)));
        }

        /**
         * Called when the Wearer crits
         */
        public static void applyOnWearerCrit(@NotNull LivingEntity attacker, LivingEntity victim, CriticalHitEvent event) {
            CuriosApi.getCuriosInventory(attacker).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerCrit(attacker, victim, result.stack(), event)));
        }

        /**
         * Called when the Wearer gets knocked back
         *
         * @param attacker can be null, the entity that knocked the player back
         */
        public static void applyOnWearerKnockedBack(LivingEntity victim, @Nullable LivingEntity attacker, LivingKnockBackEvent event) {
            CuriosApi.getCuriosInventory(victim).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerKnockedBack(victim, attacker, result.stack(), event)));
        }

        /**
         * Applies healing effects for curios worn by the given entity. This method is used to modify
         * the provided heal amount based on the behavior implemented by each {@link CuriositiesCurioItem}
         * equipped by the wearer.
         *
         */
        public static void applyOnWearerHeal(LivingHealEvent event) {
            CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerHeal(event.getEntity(), result.stack(), event)));
        }

        /**
         * Applies the effects of any equipped {@link CuriositiesCurioItem} when the wearer
         * experiences a fall. This method is invoked during a {@link LivingFallEvent}.
         *
         * @param event The {@link LivingFallEvent} representing the fall event that
         *              occurred for the entity wearing the item.
         */
        public static void applyOnWearerFall(LivingFallEvent event) {
            CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerFall(event, result.stack())));
        }

        /**
         * Applies specific effects or behavior when the wearer of a {@link CuriositiesCurioItem}
         * kills another entity. This method checks the killer's inventory for any equipped
         * {@link CuriositiesCurioItem}s and triggers their corresponding {@code onWearerKill} method.
         *
         * @param killer       The entity that performed the kill. This is typically the wearer
         *                     of the curio.
         * @param victim       The entity that was killed by the killer.
         * @param damageSource The source of damage that caused the kill. This provides context
         *                     about how the victim was killed.
         */
        public static void applyOnWearerKill(LivingEntity killer, LivingEntity victim, DamageSource damageSource, LivingDeathEvent event) {
            CuriosApi.getCuriosInventory(killer).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerKill(killer, victim, result.stack(), damageSource, event)));
        }

        /**
         * Applies specific effects or behavior when the wearer of a {@link CuriositiesCurioItem}
         * dies. This method checks the deceased's inventory for any equipped
         * {@link CuriositiesCurioItem}s and triggers their corresponding {@code onWearerDeath} method.
         *
         * @param killer       The entity that performed the kill. This is typically the wearer
         *                     of the curio.
         * @param victim       The entity that was killed by the killer.
         * @param damageSource The source of damage that caused the kill. This provides context
         *                     about how the victim was killed.
         */
        public static void applyOnWearerDeath(LivingEntity victim, @Nullable Entity killer, DamageSource damageSource, LivingDeathEvent event) {
            CuriosApi.getCuriosInventory(victim).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerDeath(victim, killer, result.stack(), damageSource, event)));
        }

        /**
         * Calls the {@code onWearerMeleeHit} method for each equipped curio of type {@link CuriositiesCurioItem}
         * when the wearer attacks an enemy with a melee weapon. This allows equipped curios to dynamically
         * modify the damage dealt by the wearer.
         *
         * @param attacker        The player who is performing the melee attack.
         * @param victim          The living entity being attacked.
         */
        public static void applyOnWearerMeleeHit(LivingEntity attacker, LivingEntity victim, DamageSource damageSource, LivingDamageEvent.Pre event) {
            CuriosApi.getCuriosInventory(attacker).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerMeleeHit(attacker, victim, result.stack(), damageSource, event)));
        }


        public static void applyOnWearerHurt(LivingEntity victim, DamageSource source, LivingDamageEvent.Pre event) {
            CuriosApi.getCuriosInventory(victim).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerHurt(victim, result.stack(), source, event)));
        }

        public static void applyOnWearerIncomingHurt(LivingEntity victim, LivingIncomingDamageEvent event) {
            CuriosApi.getCuriosInventory(victim).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerIncomingHurt(victim, result.stack(), event)));
        }

        public static void applyOnWearerHit(LivingEntity attacker, LivingEntity victim, DamageSource source, LivingDamageEvent.Pre event) {
            CuriosApi.getCuriosInventory(attacker).ifPresent(handler ->
                    handler.findCurios(s -> s.getItem() instanceof CuriositiesCurioItem).forEach(result ->
                            ((CuriositiesCurioItem) result.stack().getItem()).onWearerHit(attacker, victim, result.stack(), source, event)));
        }
    }

}

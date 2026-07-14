package com.goo.brutality.mixin;

import com.goo.brutality.common.BrutalityServerConfig;
import com.goo.brutality.common.registry.BrutalityTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "getItemBySlot", at = @At("RETURN"), cancellable = true)
    public void getEquippedStackHead(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        Player player = (Player) (Object) this;
        // cast to access player inventory directly and prevent recursion
        boolean twoHandedDisablesOffhand;
        try {
            twoHandedDisablesOffhand = BrutalityServerConfig.CONFIG.TWO_HANDED_DISABLES_OFFHAND.get();
        } catch (IllegalStateException e) {
            // config isn't loaded or synced yet because the player is still connecting
            twoHandedDisablesOffhand = false;
        }
        if (twoHandedDisablesOffhand) {
            ItemStack mainHandStack = player.getInventory().getSelected();
            ItemStack offHandStack = player.getInventory().offhand.getFirst();
            if (slot == EquipmentSlot.OFFHAND && (mainHandStack.is(BrutalityTags.Items.TWO_HANDED) || offHandStack.is(BrutalityTags.Items.TWO_HANDED))) {
                cir.setReturnValue(ItemStack.EMPTY);
            }
        }
    }
}
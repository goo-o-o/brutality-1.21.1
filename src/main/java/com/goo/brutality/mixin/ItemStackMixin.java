package com.goo.brutality.mixin;

import com.goo.brutality.common.registry.BrutalityTags;
import com.goo.brutality.util.Styles;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void gl$applyTagStylesToName(CallbackInfoReturnable<Component> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        if (stack.is(BrutalityTags.Items.RAGE_ITEMS)) {
            Component originalName = cir.getReturnValue();
            MutableComponent styledName = originalName.copy().withStyle(Styles.RAGE_STYLE);
            cir.setReturnValue(styledName);
        }
    }
}
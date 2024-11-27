package io.bluebeaker.bettertradingmenu.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.GuiMerchant;

@Mixin(GuiMerchant.class)
public interface AccessorGuiMerchant {
    @Accessor
    public void setSelectedMerchantRecipe(int i);
}

package io.bluebeaker.bettertradingmenu;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import mezz.jei.api.gui.IAdvancedGuiHandler;
import net.minecraft.client.gui.GuiMerchant;

public class JEIMover implements IAdvancedGuiHandler<GuiMerchant> {

    @Override
    public Class<GuiMerchant> getGuiContainerClass() {
        return GuiMerchant.class;
    }

    @Override
    @Nullable
    public List<Rectangle> getGuiExtraAreas(GuiMerchant guiContainer) {
        return Collections.singletonList(BTMManager.getMenuArea());
    }
    
}

package io.bluebeaker.bettertradingmenu;


import java.awt.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BTMManager {
    private static BTMButtonsMenu buttons;
    private static boolean newGUI = false;
    private static Minecraft mc = Minecraft.getMinecraft();

    public static Rectangle getMenuArea(){
        if(buttons==null){
            return new Rectangle(0, 0, 0, 0);
        }
        return buttons.getMenuSize();
    }

    @SubscribeEvent
    public static void onVillagerGUIOpen(GuiOpenEvent event) {
        GuiScreen screen = event.getGui();
        if (!(screen instanceof GuiMerchant))
            return;
        newGUI = true;
    }

    private static boolean isVillagerGUIActivated(GuiScreen screen) {
        return !newGUI && buttons != null && screen instanceof GuiMerchant;
    }

    @SubscribeEvent
    public static void onVillagerGUIMouse(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (isVillagerGUIActivated(event.getGui()) && buttons.isMouseOver()) {
            try {
                buttons.handleMouseInput();
                event.setCanceled(true);
            } catch (Exception e) {
                BetterTradingMenu.getLogger().error("Exception in GUI: ",e);
            }
        }
    }

    @SubscribeEvent
    public static void onVillagerGUIDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        GuiScreen screen = event.getGui();
        if (!(screen instanceof GuiMerchant))
            return;
        if (newGUI) {
            MerchantRecipeList recipes = ((GuiMerchant) screen).getMerchant()
                    .getRecipes(mc.player);
            if (recipes == null)
                return;

            ContainerMerchant container = (ContainerMerchant) ((GuiMerchant) screen).inventorySlots;
            if(container==null)return;

            buttons = new BTMButtonsMenu(container);
            buttons.updateRecipes(recipes);
            newGUI = false;
        }
        if (!newGUI && buttons != null) {
            int x = (screen.width - 176) / 2 - 80;
            int y = (screen.height - 166) / 2;
            buttons.setLeftTop(x, y);
            buttons.drawScreen(event.getMouseX(), event.getMouseY(), 0);
        }
    }
}

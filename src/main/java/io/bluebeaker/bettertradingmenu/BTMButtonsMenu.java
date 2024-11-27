package io.bluebeaker.bettertradingmenu;

import java.awt.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class BTMButtonsMenu extends GuiScreen {

    private MerchantRecipeList recipes;
    private ContainerMerchant containerMerchant;

    private int x;
    private int y;

    public static final int BUTTON_WIDTH = 80;
    public static final int BUTTON_HEIGHT = 20;

    public BTMButtonsMenu(ContainerMerchant containerMerchant) {
        this.containerMerchant = containerMerchant;
        this.mc = Minecraft.getMinecraft();
    }

    public void setLeftTop(int x, int y) {
        this.x = x;
        this.y = y;
        this.updateButtonsAndSize();
    }

    public void updateRecipes(MerchantRecipeList recipes) {
        this.recipes = recipes;
        this.updateButtonsAndSize();
    }

    public void updateButtonsAndSize() {
        GuiScreen screen = mc.currentScreen;
        if (screen != null) {
            this.width = screen.width;
            this.height = screen.height;
        }
        this.buttonList.clear();
        if (this.recipes == null)
            return;
        int i = 0;
        for (MerchantRecipe recipe : this.recipes) {
            this.buttonList.add(new MerchantButton(i, this.x + 0, this.y + i * 20, "", recipe, this));
            i++;
        }
    }

    public void selectIndex(int index) {
        this.containerMerchant.setCurrentRecipeIndex(index);
    }

    public boolean isMouseOver() {
        for (GuiButton button : this.buttonList) {
            if (button.isMouseOver())
                return true;
        }
        return false;
    }

    public Rectangle getMenuSize(){
        return new Rectangle(this.x, this.y, BUTTON_WIDTH, BUTTON_HEIGHT*this.buttonList.size());
    }

    public static class MerchantButton extends GuiButton {
        public final MerchantRecipe recipe;
        private static Minecraft mc = Minecraft.getMinecraft();
        private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation(
                "textures/gui/container/villager.png");
        private RenderItem itemRender;
        private FontRenderer fontRenderer;
        public final BTMButtonsMenu menu;

        public MerchantButton(int buttonId, int x, int y, String buttonText, MerchantRecipe recipe,
                BTMButtonsMenu menu) {
            super(buttonId, x, y, BUTTON_WIDTH, BUTTON_HEIGHT, buttonText);
            this.recipe = recipe;
            this.itemRender = mc.getRenderItem();
            this.fontRenderer = mc.fontRenderer;
            this.menu = menu;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            this.enabled = !this.recipe.isRecipeDisabled();
            GlStateManager.disableLighting();
            super.drawButton(mc, mouseX, mouseY, partialTicks);
            GlStateManager.enableLighting();
            if (this.visible) {

                GlStateManager.pushMatrix();
                GlStateManager.translate(this.x, this.y, 0);
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.disableLighting();
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableColorMaterial();
                GlStateManager.enableLighting();
                this.itemRender.zLevel = 100.0F;

                drawItemStack(recipe.getItemToBuy(), 2, 2, null);
                ItemStack secondItem = recipe.getSecondItemToBuy();
                if (secondItem != null)
                    drawItemStack(secondItem, 20, 2, null);
                drawItemStack(recipe.getItemToSell(), 62, 2, null);

                GlStateManager.disableLighting();

                if (this.recipe.isRecipeDisabled()) {
                    mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.disableLighting();
                    this.drawTexturedModalRect(40, 0, 212, 0, 28, 21);
                }

                this.itemRender.zLevel = 0.0F;

                GlStateManager.popMatrix();

            }
        }

        private void drawItemStack(ItemStack stack, int x, int y, String altText) {
            this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
            this.itemRender.renderItemOverlays(this.fontRenderer, stack, x, y);
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            boolean pressed = super.mousePressed(mc, mouseX, mouseY);
            // BetterTradingMenu.getLogger().atInfo()
            //         .log("Mouse pressed" + this.enabled + " " + this.visible + " " + mouseX + " " + this.x + " "
            //                 + mouseY + " " + this.y + " " + (this.x + this.width) + " " + (this.y + this.height));
            if (pressed) {
                this.menu.selectIndex(id);
                return true;
            }
            return false;
        }
    }
}
package io.bluebeaker.bettertradingmenu;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BetterTradingMenuMod.MODID,type = Type.INSTANCE,category = "general")
public class BetterTradingMenuConfig {
    @Comment({"Move items when clicked a trade in this mod's menu.",
    "when disabled, clicking a trade only selects it in the GUI."})
    @LangKey("config.bettertradingmenu.move_items.name")
    public static boolean move_items = true;
}
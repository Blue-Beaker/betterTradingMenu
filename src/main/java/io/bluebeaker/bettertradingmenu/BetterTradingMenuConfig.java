package io.bluebeaker.bettertradingmenu;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BetterTradingMenu.MODID,type = Type.INSTANCE,category = "general")
public class BetterTradingMenuConfig {
    @Comment("Example")
    @LangKey("config.bettertradingmenu.example.name")
    public static boolean example = true;
}
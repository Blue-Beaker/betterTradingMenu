package io.bluebeaker.bettertradingmenu;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEIPluginBTM implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		registry.addAdvancedGuiHandlers(new BTMGuiHandler());
	}
}
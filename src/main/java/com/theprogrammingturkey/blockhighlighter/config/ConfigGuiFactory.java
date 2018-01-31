package com.theprogrammingturkey.blockhighlighter.config;

import java.util.Set;

import com.theprogrammingturkey.blockhighlighter.client.gui.ConfigGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class ConfigGuiFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft minecraftInstance)
	{

	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new ConfigGui(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}
}

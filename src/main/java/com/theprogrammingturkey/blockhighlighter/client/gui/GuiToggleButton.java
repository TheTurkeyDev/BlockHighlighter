package com.theprogrammingturkey.blockhighlighter.client.gui;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class GuiToggleButton extends Button
{
	private BooleanValue toggle;
	private String baseDisplay;

	public GuiToggleButton(int x, int y, int widthIn, int heightIn, String display, BooleanValue enabled)
	{
		super(x, y, widthIn, heightIn, display + (enabled.get() ? "On" : "Off"), press ->
		{
		});
		this.baseDisplay = display;
		this.toggle = enabled;
	}

	@Override
	public void onPress()
	{
		toggle.set(!toggle.get());

		setMessage(baseDisplay + (toggle.get() ? "On" : "Off"));
	}
}
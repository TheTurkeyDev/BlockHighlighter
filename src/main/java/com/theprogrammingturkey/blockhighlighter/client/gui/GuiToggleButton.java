package com.theprogrammingturkey.blockhighlighter.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiToggleButton extends GuiButton
{
	private boolean toggle;
	private String baseDisplay;

	public GuiToggleButton(int buttonId, int x, int y, int widthIn, int heightIn, String display, boolean enabled)
	{
		super(buttonId, x, y, widthIn, heightIn, display + (enabled ? "On" : "Off"));
		this.baseDisplay = display;
		this.toggle = enabled;
	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{

		boolean clicked = super.mousePressed(mc, mouseX, mouseY);

		if(clicked)
		{
			toggle = !toggle;
			displayString = baseDisplay + (toggle ? "On" : "Off");
		}

		return clicked;
	}

	public boolean isToggledOn()
	{
		return this.toggle;
	}

}
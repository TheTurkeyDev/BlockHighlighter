package com.theprogrammingturkey.blockhighlighter.client.gui;

import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;

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

	public void onClick(double mouseX, double mouseY)
	{
		toggle = !toggle;
		displayString = baseDisplay + (toggle ? "On" : "Off");

		/*if(id == 10)
			BlockHighlightSettings.customHighlight = this.isToggledOn();
		else if(id == 15)
			BlockHighlightSettings.includeDefaultHighlight = this.isToggledOn();
		else if(id == 16)
			BlockHighlightSettings.highlightAffectedByLight = this.isToggledOn();
		else if(id == 17)
			BlockHighlightSettings.highlightBlockFaces = this.isToggledOn();
		else if(id == 18)
			BlockHighlightSettings.highlightBlink = this.isToggledOn();*/
	}

	public boolean isToggledOn()
	{
		return this.toggle;
	}

}
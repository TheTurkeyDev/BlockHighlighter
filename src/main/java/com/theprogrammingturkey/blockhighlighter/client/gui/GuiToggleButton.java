package com.theprogrammingturkey.blockhighlighter.client.gui;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.jetbrains.annotations.NotNull;

public class GuiToggleButton extends AbstractButton
{
	private final BooleanValue toggle;
	private final TextComponent baseDisplay;

	public GuiToggleButton(int x, int y, int widthIn, int heightIn, TextComponent baseDisplay, BooleanValue enabled)
	{
		super(x, y, widthIn, heightIn, baseDisplay.copy().append(enabled.get() ? "On" : "Off"));
		this.baseDisplay = baseDisplay;
		this.toggle = enabled;
	}

	@Override
	public void onPress()
	{
		toggle.set(!toggle.get());
		setMessage(baseDisplay.copy().append(toggle.get() ? "On" : "Off"));
	}

	@Override
	public void updateNarration(@NotNull NarrationElementOutput output)
	{
		//TODO
	}
}
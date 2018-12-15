package com.theprogrammingturkey.blockhighlighter.client.gui;

import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;
import com.theprogrammingturkey.blockhighlighter.config.BlockHighlighterConfigLoader;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ConfigGui extends GuiScreen
{
	private GuiScreen parentScreen;

	private GuiColorSelection boxOutlineColorSelelection;
	private GuiColorSelection boxFillColorSelelection;
	private GuiToggleButton customSelectionBox;
	private GuiButton blockSelectionColors;
	private GuiSlider thicknessSlider;
	private GuiToggleButton useDefaultBox;
	private GuiToggleButton highlightAffectedByLight;
	private GuiToggleButton highlightBlockFaces;
	private GuiToggleButton blinkSelectionBox;
	private GuiSlider blinkTimerSlider;

	private boolean editingColor = false;

	public static boolean buttonPressedThisUpdate = false;

	public ConfigGui(GuiScreen parent)
	{
		parentScreen = parent;
	}

	public void initGui()
	{
		this.buttonList.clear();

		this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - 25, 200, 20, "Back"));

		boxOutlineColorSelelection = new GuiColorSelection("Block Highlight Outline", this.buttonList, this.width / 2 - 100, 30);
		boxOutlineColorSelelection.setCurrentValues(BlockHighlightSettings.highlightColorR, BlockHighlightSettings.highlightColorG, BlockHighlightSettings.highlightColorB, BlockHighlightSettings.highlightColorA);
		boxOutlineColorSelelection.setVisible(false);
		boxFillColorSelelection = new GuiColorSelection("Block Highlight Fill", this.buttonList, this.width / 2 - 100, 150);
		boxFillColorSelelection.setCurrentValues(BlockHighlightSettings.fillColorR, BlockHighlightSettings.fillColorG, BlockHighlightSettings.fillColorB, BlockHighlightSettings.fillColorA);
		boxFillColorSelelection.setVisible(false);

		this.buttonList.add(useDefaultBox = new GuiToggleButton(15, this.width / 2 - 100, 30, 150, 20, "Vanilla selection box: ", BlockHighlightSettings.includeDefaultHighlight));
		this.buttonList.add(customSelectionBox = new GuiToggleButton(10, this.width / 2 - 100, 55, 150, 20, "Custom Selection Box: ", BlockHighlightSettings.customHighlight));
		this.buttonList.add(highlightBlockFaces = new GuiToggleButton(17, this.width / 2 - 100, 80, 150, 20, "Highlight Block Faces: ", BlockHighlightSettings.highlightBlockFaces));
		this.buttonList.add(blockSelectionColors = new GuiButton(11, this.width / 2 - 100, 105, 150, 20, "Set Colors"));
		this.buttonList.add(thicknessSlider = new GuiSlider(14, "Thickness", this.width / 2 - 100, 130, 1F, 10F, BlockHighlightSettings.highlightLineThickness, 0.5F));
		this.buttonList.add(highlightAffectedByLight = new GuiToggleButton(16, this.width / 2 - 100, 155, 150, 20, "Dim with light levels: ", BlockHighlightSettings.highlightAffectedByLight));
		this.buttonList.add(blinkSelectionBox = new GuiToggleButton(18, this.width / 2 - 100, 180, 150, 20, "Blink Block Highlight: ", BlockHighlightSettings.highlightBlink));
		this.buttonList.add(blinkTimerSlider = new GuiSlider(19, "Blink Time", this.width / 2 - 100, 205, 50, 3000, BlockHighlightSettings.highlightBlinkSpeed, 50));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		if(editingColor)
		{
			this.boxOutlineColorSelelection.drawScreen(mouseX, mouseY, partialTicks);
			this.boxFillColorSelelection.drawScreen(mouseX, mouseY, partialTicks);
		}
	}

	public void updateScreen()
	{
		buttonPressedThisUpdate = false;
	}

	protected void actionPerformed(GuiButton button)
	{
		if(buttonPressedThisUpdate)
			return;

		buttonPressedThisUpdate = true;

		if(button.enabled)
		{
			if(button.id == 100)
			{
				if(this.editingColor)
				{
					this.editingColor = false;
					this.boxFillColorSelelection.setVisible(false);
					this.boxOutlineColorSelelection.setVisible(false);
					this.customSelectionBox.visible = true;
					this.blockSelectionColors.visible = true;
					this.thicknessSlider.visible = true;
					this.useDefaultBox.visible = true;
					this.highlightAffectedByLight.visible = true;
					this.highlightBlockFaces.visible = true;
				}
				else
				{
					this.mc.displayGuiScreen(this.parentScreen);
				}
			}
			else if(button.id == 10)
			{
				BlockHighlightSettings.customHighlight = this.customSelectionBox.isToggledOn();
			}
			else if(button.id == 11)
			{
				this.editingColor = true;
				this.boxFillColorSelelection.setVisible(true);
				this.boxOutlineColorSelelection.setVisible(true);
				this.customSelectionBox.visible = false;
				this.blockSelectionColors.visible = false;
				this.thicknessSlider.visible = false;
				this.useDefaultBox.visible = false;
				this.highlightAffectedByLight.visible = false;
				this.highlightBlockFaces.visible = false;
			}
			else if(button.id == 15)
			{
				BlockHighlightSettings.includeDefaultHighlight = this.useDefaultBox.isToggledOn();
			}
			else if(button.id == 16)
			{
				BlockHighlightSettings.highlightAffectedByLight = this.highlightAffectedByLight.isToggledOn();
			}
			else if(button.id == 17)
			{
				BlockHighlightSettings.highlightBlockFaces = this.highlightBlockFaces.isToggledOn();
			}
			else if(button.id == 18)
			{
				BlockHighlightSettings.highlightBlink = this.blinkSelectionBox.isToggledOn();
			}

			BlockHighlightSettings.highlightBlinkSpeed = this.blinkTimerSlider.getValueAdjusted();
			BlockHighlighterConfigLoader.saveBlockHighlightSettings(boxOutlineColorSelelection, boxFillColorSelelection, thicknessSlider.getValueAdjusted());
		}
	}
}
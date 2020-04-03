package com.theprogrammingturkey.blockhighlighter.client.gui;

import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ConfigGui extends GuiScreen
{
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

	public ConfigGui()
	{
	}

	public void initGui()
	{
		this.buttons.clear();

		this.buttons.add(new GuiButton(100, this.width / 2 - 100, this.height - 25, 200, 20, "Back")
		{
			public void onClick(double mouseX, double mouseY)
			{
				if(editingColor)
				{
					editingColor = false;
					boxFillColorSelelection.setVisible(false);
					boxOutlineColorSelelection.setVisible(false);
					customSelectionBox.visible = true;
					blockSelectionColors.visible = true;
					thicknessSlider.visible = true;
					useDefaultBox.visible = true;
					highlightAffectedByLight.visible = true;
					highlightBlockFaces.visible = true;
					blinkSelectionBox.visible = true;
					blinkTimerSlider.visible = true;
				}
			}
		});

		boxOutlineColorSelelection = new GuiColorSelection("Block Highlight Outline", this.buttons, this.width / 2 - 100, 30);
		boxOutlineColorSelelection.setCurrentValues(BlockHighlightSettings.highlightColorR.get(), BlockHighlightSettings.highlightColorG.get(), BlockHighlightSettings.highlightColorB.get(), BlockHighlightSettings.highlightColorA.get());
		boxOutlineColorSelelection.setVisible(false);
		boxFillColorSelelection = new GuiColorSelection("Block Highlight Fill", this.buttons, this.width / 2 - 100, 150);
		boxFillColorSelelection.setCurrentValues(BlockHighlightSettings.fillColorR.get(), BlockHighlightSettings.fillColorG.get(), BlockHighlightSettings.fillColorB.get(), BlockHighlightSettings.fillColorA.get());
		boxFillColorSelelection.setVisible(false);

		this.buttons.add(useDefaultBox = new GuiToggleButton(15, this.width / 2 - 100, 30, 150, 20, "Vanilla selection box: ", BlockHighlightSettings.includeDefaultHighlight.get()));
		this.buttons.add(customSelectionBox = new GuiToggleButton(10, this.width / 2 - 100, 55, 150, 20, "Custom Selection Box: ", BlockHighlightSettings.customHighlight.get()));
		this.buttons.add(highlightBlockFaces = new GuiToggleButton(17, this.width / 2 - 100, 80, 150, 20, "Highlight Block Faces: ", BlockHighlightSettings.highlightBlockFaces.get()));
		this.buttons.add(blockSelectionColors = new GuiButton(11, this.width / 2 - 100, 105, 150, 20, "Set Colors")
		{
			public void onClick(double mouseX, double mouseY)
			{
				editingColor = true;
				boxFillColorSelelection.setVisible(true);
				boxOutlineColorSelelection.setVisible(true);
				customSelectionBox.visible = false;
				blockSelectionColors.visible = false;
				thicknessSlider.visible = false;
				useDefaultBox.visible = false;
				highlightAffectedByLight.visible = false;
				highlightBlockFaces.visible = false;
				blinkSelectionBox.visible = false;
				blinkTimerSlider.visible = false;
			}
		});
		this.buttons.add(thicknessSlider = new GuiSlider(14, "Thickness", this.width / 2 - 100, 130, 1F, 10F, BlockHighlightSettings.highlightLineThickness.get().floatValue(), 0.5F));
		this.buttons.add(highlightAffectedByLight = new GuiToggleButton(16, this.width / 2 - 100, 155, 150, 20, "Dim with light levels: ", BlockHighlightSettings.highlightAffectedByLight.get()));
		this.buttons.add(blinkSelectionBox = new GuiToggleButton(18, this.width / 2 - 100, 180, 150, 20, "Blink Block Highlight: ", BlockHighlightSettings.highlightBlink.get()));
		this.buttons.add(blinkTimerSlider = new GuiSlider(19, "Blink Time", this.width / 2 - 100, 205, 50, 3000, BlockHighlightSettings.highlightBlinkSpeed.get(), 50));
	}

	@Override
	public void tick()
	{
		this.drawDefaultBackground();
		super.tick();

		if(editingColor)
		{
			this.boxOutlineColorSelelection.drawScreen();
			this.boxFillColorSelelection.drawScreen();
		}

		//BlockHighlightSettings.highlightBlinkSpeed = this.blinkTimerSlider.getValueAdjusted(1);
	}

	@Override
	public void onGuiClosed()
	{
		//BlockHighlighterConfigLoader.saveBlockHighlightSettings(boxOutlineColorSelelection, boxFillColorSelelection, thicknessSlider.getValueAdjusted(1));
	}
}
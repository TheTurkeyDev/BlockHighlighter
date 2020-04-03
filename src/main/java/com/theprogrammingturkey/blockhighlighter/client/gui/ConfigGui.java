package com.theprogrammingturkey.blockhighlighter.client.gui;

import com.theprogrammingturkey.blockhighlighter.config.BHConfigLoader;
import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends Screen
{
	private GuiColorSelection boxOutlineColorSelelection;
	private GuiColorSelection boxFillColorSelelection;
	private GuiToggleButton customSelectionBox;
	private Button blockSelectionColors;
	private Slider thicknessSlider;
	private GuiToggleButton useDefaultBox;
	private GuiToggleButton highlightAffectedByLight;
	private GuiToggleButton highlightBlockFaces;
	private GuiToggleButton blinkSelectionBox;
	private Slider blinkTimerSlider;

	private boolean editingColor = false;

	public ConfigGui()
	{
		super(new StringTextComponent("Block Highlighter Config"));
	}

	public void init()
	{
		super.init();
		addButton(new Button(this.width / 2 - 75, this.height - 25, 150, 20, "Back", button ->
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
			else
			{
				onClose();
			}
		}));

		List<Button> buttonsToAdd = new ArrayList<>();
		boxOutlineColorSelelection = new GuiColorSelection("Block Highlight Outline", buttonsToAdd, this.width / 2 - 100, 30);
		boxOutlineColorSelelection.setCurrentValues(BlockHighlightSettings.highlightColorR.get(), BlockHighlightSettings.highlightColorG.get(), BlockHighlightSettings.highlightColorB.get(), BlockHighlightSettings.highlightColorA.get());
		boxOutlineColorSelelection.setVisible(false);
		boxFillColorSelelection = new GuiColorSelection("Block Highlight Fill", buttonsToAdd, this.width / 2 - 100, 150);
		boxFillColorSelelection.setCurrentValues(BlockHighlightSettings.fillColorR.get(), BlockHighlightSettings.fillColorG.get(), BlockHighlightSettings.fillColorB.get(), BlockHighlightSettings.fillColorA.get());
		boxFillColorSelelection.setVisible(false);
		for(Button b : buttonsToAdd)
			addButton(b);

		addButton(useDefaultBox = new GuiToggleButton(this.width / 2 - 100, 30, 150, 20, "Vanilla selection box: ", BlockHighlightSettings.includeDefaultHighlight));
		addButton(customSelectionBox = new GuiToggleButton(this.width / 2 - 100, 55, 150, 20, "Custom Selection Box: ", BlockHighlightSettings.customHighlight));
		addButton(highlightBlockFaces = new GuiToggleButton(this.width / 2 - 100, 80, 150, 20, "Highlight Block Faces: ", BlockHighlightSettings.highlightBlockFaces));
		addButton(blockSelectionColors = new Button(this.width / 2 - 100, 105, 150, 20, "Set Colors", click ->
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
		}));

		addButton(thicknessSlider = new Slider(this.width / 2 - 100, 130, "Thickness ", 1F, 10F, BlockHighlightSettings.highlightLineThickness.get().floatValue(), click ->
		{
		}, null));
		addButton(highlightAffectedByLight = new GuiToggleButton(this.width / 2 - 100, 155, 150, 20, "Dim with light levels: ", BlockHighlightSettings.highlightAffectedByLight));
		addButton(blinkSelectionBox = new GuiToggleButton(this.width / 2 - 100, 180, 150, 20, "Blink Block Highlight: ", BlockHighlightSettings.highlightBlink));
		addButton(blinkTimerSlider = new Slider(this.width / 2 - 100, 205, "Blink Time ", 50, 3000, BlockHighlightSettings.highlightBlinkSpeed.get(), click ->
		{
		}, null));
	}

	@Override
	public void render(int p_render_1_, int p_render_2_, float p_render_3_)
	{
		this.renderBackground();
		super.render(p_render_1_, p_render_2_, p_render_3_);

		if(editingColor)
		{
			this.boxOutlineColorSelelection.drawScreen();
			this.boxFillColorSelelection.drawScreen();
		}

		BlockHighlightSettings.highlightBlinkSpeed.set(this.blinkTimerSlider.getValueInt());
	}

	@Override
	public void onClose()
	{
		BHConfigLoader.saveBlockHighlightSettings(boxOutlineColorSelelection, boxFillColorSelelection, thicknessSlider.getValue());
		super.onClose();
	}
}
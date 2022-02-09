package com.theprogrammingturkey.blockhighlighter.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.theprogrammingturkey.blockhighlighter.config.BHConfigLoader;
import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.gui.widget.Slider;
import org.jetbrains.annotations.NotNull;

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
		super(new TextComponent("Block Highlighter Config"));
	}

	public void init()
	{
		super.init();
		addWidget(new Button(this.width / 2 - 75, this.height - 25, 150, 20, new TextComponent("Back"), button ->
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

		boxOutlineColorSelelection = new GuiColorSelection("Block Highlight Outline", this.width / 2 - 100, 30);
		boxOutlineColorSelelection.setCurrentValues(BlockHighlightSettings.highlightColorR.get(), BlockHighlightSettings.highlightColorG.get(), BlockHighlightSettings.highlightColorB.get(), BlockHighlightSettings.highlightColorA.get());
		boxOutlineColorSelelection.setVisible(false);
		addWidget(boxOutlineColorSelelection);
		boxFillColorSelelection = new GuiColorSelection("Block Highlight Fill", this.width / 2 - 100, 150);
		boxFillColorSelelection.setCurrentValues(BlockHighlightSettings.fillColorR.get(), BlockHighlightSettings.fillColorG.get(), BlockHighlightSettings.fillColorB.get(), BlockHighlightSettings.fillColorA.get());
		boxFillColorSelelection.setVisible(false);
		addWidget(boxFillColorSelelection);

		addWidget(useDefaultBox = new GuiToggleButton(this.width / 2 - 100, 30, 150, 20, new TextComponent("Vanilla selection box: "), BlockHighlightSettings.includeDefaultHighlight));
		addWidget(customSelectionBox = new GuiToggleButton(this.width / 2 - 100, 55, 150, 20, new TextComponent("Custom Selection Box: "), BlockHighlightSettings.customHighlight));
		addWidget(highlightBlockFaces = new GuiToggleButton(this.width / 2 - 100, 80, 150, 20, new TextComponent("Highlight Block Faces: "), BlockHighlightSettings.highlightBlockFaces));
		addWidget(blockSelectionColors = new Button(this.width / 2 - 100, 105, 150, 20, new TextComponent("Set Colors"), click ->
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

		addWidget(thicknessSlider = new Slider(this.width / 2 - 100, 130, new TextComponent("Thickness "), 1F, 10F, BlockHighlightSettings.highlightLineThickness.get().floatValue(), click ->
		{
		}, null));
		addWidget(highlightAffectedByLight = new GuiToggleButton(this.width / 2 - 100, 155, 150, 20, new TextComponent("Dim with light levels: "), BlockHighlightSettings.highlightAffectedByLight));
		addWidget(blinkSelectionBox = new GuiToggleButton(this.width / 2 - 100, 180, 150, 20, new TextComponent("Blink Block Highlight: "), BlockHighlightSettings.highlightBlink));
		addWidget(blinkTimerSlider = new Slider(this.width / 2 - 100, 205, new TextComponent("Blink Time "), 50, 3000, BlockHighlightSettings.highlightBlinkSpeed.get(), click ->
		{
		}, null));
	}

	@Override
	public void render(@NotNull PoseStack poseStack, int p_97922_, int p_97923_, float p_97924_)
	{
		super.render(poseStack, p_97922_, p_97923_, p_97924_);

//		if(editingColor)
//		{
//			this.boxOutlineColorSelelection.drawScreen();
//			this.boxFillColorSelelection.drawScreen();
//		}

		BlockHighlightSettings.highlightBlinkSpeed.set(this.blinkTimerSlider.getValueInt());
	}

	@Override
	public void onClose()
	{
		BHConfigLoader.saveBlockHighlightSettings(boxOutlineColorSelelection, boxFillColorSelelection, thicknessSlider.getValue());
		super.onClose();
	}
}
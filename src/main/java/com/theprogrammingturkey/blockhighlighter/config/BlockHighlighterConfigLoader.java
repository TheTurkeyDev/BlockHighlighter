package com.theprogrammingturkey.blockhighlighter.config;

import java.io.File;

import com.theprogrammingturkey.blockhighlighter.client.gui.GuiColorSelection;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockHighlighterConfigLoader
{
	public static Configuration config;

	public static final String bhCat = "Block Highlight Settings";

	public static final String thicknessAmount = "Highlight Line Thickness";
	public static final String defaultHighlight = "include Default Highlight";
	public static final String dimHighlight = "highlight Affected By Light";
	public static final String faceHighlight = "Highlight Block Faces";
	public static final String customHighlight = "Highlight Block Edges With Custom Color";

	public static final String redAmount = "Red amount";
	public static final String greenAmount = "Green amount";
	public static final String blueAmount = "Blue amount";
	public static final String alphaAmount = "Alpha amount";

	public static void loadConfigSettings(File file)
	{
		config = new Configuration(file);

		refreshSettings();
	}

	public static void refreshSettings()
	{
		config.load();

		BlockHighlightSettings.includeDefaultHighlight = config.getBoolean(defaultHighlight, bhCat, true, "Set to true to include the default thin black outline with the custom outline highlight");
		BlockHighlightSettings.highlightColorR = config.getFloat("Outline " + redAmount, bhCat, 0F, 0F, 1F, "Red color value to be mixed into the the block hightlight overall color");
		BlockHighlightSettings.highlightColorG = config.getFloat("Outline " + greenAmount, bhCat, 0F, 0F, 1F, "Green color value to be mixed into the the block hightlight overall color");
		BlockHighlightSettings.highlightColorB = config.getFloat("Outline " + blueAmount, bhCat, 0F, 0F, 1F, "Blue color value to be mixed into the the block hightlight overall color");
		BlockHighlightSettings.highlightColorA = config.getFloat("Outline " + alphaAmount, bhCat, 1F, 0F, 1F, "Alpha amount for block face hightlight overall");
		BlockHighlightSettings.fillColorR = config.getFloat("Fill " + redAmount, bhCat, 0F, 0F, 1F, "Red color value to be mixed into the the block hightlight overall color");
		BlockHighlightSettings.fillColorG = config.getFloat("Fill " + greenAmount, bhCat, 0F, 0F, 1F, "Green color value to be mixed into the the block hightlight overall color");
		BlockHighlightSettings.fillColorB = config.getFloat("Fill " + blueAmount, bhCat, 0F, 0F, 1F, "Blue color value to be mixed into the the block hightlight overall color");
		BlockHighlightSettings.fillColorA = config.getFloat("Fill " + alphaAmount, bhCat, 1F, 0F, 1F, "Alpha amount for block face hightlight overall");
		BlockHighlightSettings.highlightLineThickness = config.getFloat(thicknessAmount, bhCat, 2F, 1F, 10F, "How thick the highlight line should be.");
		BlockHighlightSettings.highlightAffectedByLight = config.getBoolean(dimHighlight, bhCat, false, "Set to true for the block highlight to dim to match the blocks light level");
		BlockHighlightSettings.highlightBlockFaces = config.getBoolean(faceHighlight, bhCat, false, "Set to true for block faces to be highlighted aswell");
		BlockHighlightSettings.customHighlight = config.getBoolean(customHighlight, bhCat, false, "Set to true for block edges to be highlighted with a custom color");

		config.save();
	}

	public static void saveBlockHighlightSettings(GuiColorSelection outline, GuiColorSelection fill, float thickness)
	{
		config.load();

		Property prop = config.get(bhCat, defaultHighlight, true);
		prop.set(BlockHighlightSettings.includeDefaultHighlight);

		prop = config.get(bhCat, "Outline " + redAmount, 0F);
		prop.set(outline.getFloatRed());
		BlockHighlightSettings.highlightColorR = outline.getFloatRed();

		prop = config.get(bhCat, "Outline " + greenAmount, 0F);
		prop.set(outline.getFloatGreen());
		BlockHighlightSettings.highlightColorG = outline.getFloatGreen();

		prop = config.get(bhCat, "Outline " + blueAmount, 0F);
		prop.set(outline.getFloatBlue());
		BlockHighlightSettings.highlightColorB = outline.getFloatBlue();

		prop = config.get(bhCat, "Outline " + alphaAmount, 1F);
		prop.set(outline.getFloatAlpha());
		BlockHighlightSettings.highlightColorA = outline.getFloatAlpha();

		prop = config.get(bhCat, "Fill " + redAmount, 0F);
		prop.set(fill.getFloatRed());
		BlockHighlightSettings.fillColorR = fill.getFloatRed();

		prop = config.get(bhCat, "Fill " + greenAmount, 0F);
		prop.set(fill.getFloatGreen());
		BlockHighlightSettings.fillColorG = fill.getFloatGreen();

		prop = config.get(bhCat, "Fill " + blueAmount, 0F);
		prop.set(fill.getFloatBlue());
		BlockHighlightSettings.fillColorB = fill.getFloatBlue();

		prop = config.get(bhCat, "Fill " + alphaAmount, 1F);
		prop.set(fill.getFloatAlpha());
		BlockHighlightSettings.fillColorA = fill.getFloatAlpha();

		prop = config.get(bhCat, thicknessAmount, 2F);
		prop.set(thickness);
		BlockHighlightSettings.highlightLineThickness = thickness;

		prop = config.get(bhCat, dimHighlight, false);
		prop.set(BlockHighlightSettings.highlightAffectedByLight);

		prop = config.get(bhCat, faceHighlight, false);
		prop.set(BlockHighlightSettings.highlightBlockFaces);

		prop = config.get(bhCat, customHighlight, false);
		prop.set(BlockHighlightSettings.customHighlight);

		config.save();
	}
}

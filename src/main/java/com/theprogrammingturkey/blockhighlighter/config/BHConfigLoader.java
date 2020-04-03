package com.theprogrammingturkey.blockhighlighter.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class BHConfigLoader
{
	public static final ForgeConfigSpec configSpec;
	public static final BHConfigLoader CONFIG;
	static
	{
		final Pair<BHConfigLoader, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(BHConfigLoader::new);
		configSpec = specPair.getRight();
		CONFIG = specPair.getLeft();
	}

	public static final String bhCat = "Block Highlight Settings";

	public static final String thicknessAmount = "Highlight Line Thickness";
	public static final String defaultHighlight = "include Default Highlight";
	public static final String dimHighlight = "highlight Affected By Light";
	public static final String faceHighlight = "Highlight Block Faces";
	public static final String blink = "Blink Highlight";
	public static final String blinkSpeed = "Blink Highlight Speed";
	public static final String customHighlight = "Highlight Block Edges With Custom Color";

	public static final String redAmount = "Red amount";
	public static final String greenAmount = "Green amount";
	public static final String blueAmount = "Blue amount";
	public static final String alphaAmount = "Alpha amount";

	public BHConfigLoader(ForgeConfigSpec.Builder builder)
	{
		builder.push(bhCat);

		// @formatter:off
		 BlockHighlightSettings.includeDefaultHighlight = builder
	                .comment("Set to true to include the default thin black outline with the custom outline highlight")
	                .define(defaultHighlight, true);
		 
		 BlockHighlightSettings.highlightColorR = builder
	                .comment("Red color value to be mixed into the the block hightlight overall color")
	                .defineInRange("Outline " + redAmount, 0d, 0d, 1d);
		 BlockHighlightSettings.highlightColorG = builder
	                .comment("Green color value to be mixed into the the block hightlight overall color")
	                .defineInRange("Outline " + greenAmount, 0d, 0d, 1d);
		 BlockHighlightSettings.highlightColorB = builder
	                .comment("Blue color value to be mixed into the the block hightlight overall color")
	                .defineInRange("Outline " + blueAmount, 0d, 0d, 1d);
		 BlockHighlightSettings.highlightColorA = builder
	                .comment("Alpha amount for block face hightlight overall")
	                .defineInRange("Outline " + alphaAmount, 1d, 0d, 1d);
		 
		 BlockHighlightSettings.fillColorR = builder
	                .comment("Red color value to be mixed into the the block hightlight overall color")
	                .defineInRange("Fill " + redAmount, 0d, 0d, 1d);
		 BlockHighlightSettings.fillColorG = builder
	                .comment("Green color value to be mixed into the the block hightlight overall color")
	                .defineInRange("Fill " + greenAmount, 0d, 0d, 1d);
		 BlockHighlightSettings.fillColorB = builder
	                .comment("Blue color value to be mixed into the the block hightlight overall color")
	                .defineInRange("Fill " + blueAmount, 0d, 0d, 1d);
		 BlockHighlightSettings.fillColorA = builder
	                .comment("Alpha amount for block face hightlight overall")
	                .defineInRange("Fill " + alphaAmount, 1d, 0d, 1d);
		 
		 BlockHighlightSettings.highlightLineThickness = builder
	                .comment("How thick the highlight line should be.")
	                .defineInRange(thicknessAmount, 2d, 1d, 10d);
		 BlockHighlightSettings.highlightAffectedByLight = builder
	                .comment("Set to true for the block highlight to dim to match the blocks light level")
	                .define(dimHighlight, false);
		 BlockHighlightSettings.highlightBlockFaces = builder
	                .comment("Set to true for block faces to be highlighted aswell")
	                .define(faceHighlight, false);
		 BlockHighlightSettings.customHighlight = builder
	                .comment("Set to true for block edges to be highlighted with a custom color")
	                .define(customHighlight, false);
		 BlockHighlightSettings.highlightBlink = builder
	                .comment("Set to true to make the block highlight blink.")
	                .define(blink, false);
		 
		 BlockHighlightSettings.highlightBlinkSpeed = builder
	                .comment("Timer for the block highlight blink (in miliseconds).")
	                .defineInRange(blinkSpeed, 200, 50, 3000);
		 
		 builder.pop();
		 
		// @formatter:on
	}

	/*
	 * public static void saveBlockHighlightSettings(GuiColorSelection outline, GuiColorSelection
	 * fill, float thickness) { config.load();
	 * 
	 * Property prop = config.get(bhCat, defaultHighlight, true);
	 * prop.set(BlockHighlightSettings.includeDefaultHighlight);
	 * 
	 * prop = config.get(bhCat, "Outline " + redAmount, 0F); prop.set(outline.getFloatRed());
	 * BlockHighlightSettings.highlightColorR = outline.getFloatRed();
	 * 
	 * prop = config.get(bhCat, "Outline " + greenAmount, 0F); prop.set(outline.getFloatGreen());
	 * BlockHighlightSettings.highlightColorG = outline.getFloatGreen();
	 * 
	 * prop = config.get(bhCat, "Outline " + blueAmount, 0F); prop.set(outline.getFloatBlue());
	 * BlockHighlightSettings.highlightColorB = outline.getFloatBlue();
	 * 
	 * prop = config.get(bhCat, "Outline " + alphaAmount, 1F); prop.set(outline.getFloatAlpha());
	 * BlockHighlightSettings.highlightColorA = outline.getFloatAlpha();
	 * 
	 * prop = config.get(bhCat, "Fill " + redAmount, 0F); prop.set(fill.getFloatRed());
	 * BlockHighlightSettings.fillColorR = fill.getFloatRed();
	 * 
	 * prop = config.get(bhCat, "Fill " + greenAmount, 0F); prop.set(fill.getFloatGreen());
	 * BlockHighlightSettings.fillColorG = fill.getFloatGreen();
	 * 
	 * prop = config.get(bhCat, "Fill " + blueAmount, 0F); prop.set(fill.getFloatBlue());
	 * BlockHighlightSettings.fillColorB = fill.getFloatBlue();
	 * 
	 * prop = config.get(bhCat, "Fill " + alphaAmount, 1F); prop.set(fill.getFloatAlpha());
	 * BlockHighlightSettings.fillColorA = fill.getFloatAlpha();
	 * 
	 * prop = config.get(bhCat, thicknessAmount, 2F); prop.set(thickness);
	 * BlockHighlightSettings.highlightLineThickness = thickness;
	 * 
	 * prop = config.get(bhCat, dimHighlight, false);
	 * prop.set(BlockHighlightSettings.highlightAffectedByLight);
	 * 
	 * prop = config.get(bhCat, faceHighlight, false);
	 * prop.set(BlockHighlightSettings.highlightBlockFaces);
	 * 
	 * prop = config.get(bhCat, customHighlight, false);
	 * prop.set(BlockHighlightSettings.customHighlight);
	 * 
	 * prop = config.get(bhCat, blink, false); prop.set(BlockHighlightSettings.highlightBlink);
	 * 
	 * prop = config.get(bhCat, blinkSpeed, 1000);
	 * prop.set(BlockHighlightSettings.highlightBlinkSpeed);
	 * 
	 * config.save(); }
	 */
}

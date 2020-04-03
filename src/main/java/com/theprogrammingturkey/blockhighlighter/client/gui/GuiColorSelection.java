package com.theprogrammingturkey.blockhighlighter.client.gui;

import com.theprogrammingturkey.blockhighlighter.util.RendererHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.util.List;

public class GuiColorSelection
{
	private String dispLabel;

	private Slider redSlider;
	private Slider greenSlider;
	private Slider blueSlider;
	private Slider alphaSlider;

	private int x;
	private int y;

	public GuiColorSelection(String disp, List<Button> buttonList, int x, int y)
	{
		this.dispLabel = disp;
		this.x = x;
		this.y = y;
		buttonList.add(redSlider = new Slider(x, y, "Red ", 0, 1, 0, press ->
		{
		}, null));
		buttonList.add(greenSlider = new Slider(x, y + 25, "Green ", 0, 1, 0, press ->
		{
		}, null));
		buttonList.add(blueSlider = new Slider(x, y + 50, "Blue ", 0, 1, 0, press ->
		{
		}, null));
		buttonList.add(alphaSlider = new Slider(x, y + 75, "Alpha ", 0, 1, 0, press ->
		{
		}, null));
	}

	public void drawScreen()
	{
		FontRenderer fr = Minecraft.getInstance().fontRenderer;
		fr.drawStringWithShadow(dispLabel + " Color", x + 160, y + 20, -1);
		int argb = getIntColor();
		int gradientX = x + 175;
		int gradientY = y + 40;
		RendererHelper.drawGradientRect(gradientX - 5, gradientY - 5, gradientX + 37, gradientY + 37, -1, -1);
		RendererHelper.drawGradientRect(gradientX, gradientY, gradientX + 32, gradientY + 32, argb, argb);
	}

	public void setCurrentValues(Double r, Double g, Double b, Double a)
	{
		redSlider.setValue(r.floatValue());
		greenSlider.setValue(g.floatValue());
		blueSlider.setValue(b.floatValue());
		alphaSlider.setValue(a.floatValue());
	}

	public void setVisible(boolean visible)
	{
		redSlider.visible = visible;
		greenSlider.visible = visible;
		blueSlider.visible = visible;
		alphaSlider.visible = visible;
	}

	public int getIntColor()
	{
		return (getIntAlpha() << 24) | (getIntRed() << 16) | (getIntGreen() << 8) | (getIntBlue());
	}

	public double getFloatRed()
	{
		return this.redSlider.getValue();
	}

	public double getFloatGreen()
	{
		return this.greenSlider.getValue();
	}

	public double getFloatBlue()
	{
		return this.blueSlider.getValue();
	}

	public double getFloatAlpha()
	{
		return this.alphaSlider.getValue();
	}

	public int getIntRed()
	{
		return (int) (redSlider.getValue() * 255);
	}

	public int getIntGreen()
	{
		return (int) (greenSlider.getValue() * 255);
	}

	public int getIntBlue()
	{
		return (int) (blueSlider.getValue() * 255);
	}

	public int getIntAlpha()
	{
		return (int) (alphaSlider.getValue() * 255);
	}
}

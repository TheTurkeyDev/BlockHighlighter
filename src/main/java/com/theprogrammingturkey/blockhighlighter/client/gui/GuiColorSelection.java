package com.theprogrammingturkey.blockhighlighter.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.gui.widget.Slider;
import org.jetbrains.annotations.NotNull;

public class GuiColorSelection extends AbstractWidget
{
	private final String dispLabel;

	private final Slider redSlider;
	private final Slider greenSlider;
	private final Slider blueSlider;
	private final Slider alphaSlider;

	public GuiColorSelection(String disp, int x, int y)
	{
		super(x, y, 175, 40, new TextComponent(""));
		this.dispLabel = disp;
		redSlider = new Slider(x, y, new TextComponent("Red "), 0, 1, 0, new EmptyPress(), null);
		greenSlider = new Slider(x, y + 25, new TextComponent("Green "), 0, 1, 0, new EmptyPress(), null);
		blueSlider = new Slider(x, y + 50, new TextComponent("Blue "), 0, 1, 0, new EmptyPress(), null);
		alphaSlider = new Slider(x, y + 75, new TextComponent("Alpha "), 0, 1, 0, new EmptyPress(), null);
	}

	@Override
	public void render(@NotNull PoseStack pose, int p_93658_, int p_93659_, float p_93660_)
	{
		Font fr = Minecraft.getInstance().font;
		fr.drawShadow(pose, dispLabel + " Color", x + 160, y + 20, -1);
		int argb = getIntColor();
		int gradientX = x + 175;
		int gradientY = y + 40;
		this.fillGradient(pose, gradientX - 5, gradientY - 5, gradientX + 37, gradientY + 37, -1, -1);
		this.fillGradient(pose, gradientX, gradientY, gradientX + 32, gradientY + 32, argb, argb);

		this.redSlider.render(pose, p_93658_, p_93659_, p_93660_);
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

	@Override
	public void updateNarration(@NotNull NarrationElementOutput output)
	{

	}

	private static class EmptyPress implements Button.OnPress
	{
		@Override
		public void onPress(Button btn)
		{

		}
	}
}

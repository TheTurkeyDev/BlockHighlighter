package com.theprogrammingturkey.blockhighlighter.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSlider extends GuiButton
{
	private float sliderValue;
	public boolean dragging;
	private final float minValue;
	private final float maxValue;
	private final String baseDisplay;
	private final float valueStep;

	public GuiSlider(int buttonId, String display, int x, int y, float minValueIn, float maxValue, float currentValue, float valueStep)
	{
		super(buttonId, x, y, 150, 20, "");
		this.minValue = minValueIn;
		this.maxValue = maxValue;
		this.sliderValue = this.normalizeValue(currentValue);
		this.baseDisplay = display;
		this.displayString = this.baseDisplay + ": " + this.denormalizeValue(this.sliderValue);
		this.valueStep = valueStep;
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over this button.
	 */
	protected int getHoverState(boolean mouseOver)
	{
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
	{
		if(this.visible)
		{
			float displayValue = MathHelper.clamp_float(this.sliderValue, 0F, 1F);
			if(this.dragging)
			{
				this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
				displayValue = MathHelper.clamp_float(this.sliderValue, 0F, 1F);
				float f = this.denormalizeValue(displayValue);
				this.sliderValue = this.normalizeValue(f);
				this.displayString = this.baseDisplay + ": " + f;
			}

			mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.xPosition + (int) (displayValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
			this.drawTexturedModalRect(this.xPosition + (int) (displayValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
		}
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent e).
	 */
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if(super.mousePressed(mc, mouseX, mouseY))
		{
			this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
			this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
			this.displayString = this.baseDisplay + ": " + this.sliderValue;
			this.dragging = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int mouseX, int mouseY)
	{
		this.dragging = false;
	}

	public float normalizeValue(float value)
	{
		return MathHelper.clamp_float((this.snapToStepClamp(value) - this.minValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
	}

	public float denormalizeValue(float value)
	{
		return this.snapToStepClamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp_float(value, 0.0F, 1.0F));
	}

	public float snapToStepClamp(float value)
	{
		value = this.snapToStep(value);
		return MathHelper.clamp_float(value, this.minValue, this.maxValue);
	}

	private float snapToStep(float value)
	{
		if(this.valueStep > 0.0F)
		{
			value = this.valueStep * (float) Math.round(value / this.valueStep);
		}

		return value;
	}

	public float getValue()
	{
		return this.sliderValue;
	}

	public int getValueAdjusted(float adjust)
	{
		return (int) (this.sliderValue * adjust);
	}

	public void setValue(float value)
	{
		this.sliderValue = value;
		this.displayString = this.baseDisplay + ": " + this.sliderValue;
	}
}
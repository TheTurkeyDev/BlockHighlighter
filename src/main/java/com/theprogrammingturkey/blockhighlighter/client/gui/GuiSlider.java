package com.theprogrammingturkey.blockhighlighter.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

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
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if
	 * it IS hovering over this button.
	 */
	protected int getHoverState(boolean mouseOver)
	{
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent
	 * e).
	 */
	protected void renderBg(Minecraft mc, int mouseX, int mouseY)
	{
		if(this.visible)
		{
			float displayValue = MathHelper.clamp(this.sliderValue, 0F, 1F);
			if(this.dragging)
			{
				this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
				displayValue = MathHelper.clamp(this.sliderValue, 0F, 1F);
				float f = this.denormalizeValue(displayValue);
				this.sliderValue = this.normalizeValue(f);
				this.displayString = this.baseDisplay + ": " + f;
			}

			mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.x + (int) (displayValue * (float) (this.width - 8)), this.y, 0, 66, 4, 20);
			this.drawTexturedModalRect(this.x + (int) (displayValue * (float) (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
		}
	}

	/**
	 * Called when the left mouse button is pressed over this button. This method is specific to
	 * GuiButton.
	 */
	public final void onClick(double mouseX, double mouseY)
	{
		this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
		this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
		this.displayString = this.baseDisplay + ": " + this.sliderValue;
		this.dragging = true;
	}

	/**
	 * Called when the left mouse button is released. This method is specific to GuiButton.
	 */
	public void onRelease(double mouseX, double mouseY)
	{
		this.dragging = false;
	}

	public float normalizeValue(float value)
	{
		return MathHelper.clamp((this.snapToStepClamp(value) - this.minValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
	}

	public float denormalizeValue(float value)
	{
		return this.snapToStepClamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp(value, 0.0F, 1.0F));
	}

	public float snapToStepClamp(float value)
	{
		value = this.snapToStep(value);
		return MathHelper.clamp(value, this.minValue, this.maxValue);
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

	public int getValueAdjusted(float adj)
	{
		float displayValue = MathHelper.clamp(this.sliderValue, 0F, 1F);
		return (int) (this.denormalizeValue(displayValue) * adj);
	}

	public void setValue(float value)
	{
		this.sliderValue = value;
		this.displayString = this.baseDisplay + ": " + this.sliderValue;
	}
}
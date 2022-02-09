package com.theprogrammingturkey.blockhighlighter.listener;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.DrawSelectionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockHighlightListener
{
	private static boolean blink = false;
	private static long blinkTimer = 0;

	@SubscribeEvent
	public void onBlockOutlineRender(DrawSelectionEvent.HighlightBlock e)
	{
		if(BlockHighlightSettings.highlightBlockFaces.get() || BlockHighlightSettings.customHighlight.get())
			drawSelectionBox(e.getPoseStack(), e.getTarget().getBlockPos());
		if(!BlockHighlightSettings.includeDefaultHighlight.get())
			e.setCanceled(true);
	}

	public void drawSelectionBox(PoseStack pose, BlockPos pos)
	{
		if(BlockHighlightSettings.highlightBlink.get())
		{
			if(System.currentTimeMillis() - blinkTimer > BlockHighlightSettings.highlightBlinkSpeed.get())
			{
				blinkTimer = System.currentTimeMillis();
				blink = !blink;
			}
			if(!blink)
				return;
		}
		float redHighlight = BlockHighlightSettings.highlightColorR.get().floatValue();
		float greenHighlight = BlockHighlightSettings.highlightColorG.get().floatValue();
		float blueHighlight = BlockHighlightSettings.highlightColorB.get().floatValue();
		float alphaHighlight = BlockHighlightSettings.highlightColorA.get().floatValue();
		renderLineBox(pose, pos, .1f, redHighlight, greenHighlight, blueHighlight, alphaHighlight);
		float redFill = BlockHighlightSettings.fillColorR.get().floatValue();
		float greenFill = BlockHighlightSettings.fillColorG.get().floatValue();
		float blueFill = BlockHighlightSettings.fillColorB.get().floatValue();
		float alphaFill = BlockHighlightSettings.fillColorA.get().floatValue();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		DebugRenderer.renderFilledBox(pos, .1f, redFill, greenFill, blueFill, alphaFill);
	}

	public void renderLineBox(PoseStack pose, BlockPos pos, float inflateAmount, float r, float g, float b, float a)
	{
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		if(camera.isInitialized())
		{
			Vec3 vec3 = camera.getPosition().reverse();
			AABB aabb = (new AABB(pos)).move(vec3).inflate(inflateAmount);
			renderLineBox(pose, aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ, r, g, b, a);
		}
	}

	public void renderLineBox(PoseStack pose, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float r, float g, float b, float a)
	{
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
		LevelRenderer.renderLineBox(pose, bufferbuilder, minX, minY, minZ, maxX, maxY, maxZ, r, g, b, a);
		tesselator.end();
	}
}

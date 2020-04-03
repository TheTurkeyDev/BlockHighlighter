package com.theprogrammingturkey.blockhighlighter.listener;

import com.mojang.blaze3d.platform.GlStateManager;
import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockHighlightListener
{
	private static boolean blink = false;
	private static long blinkTimer = 0;

	@SubscribeEvent
	public void onBlockOutlineRender(DrawBlockHighlightEvent e)
	{
		if(BlockHighlightSettings.highlightBlockFaces.get() || BlockHighlightSettings.customHighlight.get())
			this.drawSelectionBox(e.getInfo().getRenderViewEntity(), e.getInfo(), e.getTarget(), e.getSubID(), e.getPartialTicks());
		if(!BlockHighlightSettings.includeDefaultHighlight.get())
			e.setCanceled(true);
	}

	public void drawSelectionBox(Entity entity, ActiveRenderInfo renderInfo, RayTraceResult movingObjectPositionIn, int execute, float partialTicks)
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

		World theWorld = entity.getEntityWorld();
		if(execute == 0 && movingObjectPositionIn.getType() == RayTraceResult.Type.BLOCK)
		{
			BlockPos blockpos = ((BlockRayTraceResult) movingObjectPositionIn).getPos();
			BlockState blockstate = theWorld.getBlockState(blockpos);
			if(!blockstate.isAir(theWorld, blockpos) && theWorld.getWorldBorder().contains(blockpos))
			{
				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();
				GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.lineWidth(BlockHighlightSettings.highlightLineThickness.get().floatValue());
				GlStateManager.disableTexture();
				GlStateManager.depthMask(false);
				GlStateManager.matrixMode(5889);
				GlStateManager.pushMatrix();
				GlStateManager.scalef(1.0F, 1.0F, 0.999F);

				double d0 = renderInfo.getProjectedView().x;
				double d1 = renderInfo.getProjectedView().y;
				double d2 = renderInfo.getProjectedView().z;
				float redAmountHighlight = BlockHighlightSettings.highlightColorR.get().floatValue();
				float greenAmountHighlight = BlockHighlightSettings.highlightColorG.get().floatValue();
				float blueAmountHighlight = BlockHighlightSettings.highlightColorB.get().floatValue();
				float alphaAmountHighlight = BlockHighlightSettings.highlightColorA.get().floatValue();
				float redAmountFill = BlockHighlightSettings.fillColorR.get().floatValue();
				float greenAmountFill = BlockHighlightSettings.fillColorG.get().floatValue();
				float blueAmountFill = BlockHighlightSettings.fillColorB.get().floatValue();
				float alphaAmountFill = BlockHighlightSettings.fillColorA.get().floatValue();

				if(BlockHighlightSettings.highlightAffectedByLight.get())
				{
					BlockPos lightCheckPos;
					switch(Direction.byIndex(movingObjectPositionIn.subHit))
					{
						case DOWN:
							lightCheckPos = blockpos.add(0, -1, 0);
							break;
						case EAST:
							lightCheckPos = blockpos.add(1, 0, 0);
							break;
						case NORTH:
							lightCheckPos = blockpos.add(0, 0, -1);
							break;
						case SOUTH:
							lightCheckPos = blockpos.add(0, 0, 1);
							break;
						case UP:
							lightCheckPos = blockpos.add(0, 1, 0);
							break;
						case WEST:
							lightCheckPos = blockpos.add(-1, 0, 0);
							break;
						default:
							lightCheckPos = blockpos;
							break;
					}

					int blockLight = theWorld.getLightFor(LightType.BLOCK, lightCheckPos);
					int skyLight = theWorld.getLightFor(LightType.SKY, lightCheckPos) - theWorld.getSkylightSubtracted();
					float light = (float) (Math.max(blockLight, skyLight)) / 15f;
					light = Math.max(light, 0.5f);
					if(light > 0)
					{
						redAmountHighlight *= light;
						greenAmountHighlight *= light;
						blueAmountHighlight *= light;
						alphaAmountHighlight *= light;
						redAmountFill *= light;
						greenAmountFill *= light;
						blueAmountFill *= light;
						alphaAmountFill *= light;
					}
				}


				if(BlockHighlightSettings.highlightBlockFaces.get())
				{
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();
					bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
					AxisAlignedBB vox = blockstate.getRenderShape(theWorld, blockpos).getBoundingBox().expand(0.002, 0.002, 0.002).offset(d0, d1, d2);
					WorldRenderer.addChainedFilledBoxVertices(bufferbuilder, vox.minX, vox.minY, vox.minZ, vox.maxX, vox.maxY, vox.maxZ, redAmountFill, greenAmountFill, blueAmountFill, alphaAmountFill);
					tessellator.draw();
				}

				if(BlockHighlightSettings.customHighlight.get())
					WorldRenderer.drawShape(blockstate.getShape(theWorld, blockpos, ISelectionContext.forEntity(entity)), (double) blockpos.getX() - d0, (double) blockpos.getY() - d1, (double) blockpos.getZ() - d2, redAmountHighlight, greenAmountHighlight, blueAmountHighlight, alphaAmountHighlight);

				GlStateManager.popMatrix();
				GlStateManager.matrixMode(5888);
				GlStateManager.depthMask(true);
				GlStateManager.enableTexture();
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}
		}
	}
}

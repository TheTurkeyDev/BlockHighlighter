package com.theprogrammingturkey.blockhighlighter.listener;

import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumLightType;
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
			this.drawSelectionBox(e.getPlayer(), e.getTarget(), e.getSubID(), e.getPartialTicks());
		if(!BlockHighlightSettings.includeDefaultHighlight.get())
			e.setCanceled(true);
	}

	public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks)
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

		World theWorld = player.getEntityWorld();
		if(execute == 0 && movingObjectPositionIn.type == RayTraceResult.Type.BLOCK)
		{
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.lineWidth(BlockHighlightSettings.highlightLineThickness.get().floatValue());
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);
			BlockPos blockpos = movingObjectPositionIn.getBlockPos();
			IBlockState iblockstate = theWorld.getBlockState(blockpos);

			if(iblockstate.getMaterial() != Material.AIR && theWorld.getWorldBorder().contains(blockpos))
			{
				double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
				double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
				double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
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

					switch(movingObjectPositionIn.sideHit)
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

					int blockLight = theWorld.getLightFor(EnumLightType.BLOCK, lightCheckPos);
					int skyLight = theWorld.getLightFor(EnumLightType.SKY, lightCheckPos) - theWorld.getSkylightSubtracted();
					float light = (float) (blockLight > skyLight ? blockLight : skyLight) / 15f;
					light = light < 0.5f ? 0.5f : light;
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
					WorldRenderer.renderFilledBox(iblockstate.getRenderShape(theWorld, blockpos).getBoundingBox().expand(0.002, 0.002, 0.002).offset(d0, d1, d2), redAmountFill, greenAmountFill, blueAmountFill, alphaAmountFill);

				if(BlockHighlightSettings.customHighlight.get())
					WorldRenderer.drawShape(iblockstate.getShape(theWorld, blockpos), (double)blockpos.getX() - d0, (double)blockpos.getY() - d1, (double)blockpos.getZ() - d2, redAmountHighlight, greenAmountHighlight, blueAmountHighlight, alphaAmountHighlight);
			}

			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
}

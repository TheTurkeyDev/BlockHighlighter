package com.theprogrammingturkey.blockhighlighter.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.theprogrammingturkey.blockhighlighter.config.BlockHighlightSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockHighlightListener
{
	private static boolean blink = false;
	private static long blinkTimer = 0;

	@SubscribeEvent
	public void onBlockOutlineRender(DrawHighlightEvent e)
	{
		if(BlockHighlightSettings.highlightBlockFaces.get() || BlockHighlightSettings.customHighlight.get())
			this.drawSelectionBox(e.getMatrix(), e.getInfo(), e.getTarget(), Minecraft.getInstance().world);
		if(!BlockHighlightSettings.includeDefaultHighlight.get())
			e.setCanceled(true);
	}

	public void drawSelectionBox(MatrixStack matrix, ActiveRenderInfo activeRenderInfo, RayTraceResult rayTraceResult, World world)
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
		if(rayTraceResult != null && rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
		{
			BlockPos blockpos = ((BlockRayTraceResult) rayTraceResult).getPos();
			BlockState blockstate = world.getBlockState(blockpos);
			double d0 = activeRenderInfo.getProjectedView().x;
			double d1 = activeRenderInfo.getProjectedView().y;
			double d2 = activeRenderInfo.getProjectedView().z;
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
				switch(Direction.byIndex(rayTraceResult.subHit))
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

				int blockLight = world.getLightFor(LightType.BLOCK, lightCheckPos);
				int skyLight = world.getLightFor(LightType.SKY, lightCheckPos) - world.getSkylightSubtracted();
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
			if(!blockstate.isAir(world, blockpos) && world.getWorldBorder().contains(blockpos))
			{
				IVertexBuilder ivertexbuilder2 = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderType.getLines());
				drawSelectionBox(matrix, ivertexbuilder2, world, activeRenderInfo.getRenderViewEntity(), d0, d1, d2, blockpos, blockstate, redAmountHighlight, greenAmountHighlight, blueAmountHighlight, alphaAmountHighlight);
			}
		}
	}

	private void drawSelectionBox(MatrixStack matrixStackIn, IVertexBuilder bufferIn, World world, Entity entityIn, double xIn, double yIn, double zIn, BlockPos blockPosIn, BlockState blockStateIn, float red, float green, float blue, float alpha)
	{
		drawShape(matrixStackIn, bufferIn, blockStateIn.getShape(world, blockPosIn, ISelectionContext.forEntity(entityIn)), (double) blockPosIn.getX() - xIn, (double) blockPosIn.getY() - yIn, (double) blockPosIn.getZ() - zIn, red, green, blue, alpha);
	}

	private static void drawShape(MatrixStack matrixStackIn, IVertexBuilder bufferIn, VoxelShape shapeIn, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha)
	{
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		shapeIn.forEachEdge((p_230013_12_, p_230013_14_, p_230013_16_, p_230013_18_, p_230013_20_, p_230013_22_) ->
		{
			bufferIn.pos(matrix4f, (float) (p_230013_12_ + xIn), (float) (p_230013_14_ + yIn), (float) (p_230013_16_ + zIn)).color(red, green, blue, alpha).endVertex();
			bufferIn.pos(matrix4f, (float) (p_230013_18_ + xIn), (float) (p_230013_20_ + yIn), (float) (p_230013_22_ + zIn)).color(red, green, blue, alpha).endVertex();
		});
	}
}

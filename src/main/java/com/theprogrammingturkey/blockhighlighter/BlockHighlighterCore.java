package com.theprogrammingturkey.blockhighlighter;

import com.theprogrammingturkey.blockhighlighter.config.BHConfigLoader;
import com.theprogrammingturkey.blockhighlighter.listener.BlockHighlightListener;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(BlockHighlighterCore.MODID)
public class BlockHighlighterCore
{
	public static final String MODID = "blockhighlighter";

	public BlockHighlighterCore()
	{
		MinecraftForge.EVENT_BUS.register(new BlockHighlightListener());
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BHConfigLoader.configSpec);
	}
}
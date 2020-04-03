package com.theprogrammingturkey.blockhighlighter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.theprogrammingturkey.blockhighlighter.client.gui.ConfigGui;
import com.theprogrammingturkey.blockhighlighter.config.BHConfigLoader;
import com.theprogrammingturkey.blockhighlighter.listener.BlockHighlightListener;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(BlockHighlighterCore.MODID)
public class BlockHighlighterCore
{
	public static final String MODID = "blockhighlighter";

	public static final Logger logger = LogManager.getLogger(MODID);

	public BlockHighlighterCore()
	{
		MinecraftForge.EVENT_BUS.register(new BlockHighlightListener());
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BHConfigLoader.configSpec);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, parent) -> new ConfigGui());
	}
}
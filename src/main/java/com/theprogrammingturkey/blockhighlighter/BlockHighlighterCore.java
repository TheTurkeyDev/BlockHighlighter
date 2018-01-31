package com.theprogrammingturkey.blockhighlighter;

import org.apache.logging.log4j.Logger;

import com.theprogrammingturkey.blockhighlighter.config.BlockHighlighterConfigLoader;
import com.theprogrammingturkey.blockhighlighter.listener.BlockHighlightListener;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = BlockHighlighterCore.MODID, version = BlockHighlighterCore.VERSION, name = BlockHighlighterCore.NAME, guiFactory = BlockHighlighterCore.GUIFACTORY)
public class BlockHighlighterCore
{
	public static final String MODID = "blockhighlighter";
	public static final String VERSION = "1.0";
	public static final String NAME = "Block Highlighter";
	public static final String GUIFACTORY = "com.theprogrammingturkey.blockhighlighter.config.ConfigGuiFactory";

	@Instance(value = MODID)
	public static BlockHighlighterCore instance;
	public static Logger logger;

	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{	
		logger = event.getModLog();

		if(event.getSide() == Side.CLIENT)
			MinecraftForge.EVENT_BUS.register(new BlockHighlightListener());

		BlockHighlighterConfigLoader.loadConfigSettings(event.getSuggestedConfigurationFile());
	}
}
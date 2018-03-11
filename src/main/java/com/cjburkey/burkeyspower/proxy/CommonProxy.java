package com.cjburkey.burkeyspower.proxy;

import com.cjburkey.burkeyspower.BurkeysPower;
import com.cjburkey.burkeyspower.crafting.FurnaceRecipe;
import com.cjburkey.burkeyspower.item.ModItems;
import com.cjburkey.burkeyspower.world.BurkeysPowerOreGeneration;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public static Configuration config;
	
	public void construction(FMLConstructionEvent e) {
		BurkeysPower.logger.info("Constructing Burkey's Power");
	}
	
	public void preinit(FMLPreInitializationEvent e) {
		BurkeysPower.logger.info("Preinitializing Burkey's Power");
		
		BurkeysPower.logger.info("Loading configuration");
		config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();
		config.save();
		
		ModItems.tabBurkeysPower.setBackgroundImageName("burkeyspower_background.png");
	}
	
	public void init(FMLInitializationEvent e) {
		BurkeysPower.logger.info("Initializing Burkey's Power");

		BurkeysPower.logger.info("Registering ore generation");
		GameRegistry.registerWorldGenerator(new BurkeysPowerOreGeneration(), 0);

		BurkeysPower.logger.info("Registering furnace recipes");
		FurnaceRecipe.commonInit();
	}
	
	public void postinit(FMLPostInitializationEvent e) {
		BurkeysPower.logger.info("Postinitializing Burkey's Power");
	}
	
}
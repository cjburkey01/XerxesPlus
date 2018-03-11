package com.cjburkey.burkeyspower.proxy;

import com.cjburkey.burkeyspower.BurkeysPower;
import com.cjburkey.burkeyspower.container.ContainerInventory;
import com.cjburkey.burkeyspower.crafting.FurnaceRecipe;
import com.cjburkey.burkeyspower.gui.GuiContainerBasic;
import com.cjburkey.burkeyspower.gui.GuiHandler;
import com.cjburkey.burkeyspower.gui.GuiHandler.GuiRegister;
import com.cjburkey.burkeyspower.item.ModItems;
import com.cjburkey.burkeyspower.tile.ModTiles;
import com.cjburkey.burkeyspower.tile.TileEntityInventory;
import com.cjburkey.burkeyspower.world.BurkeysPowerOreGeneration;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public static Configuration config;
	
	public static int guiBasicContainerId;
	
	private void registerGuis() {
		// Container Basic
		guiBasicContainerId = GuiHandler.addGui(new GuiRegister() {
			public Object onServer(EntityPlayer player, World world, int x, int y, int z) {
				return new ContainerInventory(GuiContainerBasic.INV_DEF, player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
			public Object onClient(EntityPlayer player, World world, int x, int y, int z) {
				return new GuiContainerBasic(GuiContainerBasic.TEXTURE, 176, 166, player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
			}
		});
	}
	
	public void construction(FMLConstructionEvent e) {
		BurkeysPower.logger.info("Constructing Burkey's Power");
	}
	
	public void preinit(FMLPreInitializationEvent e) {
		BurkeysPower.logger.info("Preinitializing Burkey's Power");
		
		BurkeysPower.logger.info("Loading configuration");
		config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();
		config.save();

		BurkeysPower.logger.info("Registering tile entities");
		ModTiles.commonPreinit();
		
		BurkeysPower.logger.info("Registering GUI handler");
		NetworkRegistry.INSTANCE.registerGuiHandler(BurkeysPower.instance, new GuiHandler());

		BurkeysPower.logger.info("Registering GUIs");
		registerGuis();
		
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
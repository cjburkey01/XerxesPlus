package com.cjburkey.burkeyspower.proxy;

import java.lang.reflect.InvocationTargetException;
import com.cjburkey.burkeyspower.BurkeysPower;
import com.cjburkey.burkeyspower.block.BlockContainerAdvanced;
import com.cjburkey.burkeyspower.block.BlockContainerBasic;
import com.cjburkey.burkeyspower.block.BlockContainerExtreme;
import com.cjburkey.burkeyspower.container.ContainerInventory;
import com.cjburkey.burkeyspower.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.burkeyspower.crafting.FurnaceRecipe;
import com.cjburkey.burkeyspower.gui.GuiContainerAdvanced;
import com.cjburkey.burkeyspower.gui.GuiContainerBasic;
import com.cjburkey.burkeyspower.gui.GuiContainerExtreme;
import com.cjburkey.burkeyspower.gui.GuiHandler;
import com.cjburkey.burkeyspower.gui.GuiHandler.GuiRegister;
import com.cjburkey.burkeyspower.gui.GuiInventoryBase;
import com.cjburkey.burkeyspower.item.ModItems;
import com.cjburkey.burkeyspower.tile.ModTiles;
import com.cjburkey.burkeyspower.world.BurkeysPowerOreGeneration;
import net.minecraft.entity.player.EntityPlayer;
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
	public static int guiAdvancedContainerId;
	public static int guiExtremeContainerId;
	
	private void registerGuis() {
		guiBasicContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerBasic.INV_DEF, GuiContainerBasic.class));
		guiAdvancedContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerAdvanced.INV_DEF, GuiContainerAdvanced.class));
		guiExtremeContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerExtreme.INV_DEF, GuiContainerExtreme.class));
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
	
	public static class GuiInventoryRegister extends GuiRegister {
		
		private InventoryDefinition invDef;
		private Class<? extends GuiInventoryBase> guiClass;
		
		public GuiInventoryRegister(InventoryDefinition invDef, Class<? extends GuiInventoryBase> guiClass) {
			this.invDef = invDef;
			this.guiClass = guiClass;
		}
		
		public Object onServer(EntityPlayer player, World world, int x, int y, int z) {
			return new ContainerInventory(invDef, player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		public Object onClient(EntityPlayer player, World world, int x, int y, int z) {
			try {
				return guiClass.getConstructor(IInventory.class, IInventory.class).newInstance(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				BurkeysPower.logger.error("Failed to create inventory GUI on client: " + e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
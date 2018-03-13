package com.cjburkey.xerxesplus.proxy;

import java.lang.reflect.InvocationTargetException;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.block.BlockContainerAdvanced;
import com.cjburkey.xerxesplus.block.BlockContainerBasic;
import com.cjburkey.xerxesplus.block.BlockContainerExtreme;
import com.cjburkey.xerxesplus.block.BlockXpStore;
import com.cjburkey.xerxesplus.container.ContainerInventory;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.crafting.FurnaceRecipe;
import com.cjburkey.xerxesplus.crafting.PounderRecipe;
import com.cjburkey.xerxesplus.gui.GuiContainerAdvanced;
import com.cjburkey.xerxesplus.gui.GuiContainerBasic;
import com.cjburkey.xerxesplus.gui.GuiContainerExtreme;
import com.cjburkey.xerxesplus.gui.GuiContainerXpStore;
import com.cjburkey.xerxesplus.gui.GuiHandler;
import com.cjburkey.xerxesplus.gui.GuiHandler.GuiRegister;
import com.cjburkey.xerxesplus.gui.GuiInventoryBase;
import com.cjburkey.xerxesplus.item.ModItems;
import com.cjburkey.xerxesplus.packet.PacketHandler;
import com.cjburkey.xerxesplus.tile.ModTiles;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import com.cjburkey.xerxesplus.world.XerxesPlusOreGeneration;
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
	public static int guiXpStoreId;
	
	public static int maxXp;
	
	private void registerGuis() {
		guiBasicContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerBasic.INV_DEF, GuiContainerBasic.class));
		guiAdvancedContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerAdvanced.INV_DEF, GuiContainerAdvanced.class));
		guiExtremeContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerExtreme.INV_DEF, GuiContainerExtreme.class));
		guiXpStoreId = GuiHandler.addGui(new GuiInventoryRegister(BlockXpStore.INV_DEF, GuiContainerXpStore.class));
	}
	
	public void construction(FMLConstructionEvent e) {
		XerxesPlus.logger.info("Constructing Xerxes Plus");
	}
	
	public void preinit(FMLPreInitializationEvent e) {
		XerxesPlus.logger.info("Preinitializing Xerxes Plus");
		
		XerxesPlus.logger.info("Loading configuration");
		config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();
		
		maxXp = config.getInt("maxXp", "xpStore", Integer.MAX_VALUE, 1, Integer.MAX_VALUE, "How much experience the XP Storage block may hold (this is NOT in levels).");
		
		config.save();

		XerxesPlus.logger.info("Registering tile entities");
		ModTiles.commonPreinit();
		
		XerxesPlus.logger.info("Registering GUI handler");
		NetworkRegistry.INSTANCE.registerGuiHandler(XerxesPlus.instance, new GuiHandler());

		XerxesPlus.logger.info("Registering GUIs");
		registerGuis();
		
		XerxesPlus.logger.info("Registering packets");
		PacketHandler.commonPreinit();
		
		ModItems.tabXerxesPlus.setBackgroundImageName("xerxesplus_background.png");
	}
	
	public void init(FMLInitializationEvent e) {
		XerxesPlus.logger.info("Initializing Xerxes Plus");

		XerxesPlus.logger.info("Registering ore generation");
		GameRegistry.registerWorldGenerator(new XerxesPlusOreGeneration(), 0);

		XerxesPlus.logger.info("Registering furnace recipes");
		FurnaceRecipe.commonInit();
		
		XerxesPlus.logger.info("Registering pounder recipes");
		PounderRecipe.commonInit();
	}
	
	public void postinit(FMLPostInitializationEvent e) {
		XerxesPlus.logger.info("Postinitializing Xerxes Plus");
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
				return guiClass.getConstructor(IInventory.class, TileEntityInventory.class).newInstance(player.inventory, (TileEntityInventory) world.getTileEntity(new BlockPos(x, y, z)));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				XerxesPlus.logger.error("Failed to create inventory GUI on client: " + e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
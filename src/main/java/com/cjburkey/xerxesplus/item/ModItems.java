package com.cjburkey.xerxesplus.item;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class ModItems {
	
	public static Item itemDebug;
	
	public static Item meticulumDust;
	public static Item meticulumIngot;
	public static Item meticulumNugget;
	
	private static void createItems() {
		itemDebug = createItem(new Item(), "item_debug").setCreativeTab(null);
		
		meticulumDust = createItem(new Item(), "dust_meticulum");
		meticulumIngot = createItem(new Item(), "ingot_meticulum");
		meticulumNugget = createItem(new Item(), "nugget_meticulum");
	}
	
	private static void createOreDictionary() {
		OreDictionary.registerOre("ingotMeticulum", new ItemStack(ModItems.meticulumIngot));
		OreDictionary.registerOre("nuggetMeticulum", new ItemStack(ModItems.meticulumNugget));
		OreDictionary.registerOre("dustMeticulum", new ItemStack(ModItems.meticulumDust));
		OreDictionary.registerOre("blockMeticulum", new ItemStack(ModBlocks.blockMeticulum));
		OreDictionary.registerOre("oreMeticulum", new ItemStack(ModBlocks.blockMeticulumOre));
	}
	
	// -- STATIC ITEM REGISTRATION -- //
	
	public static final CreativeTabs tabXerxesPlus = new CreativeTabs("tab_xerxesplus") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.meticulumIngot, 1);
		}
	};
	
	private static final List<Item> items = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		XerxesPlus.logger.info("Registering items for Xerxes Plus");
		createItems();
		for (Block block : ModBlocks.getBlocks()) {
			createItem(new ItemBlock(block), block.getRegistryName().getResourcePath());
		}
		for (Item item : items) {
			e.getRegistry().register(item);
		}
		createOreDictionary();
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent e) {
		XerxesPlus.logger.info("Registering item renders for Xerxes Plus");
		for (Item item : items) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	private static Item createItem(Item item, String unlocName) {
		item.setUnlocalizedName(unlocName);
		item.setRegistryName(ModInfo.MODID, unlocName);
		item.setCreativeTab(tabXerxesPlus);
		items.add(item);
		return item;
	}
	
}
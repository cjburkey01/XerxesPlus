package com.cjburkey.xerxesplus.block;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.item.ModItems;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModBlocks {
	
	public static Block blockMeticulumOre;
	public static Block blockContainerBasic;
	public static Block blockContainerAdvanced;
	public static Block blockContainerExtreme;
	public static Block blockXpStore;
	public static Block blockQuarry;
	
	private static void createBlocks() {
		blockMeticulumOre = createBlock(new BlockOre(2.5f), "block_meticulum_ore");
		blockContainerBasic = createBlock(new BlockContainerBasic(), "block_container_basic");
		blockContainerAdvanced = createBlock(new BlockContainerAdvanced(), "block_container_advanced");
		blockContainerExtreme = createBlock(new BlockContainerExtreme(), "block_container_extreme");
		blockXpStore = createBlock(new BlockXpStore(), "block_xp_store");
		blockQuarry = createBlock(new BlockQuarry(), "block_quarry");
	}
	
	// -- STATIC BLOCK REGISTRATION -- //
	
	private static final List<Block> blocks = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		XerxesPlus.logger.info("Registering blocks for Xerxes Plus");
		createBlocks();
		for (Block block : blocks) {
			e.getRegistry().register(block);
		}
	}
	
	private static Block createBlock(Block block, String unlocName) {
		block.setUnlocalizedName(unlocName);
		block.setRegistryName(ModInfo.MODID, unlocName);
		block.setCreativeTab(ModItems.tabXerxesPlus);
		blocks.add(block);
		return block;
	}
	
	public static Block[] getBlocks() {
		return blocks.toArray(new Block[blocks.size()]);
	}
	
}
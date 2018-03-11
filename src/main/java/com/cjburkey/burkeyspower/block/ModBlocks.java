package com.cjburkey.burkeyspower.block;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.burkeyspower.BurkeysPower;
import com.cjburkey.burkeyspower.ModInfo;
import com.cjburkey.burkeyspower.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModBlocks {
	
	public static Block blockDebug;
	
	public static Block blockMeticulum;
	public static Block blockMeticulumOre;
	
	private static void createBlocks() {
		blockDebug = createBlock(new Block(Material.GROUND), "block_debug").setCreativeTab(null);
		
		blockMeticulum = createBlock(new BlockMetal(), "block_meticulum");
		blockMeticulumOre = createBlock(new BlockOre(2.5f), "block_meticulum_ore");
	}
	
	// -- STATIC BLOCK REGISTRATION -- //
	
	private static final List<Block> blocks = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		BurkeysPower.logger.info("Registering blocks for Burkey's Power");
		createBlocks();
		for (Block block : blocks) {
			e.getRegistry().register(block);
		}
	}
	
	private static Block createBlock(Block block, String unlocName) {
		block.setUnlocalizedName(unlocName);
		block.setRegistryName(ModInfo.MODID, unlocName);
		block.setCreativeTab(ModItems.tabBurkeysPower);
		blocks.add(block);
		return block;
	}
	
	public static Block[] getBlocks() {
		return blocks.toArray(new Block[blocks.size()]);
	}
	
}
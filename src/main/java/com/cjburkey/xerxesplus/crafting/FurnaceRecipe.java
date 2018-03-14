package com.cjburkey.xerxesplus.crafting;

import com.cjburkey.xerxesplus.block.ModBlocks;
import com.cjburkey.xerxesplus.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnaceRecipe {
	
	public static void commonInit() {
		GameRegistry.addSmelting(new ItemStack(ModBlocks.blockMeticulumOre, 1), new ItemStack(ModItems.meticulumIngot, 1), 1.0f);
		GameRegistry.addSmelting(new ItemStack(ModItems.meticulumDust, 1), new ItemStack(ModItems.meticulumIngot, 1), 0.0f);
	}
	
}
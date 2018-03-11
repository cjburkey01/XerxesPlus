package com.cjburkey.burkeyspower.crafting;

import com.cjburkey.burkeyspower.block.ModBlocks;
import com.cjburkey.burkeyspower.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnaceRecipe {
	
	public static void commonInit() {
		GameRegistry.addSmelting(new ItemStack(ModBlocks.blockMeticulumOre, 1), new ItemStack(ModItems.meticulumIngot, 1), 1.0f);
	}
	
}
package com.cjburkey.burkeyspower.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockOre extends Block {
	
	public BlockOre(float hardness) {
		super(Material.GROUND);
		setSoundType(SoundType.METAL);
		setHardness(hardness);
		setHarvestLevel("pickaxe", 2);
	}
	
}
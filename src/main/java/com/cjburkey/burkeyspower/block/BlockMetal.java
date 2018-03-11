package com.cjburkey.burkeyspower.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMetal extends Block {
	
	public BlockMetal() {
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setHardness(3.5f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
}
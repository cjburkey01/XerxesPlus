package com.cjburkey.xerxesplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMetal extends Block {
	
	public BlockMetal() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHardness(3.5f);
		setHarvestLevel("pickaxe", 2);
	}
	
}
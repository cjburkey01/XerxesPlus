package com.cjburkey.burkeyspower.block;

import com.cjburkey.burkeyspower.tile.TileEntityContainerBasic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockContainerBasic extends Block {

	public BlockContainerBasic() {
		super(Material.IRON);
		setHardness(1.0f);
		setHarvestLevel("pickaxe", 1);
	}
	
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityContainerBasic("tile_container_basic", 27);
	}
	
}
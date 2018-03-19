package com.cjburkey.xerxesplus.block;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.proxy.CommonProxy;
import com.cjburkey.xerxesplus.tile.TileEntityTrash;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTrash extends Block implements ITileEntityProvider {
	
	public static InventoryDefinition INV_DEF = new InventoryDefinition(176, 166, 1, 0, 80, 35, 58, 8, 84);
	
	public BlockTrash() {
		super(Material.IRON);
		setHardness(1.5f);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 0);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ply, EnumHand hand, EnumFacing facing, float x, float y, float z) {
		if (!world.isRemote) {
			ply.openGui(XerxesPlus.instance, CommonProxy.guiTrashId, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTrash();
	}
	
}
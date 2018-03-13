package com.cjburkey.xerxesplus.block;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.proxy.CommonProxy;
import com.cjburkey.xerxesplus.tile.TileEntityXpStore;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockXpStore extends BlockContainer {
	
	public static InventoryDefinition INV_DEF = new InventoryDefinition(176, 166, 0, 0, 8, 18, 58, 8, 84);
	
	public BlockXpStore() {
		super(Material.IRON);
		setHardness(1.0f);
		setHarvestLevel("pickaxe", 1);
		setSoundType(SoundType.METAL);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ply, EnumHand hand, EnumFacing facing, float x, float y, float z) {
		if (!world.isRemote) {
			ply.openGui(XerxesPlus.instance, CommonProxy.guiXpStoreId, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityXpStore();
	}
	
}
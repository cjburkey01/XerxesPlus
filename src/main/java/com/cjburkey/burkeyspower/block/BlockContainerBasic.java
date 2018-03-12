package com.cjburkey.burkeyspower.block;

import com.cjburkey.burkeyspower.BurkeysPower;
import com.cjburkey.burkeyspower.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.burkeyspower.proxy.CommonProxy;
import com.cjburkey.burkeyspower.tile.TileEntityContainerBasic;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockContainerBasic extends BlockContainer {
	
	public static InventoryDefinition INV_DEF = new InventoryDefinition(176, 166, 9, 3, 8, 18, 58, 8, 84);
	
	public BlockContainerBasic() {
		super(Material.IRON);
		setHardness(1.0f);
		setHarvestLevel("pickaxe", 1);
		setSoundType(SoundType.METAL);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ply, EnumHand hand, EnumFacing facing, float x, float y, float z) {
		if (!world.isRemote) {
			ply.openGui(BurkeysPower.instance, CommonProxy.guiBasicContainerId, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, (IInventory) world.getTileEntity(pos));
		super.breakBlock(world, pos, state);
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityContainerBasic();
	}
	
}
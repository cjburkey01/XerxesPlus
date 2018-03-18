package com.cjburkey.xerxesplus.block;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.proxy.CommonProxy;
import com.cjburkey.xerxesplus.tile.TileEntityQuarry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockQuarry extends Block implements ITileEntityProvider {
	
	public static InventoryDefinition INV_DEF = new InventoryDefinition(176, 166, 0, 0, 8, 18, 58, 8, 84);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockQuarry() {
		super(Material.IRON);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setHardness(3.5f);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ply, EnumHand hand, EnumFacing facing, float x, float y, float z) {
		if (!world.isRemote) {
			ply.openGui(XerxesPlus.instance, CommonProxy.guiQuarryId, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
	}
	
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityQuarry();
	}
	
}
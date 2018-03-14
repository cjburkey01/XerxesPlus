package com.cjburkey.xerxesplus.block;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.proxy.CommonProxy;
import com.cjburkey.xerxesplus.tile.TileEntityXpStore;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockXpStore extends BlockContainer {
	
	public static InventoryDefinition INV_DEF = new InventoryDefinition(176, 166, 0, 0, 8, 18, 58, 8, 84);
	
	public static final AxisAlignedBB BASE_BOUNDING_BOX = new AxisAlignedBB(1.0f / 16.0f, 0.0f, 1.0f / 16.0f, 15.0f / 16.0f, 4.0f / 16.0f, 15.0f / 16.0f);
	public static final AxisAlignedBB TOPPING_BOUNDING_BOX = new AxisAlignedBB(4.5f / 16.0f, 4.0f / 16.0f, 4.5f / 16.0f, 11.5f / 16.0f, 6.0f / 16.0f, 11.5f / 16.0f);
	public static final AxisAlignedBB COMBINED_BOUNDING_BOX = new AxisAlignedBB(1.0f / 16.0f, 0.0f, 1.0f / 16.0f, 15.0f / 16.0f, 6.0f / 16.0f, 15.0f / 16.0f);
	
	public BlockXpStore() {
		super(Material.IRON);
		setHardness(1.0f);
		setHarvestLevel("pickaxe", 1);
		setSoundType(SoundType.METAL);
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return COMBINED_BOUNDING_BOX;
	}
	
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) {
            state = getActualState(state, worldIn, pos);
        }
        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
	}
	
	// Override because it shows up as deprecated (for no reason)
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state;
	}
	
	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate) {
		List<AxisAlignedBB> list = new ArrayList<>();
		list.add(BASE_BOUNDING_BOX);
		list.add(TOPPING_BOUNDING_BOX);
		return list;
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return canPlaceOn(worldIn, pos.down());
	}
	
	private boolean canPlaceOn(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return state != null && state.isFullBlock();
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
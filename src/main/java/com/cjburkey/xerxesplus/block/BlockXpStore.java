package com.cjburkey.xerxesplus.block;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.proxy.CommonProxy;
import com.cjburkey.xerxesplus.tile.TileEntityXpStore;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import javax.annotation.Nonnull;
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

    private static final AxisAlignedBB BASE_BOUNDING_BOX = new AxisAlignedBB(1.0f / 16.0f, 0.0f, 1.0f / 16.0f, 15.0f / 16.0f, 4.0f / 16.0f, 15.0f / 16.0f);
    private static final AxisAlignedBB TOPPING_BOUNDING_BOX = new AxisAlignedBB(4.5f / 16.0f, 4.0f / 16.0f, 4.5f / 16.0f, 11.5f / 16.0f, 6.0f / 16.0f, 11.5f / 16.0f);
    private static final AxisAlignedBB COMBINED_BOUNDING_BOX = new AxisAlignedBB(1.0f / 16.0f, 0.0f, 1.0f / 16.0f, 15.0f / 16.0f, 6.0f / 16.0f, 15.0f / 16.0f);

    BlockXpStore() {
        super(Material.IRON);
        setHardness(1.0f);
        setHarvestLevel("pickaxe", 1);
        setSoundType(SoundType.METAL);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return COMBINED_BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    public void addCollisionBoxToList(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        for (AxisAlignedBB axisalignedbb : getCollisionBoxList()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    // Override because it shows up as deprecated (for no reason)
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state;
    }

    private static List<AxisAlignedBB> getCollisionBoxList() {
        ObjectArrayList<AxisAlignedBB> list = new ObjectArrayList<>();
        list.add(BASE_BOUNDING_BOX);
        list.add(TOPPING_BOUNDING_BOX);
        return list;
    }

    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean canPlaceBlockAt(@Nonnull World worldIn, BlockPos pos) {
        return canPlaceOn(worldIn, pos.down());
    }

    private boolean canPlaceOn(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.isFullBlock();
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ply, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        if (!world.isRemote) {
            ply.openGui(XerxesPlus.instance, CommonProxy.guiXpStoreId, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new TileEntityXpStore();
    }

}

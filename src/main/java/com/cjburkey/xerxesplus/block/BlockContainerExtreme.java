package com.cjburkey.xerxesplus.block;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.proxy.CommonProxy;
import com.cjburkey.xerxesplus.tile.TileEntityContainerExtreme;
import java.util.Objects;
import javax.annotation.Nonnull;
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

public class BlockContainerExtreme extends BlockContainer {

    public static InventoryDefinition INV_DEF = new InventoryDefinition(248, 240, 13, 7, 8, 18, 58, 44, 158);

    BlockContainerExtreme() {
        super(Material.IRON);
        setHardness(1.0f);
        setHarvestLevel("pickaxe", 1);
        setSoundType(SoundType.METAL);
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ply, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        if (!world.isRemote) {
            ply.openGui(XerxesPlus.instance, CommonProxy.guiExtremeContainerId, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        InventoryHelper.dropInventoryItems(world, pos, (IInventory) Objects.requireNonNull(world.getTileEntity(pos)));
        super.breakBlock(world, pos, state);
    }

    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEntityContainerExtreme();
    }

}

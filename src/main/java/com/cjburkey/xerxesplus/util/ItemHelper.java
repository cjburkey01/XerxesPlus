package com.cjburkey.xerxesplus.util;

import com.cjburkey.xerxesplus.player.FakePlayerHandler;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemHelper {

    public static List<ItemStack> getDropsForBlock(World world, BlockPos pos, int fortune) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        NonNullList<ItemStack> drops = NonNullList.create();
        block.getDrops(drops, world, pos, state, fortune);
        ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, fortune, 1.0f, false, FakePlayerHandler.getFakePlayer());
        return drops;
    }

    public static NBTTagCompound addItemsToTag(String tagName, NBTTagCompound nbt, List<ItemStack> items) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }
        NBTTagList itemTag = new NBTTagList();
        for (ItemStack stack : items) {
            itemTag.appendTag(stack.serializeNBT());
        }
        nbt.setTag(tagName, itemTag);
        return nbt;
    }

    public static List<ItemStack> getItemsFromTag(String tagName, NBTTagCompound tag) {
        NonNullList<ItemStack> out = NonNullList.create();
        NBTTagList items = tag.getTagList(tagName, new NBTTagCompound().getId());
        for (int i = 0; i < items.tagCount(); i++) {
            out.add(new ItemStack(items.getCompoundTagAt(i)));
        }
        return out;
    }

    public static List<ItemStack> getItemsFromTag(String tagName, NBTTagCompound tag, int size) {
        NonNullList<ItemStack> out = NonNullList.withSize(size, ItemStack.EMPTY);
        if (tag == null) {
            return out;
        }
        NBTTagList items = tag.getTagList(tagName, new NBTTagCompound().getId());
        if (items.tagCount() != size) {
            return out;
        }
        for (int i = 0; i < items.tagCount(); i++) {
            out.set(i, new ItemStack(items.getCompoundTagAt(i)));
        }
        return out;
    }

    private static boolean addStackToInventory(ItemStack stack, IItemHandler inventory) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            stack = inventory.insertItem(i, stack, false);
            if (stack.equals(ItemStack.EMPTY)) {
                return true;
            }
        }
        return false;
    }

    private static boolean addStackToTileEntity(EnumFacing facing, ItemStack item, TileEntity ent) {
        if (ent == null) {
            return false;
        }
        if (!ent.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
            return false;
        }
        IItemHandler itemHandler = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
        return addStackToInventory(item, Objects.requireNonNull(itemHandler));
    }

    public static boolean addStackToAdjacentInventories(ItemStack item, World world, BlockPos pos) {
        if (addStackToTileEntity(EnumFacing.SOUTH, item, world.getTileEntity(pos.north()))) {
            return true;
        }
        if (addStackToTileEntity(EnumFacing.NORTH, item, world.getTileEntity(pos.south()))) {
            return true;
        }
        if (addStackToTileEntity(EnumFacing.WEST, item, world.getTileEntity(pos.east()))) {
            return true;
        }
        if (addStackToTileEntity(EnumFacing.EAST, item, world.getTileEntity(pos.west()))) {
            return true;
        }
        if (addStackToTileEntity(EnumFacing.DOWN, item, world.getTileEntity(pos.up()))) {
            return true;
        }
        return addStackToTileEntity(EnumFacing.UP, item, world.getTileEntity(pos.down()));
    }

}

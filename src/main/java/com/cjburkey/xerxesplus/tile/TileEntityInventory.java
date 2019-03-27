package com.cjburkey.xerxesplus.tile;

import com.cjburkey.xerxesplus.util.ItemHelper;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class TileEntityInventory extends TileEntity implements IInventory {

    private IItemHandler itemHandler;
    private String name;
    private String customName = null;
    private int size;
    private final NonNullList<ItemStack> stacks;

    protected TileEntityInventory(String name, int size) {
        this.name = name;
        this.size = size;
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Nonnull
    public String getName() {
        return (customName == null) ? name : customName;
    }

    @Nonnull
    public ITextComponent getDisplayName() {
        return (hasCustomName()) ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }

    public boolean hasCustomName() {
        return customName != null;
    }

    public int getSizeInventory() {
        return size;
    }

    public boolean isEmpty() {
        for (ItemStack stack : stacks) if (!stack.isEmpty()) return false;
        return true;
    }

    @Nonnull
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= size) return ItemStack.EMPTY;
        return stacks.get(index);
    }

    @Nonnull
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack;
        if ((stack = getStackInSlot(index)).isEmpty() || count <= 0) return ItemStack.EMPTY;
        if (stack.getCount() <= count) {
            setInventorySlotContents(index, ItemStack.EMPTY);
            return stack;
        }
        ItemStack ret = stack.copy();
        ret.setCount(count);
        stack.setCount(stack.getCount() - count);
        markDirty();
        return ret;
    }

    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        final ItemStack stack;
        if ((stack = getStackInSlot(index)).isEmpty()) return ItemStack.EMPTY;
        setInventorySlotContents(index, ItemStack.EMPTY);
        return stack;
    }

    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        if (index < 0 || index >= size) {
            return;
        }
        stacks.set(index, stack);
        markDirty();
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        return true;
    }

    public void openInventory(@Nonnull EntityPlayer player) {

    }

    public void closeInventory(@Nonnull EntityPlayer player) {

    }

    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return index >= 0 && index < size;
    }

    public int getField(int id) {
        return 0;
    }

    public void setField(int id, int value) {

    }

    public int getFieldCount() {
        return 0;
    }

    public void clear() {
        stacks.clear();
    }

    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
        if (hasCustomName()) nbt.setString("custom_name", customName);
        return super.writeToNBT(ItemHelper.addItemsToTag("items", nbt, stacks));
    }

    public void readFromNBT(@Nonnull NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("custom_name")) customName = nbt.getString("custom_name");
        else customName = null;
        if (nbt.hasKey("items")) {
            int i = 0;
            for (ItemStack stack : ItemHelper.getItemsFromTag("items", nbt, size)) setInventorySlotContents(i++, stack);
        }
    }

    private IItemHandler createUnSidedHandler() {
        return new InvWrapper(this);
    }

    private IItemHandler getUnSidedHandler() {
        if (itemHandler == null) return (itemHandler = createUnSidedHandler());
        return itemHandler;
    }


    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) return (T) getUnSidedHandler();
        return super.getCapability(capability, facing);
    }

    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
    }

}

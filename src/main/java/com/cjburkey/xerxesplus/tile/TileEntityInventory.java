package com.cjburkey.xerxesplus.tile;

import com.cjburkey.xerxesplus.util.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
	
	public String getName() {
		return (customName == null) ? name : customName;
	}
	
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
		for (ItemStack stack : stacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= size) {
			return ItemStack.EMPTY;
		}
		return stacks.get(index);
	}
	
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack;
		if ((stack = getStackInSlot(index)).isEmpty() || count <= 0) {
			return ItemStack.EMPTY;
		}
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
	
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		if ((stack = getStackInSlot(index)).isEmpty()) {
			return ItemStack.EMPTY;
		}
		setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}
	
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= size || stack.equals(null)) {
			return;
		}
		stacks.set(index, stack);
		markDirty();
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}
	
	public void openInventory(EntityPlayer player) {
		
	}
	
	public void closeInventory(EntityPlayer player) {
		
	}
	
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index < 0 || index >= size) {
			return false;
		}
		return true;
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
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		if (hasCustomName()) {
			nbt.setString("custom_name", customName);
		}
		return super.writeToNBT(ItemHelper.addItemsToTag("items", nbt, stacks));
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt == null || !nbt.hasKey("items")) {
			return;
		}
		if (nbt.hasKey("custom_name")) {
			customName = nbt.getString("custom_name");
		} else {
			customName = null;
		}
		int i = 0;
		for (ItemStack stack : ItemHelper.getItemsFromTag("items", nbt, size)) {
			setInventorySlotContents(i ++, stack);
		}
	}
	
	protected IItemHandler createUnSidedHandler() {
		return new InvWrapper(this);
	}
	
	protected IItemHandler getUnSidedHandler() {
		if (itemHandler == null) {
			return (itemHandler = createUnSidedHandler());
		}
		return itemHandler;
	}
	
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			return (T) getUnSidedHandler();
		}
		return super.getCapability(capability, facing);
	}
	
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
	}
	
}
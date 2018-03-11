package com.cjburkey.burkeyspower.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class TileEntityInventory extends TileEntity implements IInventory {
	
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
		NBTTagList items = new NBTTagList();
		for (ItemStack stack : stacks) {
			items.appendTag(stack.serializeNBT());
		}
		nbt.setTag("items", items);
		return super.writeToNBT(nbt);
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
		NBTTagList items = nbt.getTagList("items", new NBTTagCompound().getId());
		if (items.tagCount() != size) {
			return;
		}
		for (int i = 0; i < items.tagCount(); i ++) {
			setInventorySlotContents(i, new ItemStack(items.getCompoundTagAt(i)));
		}
	}
	
}
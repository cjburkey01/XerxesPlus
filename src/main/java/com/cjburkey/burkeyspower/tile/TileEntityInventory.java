package com.cjburkey.burkeyspower.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public abstract class TileEntityInventory extends TileEntity implements IInventory {
	
	private String name;
	private int size;
	private final NonNullList<ItemStack> stacks;
	
	protected TileEntityInventory(String name, int size) {
		this.name = name;
		this.size = size;
		stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasCustomName() {
		return false;
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
		ItemStack ret = stack.copy();
		if (stack.getCount() <= count) {
			stack.setCount(0);
			return ret;
		}
		ret.setCount(count);
		stack.setCount(stack.getCount() - count);
		return ret;
	}
	
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		if ((stack = getStackInSlot(index)).isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack ret = stack.copy();
		stack.setCount(0);
		return ret;
	}
	
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= size || stack.equals(null)) {
			return;
		}
		stacks.set(index, stack);
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
	
}
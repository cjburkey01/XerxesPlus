package com.cjburkey.xerxesplus.container;

import com.cjburkey.xerxesplus.block.BlockTrash;
import com.cjburkey.xerxesplus.gui.core.SlotUpgradeLocked;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTrash extends Container {
	
	private IInventory playerInv;
	private IInventory inventory;
	
	public ContainerTrash(IInventory player, IInventory inventory) {
		playerInv = player;
		this.inventory = inventory;
		
		addSlots();
	}
	
	private void addSlots() {
		// Inventory slot
		addSlotToContainer(new Slot(inventory, 0, BlockTrash.INV_DEF.getStartInvX(), BlockTrash.INV_DEF.getStartInvY()));
		
		// Player Inventory Slots
		for (int y = 0; y < 3; y ++) {
			for (int x = 0; x < 9; x ++) {
				addSlotToContainer(new Slot(playerInv, 9 + y * 9 + x, BlockTrash.INV_DEF.getStartPlyX() + x * 18, BlockTrash.INV_DEF.getStartPlyY() + y * 18));
			}
		}
		
		// Hotbar slots
		for (int x = 0; x < 9; x ++) {
			addSlotToContainer(new Slot(playerInv, x, BlockTrash.INV_DEF.getStartPlyX() + x * 18, BlockTrash.INV_DEF.getStartPlyY() + BlockTrash.INV_DEF.getHotbarOffset()));
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if (index < inventory.getSizeInventory()) {
				if (!mergeItemStack(stackInSlot, inventory.getSizeInventory(), inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(stackInSlot, 0, inventory.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}
			if (stackInSlot.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return stack;
	}
	
	public boolean canInteractWith(EntityPlayer ply) {
		return inventory.isUsableByPlayer(ply);
	}
	
}
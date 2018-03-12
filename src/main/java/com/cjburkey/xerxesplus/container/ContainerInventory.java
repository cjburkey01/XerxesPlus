package com.cjburkey.xerxesplus.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInventory extends Container {
	
	private static final int slotSkip = 18;
	
	private IInventory playerInv;
	private IInventory inventory;
	private InventoryDefinition invDef;
	
	public ContainerInventory(InventoryDefinition invDef, IInventory player, IInventory inventory) {
		playerInv = player;
		this.inventory = inventory;
		this.invDef = invDef;
		addStuffAndSlots();
	}
	
	private void addStuffAndSlots() {
		// Inventory slots
		for (int y = 0; y < invDef.getSlotsY(); y ++) {
			for (int x = 0; x < invDef.getSlotsX(); x ++) {
				addSlotToContainer(new Slot(inventory, y * invDef.getSlotsX() + x, invDef.getStartInvX() + x * slotSkip, invDef.getStartInvY() + y * slotSkip));
			}
		}
		
		// Player Inventory Slots
		for (int y = 0; y < 3; y ++) {
			for (int x = 0; x < 9; x ++) {
				addSlotToContainer(new Slot(playerInv, 9 + y * 9 + x, invDef.getStartPlyX() + x * slotSkip, invDef.getStartPlyY() + y * slotSkip));
			}
		}
		
		// Hotbar slots
		for (int x = 0; x < 9; x ++) {
			addSlotToContainer(new Slot(playerInv, x, invDef.getStartPlyX() + x * slotSkip, invDef.getStartPlyY() + invDef.getHotbarOffset()));
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
	
	public static class InventoryDefinition {
		
		private int width;
		private int height;
		private int slotsX;
		private int slotsY;
		private int startInvX;
		private int startInvY;
		private int hotbarOffset;
		private int startPlyX;
		private int startPlyY;
		
		public InventoryDefinition(int width, int height, int slotsX, int slotsY, int startInvX, int startInvY, int hotbarOffset, int startPlyX, int startPlyY) {
			this.width = width;
			this.height = height;
			this.slotsX = slotsX;
			this.slotsY = slotsY;
			this.startInvX = startInvX;
			this.startInvY = startInvY;
			this.hotbarOffset = hotbarOffset;
			this.startPlyX = startPlyX;
			this.startPlyY = startPlyY;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getSlotsX() {
			return slotsX;
		}
		
		public int getSlotsY() {
			return slotsY;
		}
		
		public int getStartInvX() {
			return startInvX;
		}
		
		public int getStartInvY() {
			return startInvY;
		}
		
		public int getHotbarOffset() {
			return hotbarOffset;
		}
		
		public int getStartPlyX() {
			return startPlyX;
		}
		
		public int getStartPlyY() {
			return startPlyY;
		}
		
	}
	
}
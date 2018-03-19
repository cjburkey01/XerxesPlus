package com.cjburkey.xerxesplus.gui.core;

import com.cjburkey.xerxesplus.item.ItemUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgradeLocked extends Slot {
	
	private final int upgradeId;
	
	public SlotUpgradeLocked(int upgradeId, IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
		this.upgradeId = upgradeId;
	}
	
	public boolean isItemValid(ItemStack stack) {
		if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof ItemUpgrade)) {
			return false;
		}
		return ((ItemUpgrade) stack.getItem()).upgradeType == upgradeId;
	}
	
}
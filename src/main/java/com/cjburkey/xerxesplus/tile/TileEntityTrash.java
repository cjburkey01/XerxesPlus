package com.cjburkey.xerxesplus.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class TileEntityTrash extends TileEntityInventory implements ITickable {
	
	public TileEntityTrash() {
		super("tile_trash", 1);
	}
	
	public void update() {
		setInventorySlotContents(0, ItemStack.EMPTY);
	}
	
}
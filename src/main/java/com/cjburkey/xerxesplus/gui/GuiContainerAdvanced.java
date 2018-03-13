package com.cjburkey.xerxesplus.gui;

import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.block.BlockContainerAdvanced;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerAdvanced extends GuiInventoryBase {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_container_advanced.png");
	
	public GuiContainerAdvanced(IInventory player, TileEntityInventory inventory) {
		super(BlockContainerAdvanced.INV_DEF, TEXTURE, player, inventory);
	}
	
}
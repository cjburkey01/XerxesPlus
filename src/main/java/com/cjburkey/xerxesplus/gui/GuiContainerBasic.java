package com.cjburkey.xerxesplus.gui;

import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.block.BlockContainerBasic;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerBasic extends GuiInventoryBase {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_container_basic.png");
	
	public GuiContainerBasic(IInventory player, TileEntityInventory inventory) {
		super(BlockContainerBasic.INV_DEF, TEXTURE, player, inventory);
	}
	
}
package com.cjburkey.xerxesplus.gui;

import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.block.BlockContainerExtreme;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerExtreme extends GuiInventoryBase {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_container_extreme.png");
	
	public GuiContainerExtreme(IInventory player, TileEntityInventory inventory) {
		super(BlockContainerExtreme.INV_DEF, TEXTURE, player, inventory);
	}
	
}
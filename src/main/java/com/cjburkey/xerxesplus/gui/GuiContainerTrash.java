package com.cjburkey.xerxesplus.gui;

import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.block.BlockTrash;
import com.cjburkey.xerxesplus.container.ContainerTrash;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerTrash extends GuiInventoryBase {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_trash.png");
	
	public GuiContainerTrash(IInventory player, TileEntityInventory inventory) {
		super(new ContainerTrash(player, inventory), BlockTrash.INV_DEF, TEXTURE, player, inventory);
	}
	
}
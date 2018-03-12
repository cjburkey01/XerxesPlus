package com.cjburkey.burkeyspower.gui;

import com.cjburkey.burkeyspower.ModInfo;
import com.cjburkey.burkeyspower.block.BlockContainerBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerBasic extends GuiInventoryBase {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_container_basic.png");
	
	public GuiContainerBasic(IInventory player, IInventory inventory) {
		super(BlockContainerBasic.INV_DEF, TEXTURE, player, inventory);
	}
	
}
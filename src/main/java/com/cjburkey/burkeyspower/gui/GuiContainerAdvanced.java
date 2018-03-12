package com.cjburkey.burkeyspower.gui;

import com.cjburkey.burkeyspower.ModInfo;
import com.cjburkey.burkeyspower.block.BlockContainerAdvanced;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerAdvanced extends GuiInventoryBase {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_container_advanced.png");
	
	public GuiContainerAdvanced(IInventory player, IInventory inventory) {
		super(BlockContainerAdvanced.INV_DEF, TEXTURE, player, inventory);
	}
	
}
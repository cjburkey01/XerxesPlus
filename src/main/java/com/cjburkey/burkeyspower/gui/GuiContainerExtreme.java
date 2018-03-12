package com.cjburkey.burkeyspower.gui;

import com.cjburkey.burkeyspower.ModInfo;
import com.cjburkey.burkeyspower.block.BlockContainerExtreme;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerExtreme extends GuiInventoryBase {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_container_extreme.png");
	
	public GuiContainerExtreme(IInventory player, IInventory inventory) {
		super(BlockContainerExtreme.INV_DEF, TEXTURE, player, inventory);
	}
	
}
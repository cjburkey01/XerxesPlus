package com.cjburkey.burkeyspower.gui;

import com.cjburkey.burkeyspower.container.ContainerInventory;
import com.cjburkey.burkeyspower.container.ContainerInventory.InventoryDefinition;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiInventoryBase extends GuiContainer {
	
	private IInventory playerInv;
	private IInventory inventory;
	private ResourceLocation texture;
	private InventoryDefinition invDef;
	
	public GuiInventoryBase(InventoryDefinition invDef, ResourceLocation texture, IInventory player, IInventory inventory) {
		super(new ContainerInventory(invDef, player, inventory));
		this.invDef = invDef;
		playerInv = player;
		this.inventory = inventory;
		this.texture = texture;
		xSize = invDef.getWidth();
		ySize = invDef.getHeight();
	}
	
	protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	public void drawGuiContainerForegroundLayer(int mx, int my) {
		super.drawGuiContainerForegroundLayer(mx, my);
		String name = inventory.getDisplayName().getUnformattedText();
		fontRenderer.drawString(name, (xSize / 2) - (fontRenderer.getStringWidth(name) / 2), 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, invDef.getStartPlyY() - 12, 0x404040);
	}
	
}
package com.cjburkey.burkeyspower.gui;

import com.cjburkey.burkeyspower.ModInfo;
import com.cjburkey.burkeyspower.container.ContainerInventory;
import com.cjburkey.burkeyspower.container.ContainerInventory.InventoryDefinition;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerBasic extends GuiContainer {
	
	public static InventoryDefinition INV_DEF = new InventoryDefinition(9, 3, 8, 18, 58, 8, 84);
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_container_basic.png");
	
	private IInventory playerInv;
	private IInventory inventory;
	private ResourceLocation texture;
	
	public GuiContainerBasic(ResourceLocation texture, int sizeX, int sizeY, IInventory player, IInventory inventory) {
		super(new ContainerInventory(INV_DEF, player, inventory));
		playerInv = player;
		this.inventory = inventory;
		this.texture = texture;
		xSize = sizeX;
		ySize = sizeY;
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
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, 72, 0x404040);
	}
	
}
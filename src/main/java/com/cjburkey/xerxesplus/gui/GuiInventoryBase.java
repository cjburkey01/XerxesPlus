package com.cjburkey.xerxesplus.gui;

import com.cjburkey.xerxesplus.container.ContainerInventory;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import java.util.Objects;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiInventoryBase extends GuiContainer {

    private final IInventory playerInv;
    protected final TileEntityInventory inventory;
    protected final ResourceLocation texture;
    private final InventoryDefinition invDef;

    GuiInventoryBase(InventoryDefinition invDef, ResourceLocation texture, IInventory player, TileEntityInventory inventory) {
        this(new ContainerInventory(invDef, player, inventory), invDef, texture, player, inventory);
    }

    GuiInventoryBase(Container container, InventoryDefinition invDef, ResourceLocation texture, IInventory player, TileEntityInventory inventory) {
        super(container);
        this.invDef = invDef;
        playerInv = player;
        this.inventory = inventory;
        this.texture = texture;
        xSize = invDef.getWidth();
        ySize = invDef.getHeight();
    }

    public void drawScreen(int mx, int my, float partials) {
        drawDefaultBackground();
        super.drawScreen(mx, my, partials);
        renderHoveredToolTip(mx, my);
    }

    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    public void drawGuiContainerForegroundLayer(int mx, int my) {
        super.drawGuiContainerForegroundLayer(mx, my);
        String name = Objects.requireNonNull(inventory.getDisplayName()).getUnformattedText();
        fontRenderer.drawString(name, (xSize / 2) - (fontRenderer.getStringWidth(name) / 2), 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, invDef.getStartPlyY() - 12, 0x404040);
    }

}

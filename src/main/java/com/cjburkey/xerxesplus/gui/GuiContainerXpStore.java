package com.cjburkey.xerxesplus.gui;

import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.List;
import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.block.BlockXpStore;
import com.cjburkey.xerxesplus.gui.core.GuiToolTip;
import com.cjburkey.xerxesplus.gui.core.ToolTipManager;
import com.cjburkey.xerxesplus.gui.core.ToolTipManager.ToolTipRenderer;
import com.cjburkey.xerxesplus.packet.PacketHandler;
import com.cjburkey.xerxesplus.packet.PacketTakeXpToServer;
import com.cjburkey.xerxesplus.packet.PacketXpToServer;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import com.cjburkey.xerxesplus.util.XpCalculation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiContainerXpStore extends GuiInventoryBase implements ToolTipRenderer {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_xp_store.png");
	private static GuiContainerXpStore INSTANCE;
	private static DecimalFormat formatter;
	private static String xpString;
	private static String xpLevelString;
	
	private ToolTipManager ttm;
	
	public GuiContainerXpStore(IInventory player, TileEntityInventory inventory) {
		super(BlockXpStore.INV_DEF, TEXTURE, player, inventory);
		INSTANCE = this;
	}
	
	public void initGui() {
		super.initGui();
		
		ttm = new ToolTipManager();
		
		addButton(new GuiButton(0, guiLeft + 10, guiTop + 42, 20, 20, "+"));		// 1 level
		addButton(new GuiButton(1, guiLeft + 35, guiTop + 42, 20, 20, "++"));		// 5 levels
		addButton(new GuiButton(2, guiLeft + 60, guiTop + 42, 20, 20, "+++"));		// All levels
		addButton(new GuiButton(3, guiLeft + xSize - 80, guiTop + 42, 20, 20, "-"));
		addButton(new GuiButton(4, guiLeft + xSize - 55, guiTop + 42, 20, 20, "--"));
		addButton(new GuiButton(5, guiLeft + xSize - 30, guiTop + 42, 20, 20, "---"));
		
		ttm.addToolTip(new GuiToolTip(new Rectangle(10, 42, 20, 20), I18n.format("xp_add_one")));
		ttm.addToolTip(new GuiToolTip(new Rectangle(35, 42, 20, 20), I18n.format("xp_add_five")));
		ttm.addToolTip(new GuiToolTip(new Rectangle(60, 42, 20, 20), I18n.format("xp_add_all")));
		ttm.addToolTip(new GuiToolTip(new Rectangle(xSize - 80, 42, 20, 20), I18n.format("xp_take_one")));
		ttm.addToolTip(new GuiToolTip(new Rectangle(xSize - 55, 42, 20, 20), I18n.format("xp_take_five")));
		ttm.addToolTip(new GuiToolTip(new Rectangle(xSize - 30, 42, 20, 20), I18n.format("xp_take_all")));
	}
	
	public void actionPerformed(GuiButton btn) {
		switch (btn.id) {
		case 0:
			tryTakeLevels(1);
			break;
		case 1:
			tryTakeLevels(5);
			break;
		case 2:
			tryTakeLevels(Integer.MAX_VALUE);
			break;
		case 3:
			tryTakeLevels(-1);
			break;
		case 4:
			tryTakeLevels(-5);
			break;
		case 5:
			tryTakeLevels(Integer.MIN_VALUE);
			break;
		}
	}
	
	private void tryTakeLevels(int level) {
		PacketHandler.sendToServer(new PacketTakeXpToServer(inventory.getPos(), level));
	}
	
	public void drawScreen(int mx, int my, float delta) {
		drawDefaultBackground();
		super.drawScreen(mx, my, delta);
		renderHoveredToolTip(mx, my);
		ttm.drawTooltips(this, mx, my);
	}
	
	public void drawGuiContainerForegroundLayer(int mx, int my) {
		super.drawGuiContainerForegroundLayer(mx, my);
		
		PacketHandler.sendToServer(new PacketXpToServer(inventory.getPos()));
		
		fontRenderer.drawString(xpLevelString, (xSize - fontRenderer.getStringWidth(xpLevelString)) / 2, 18, 0x404040);
		fontRenderer.drawString(xpString, (xSize - fontRenderer.getStringWidth(xpString)) / 2, 30, 0x404040);
	}
	
	public void drawTextS(List<String> strings, int x, int y, FontRenderer font) {
		drawHoveringText(strings, x, y, font);
	}
	
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
	
	// -- STATIC EVENT LISTENER -- //
	
	public static void updateValues(int xpLevel, int xpStored, int maxXp) {
		xpLevelString = getFormatted(xpLevel) + " / " + getFormatted(maxXp) + " " + I18n.format("xp_levels");
		xpString = getFormatted(xpStored) + " / " + getFormatted(XpCalculation.getXpBarCapacity(xpLevel)) + " " + I18n.format("xp_singles");
	}
	
	public static String getFormatted(int value) {
		if (formatter == null) {
			formatter = new DecimalFormat("###,###");
		}
		String output = formatter.format(value);
		return output;
	}
	
}
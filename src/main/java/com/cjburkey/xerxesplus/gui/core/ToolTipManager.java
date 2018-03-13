package com.cjburkey.xerxesplus.gui.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;

/*
 * CREATED BY CRAZYPANTS, NOT CJ BURKEY
 * CJ DOES NOT CLAIM TO HAVE CREATED THIS
 */
public class ToolTipManager {
	
	public static interface ToolTipRenderer {
		int getGuiLeft();
		int getGuiTop();
		int getXSize();
		FontRenderer getFontRenderer();
		void drawTextS(List<String> par1List, int par2, int par3, FontRenderer font);
	}

	private List<GuiToolTip> toolTips = new ArrayList<GuiToolTip>();

	public void addToolTip(GuiToolTip toolTip) {
		if (!toolTips.contains(toolTip)) {
			toolTips.add(toolTip);
		}
	}
	
	public void clear() {
		toolTips.clear();
	}

	public boolean removeToolTip(GuiToolTip toolTip) {
		return toolTips.remove(toolTip);
	}

	public final void drawTooltips(ToolTipRenderer renderer, int mouseX, int mouseY) {
		for (GuiToolTip toolTip : toolTips) {
			toolTip.onTick(mouseX - renderer.getGuiLeft(), mouseY - renderer.getGuiTop());
			if (toolTip.shouldDraw()) {
				drawTooltip(toolTip, mouseX, mouseY, renderer);
			}
		}
	}

	protected void drawTooltip(GuiToolTip toolTip, int mouseX, int mouseY, ToolTipRenderer renderer) {
		List<String> list = toolTip.getToolTipText();
		if (list == null) {
			return;
		}

		List<String> formatted = new ArrayList<String>(list.size());
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				formatted.add("\u00a7f" + list.get(i));
			} else {
				formatted.add("\u00a77" + list.get(i));
			}
		}

		if (mouseX > renderer.getGuiLeft() + renderer.getXSize() / 2) {
			int maxWidth = 0;
			Iterator<String> iterator = formatted.iterator();
			while (iterator.hasNext()) {
				String s = (String) iterator.next();
				int w = renderer.getFontRenderer().getStringWidth(s);
				if (w > maxWidth) {
					maxWidth = w;
				}
			}
			mouseX -= (maxWidth + 18);
		}
		renderer.drawTextS(formatted, mouseX, mouseY, renderer.getFontRenderer());
	}
	
}
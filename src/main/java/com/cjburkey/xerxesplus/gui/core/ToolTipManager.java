package com.cjburkey.xerxesplus.gui.core;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;

public class ToolTipManager {

    public interface ToolTipRenderer {

        int getGuiLeft();

        int getGuiTop();

        int getXSize();

        FontRenderer getFontRenderer();

        void drawTextS(List<String> par1List, int par2, int par3, FontRenderer font);

    }

    private ObjectArrayList<GuiToolTip> toolTips = new ObjectArrayList<>();

    public void addToolTip(GuiToolTip toolTip) {
        if (!toolTips.contains(toolTip)) toolTips.add(toolTip);
    }

    @SuppressWarnings("unused")
    public void clear() {
        toolTips.clear();
    }

    @SuppressWarnings("unused")
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

    private void drawTooltip(GuiToolTip toolTip, int mouseX, int mouseY, ToolTipRenderer renderer) {
        List<String> list = toolTip.getToolTipText();
        if (list == null) return;

        List<String> formatted = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) formatted.add("\u00a7f" + list.get(i));
            else formatted.add("\u00a77" + list.get(i));
        }

        if (mouseX > renderer.getGuiLeft() + renderer.getXSize() / 2) {
            int maxWidth = 0;
            for (String s : formatted) {
                int w = renderer.getFontRenderer().getStringWidth(s);
                if (w > maxWidth) maxWidth = w;
            }
            mouseX -= (maxWidth + 18);
        }
        renderer.drawTextS(formatted, mouseX, mouseY, renderer.getFontRenderer());
    }

}

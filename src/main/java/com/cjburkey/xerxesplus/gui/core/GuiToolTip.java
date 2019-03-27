package com.cjburkey.xerxesplus.gui.core;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

/*
 * CREATED BY CRAZYPANTS, NOT CJ BURKEY
 * CJ DOES NOT CLAIM TO HAVE CREATED THIS
 */
public class GuiToolTip {

    private static final long DELAY = 0;
    private Rectangle bounds;
    private long mouseOverStart;
    private final ObjectArrayList<String> text;
    private int lastMouseX = -1;
    private int lastMouseY = -1;
    private boolean visible = true;

    public GuiToolTip(Rectangle bounds, String... lines) {
        this.bounds = bounds;
        text = new ObjectArrayList<>();
        if (lines != null) Collections.addAll(text, lines);
    }

    @SuppressWarnings("unused")
    public boolean isVisible() {
        return visible;
    }

    @SuppressWarnings("unused")
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @SuppressWarnings("unused")
    public Rectangle getBounds() {
        return bounds;
    }

    @SuppressWarnings("unused")
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    void onTick(int mouseX, int mouseY) {
        if (lastMouseX != mouseX || lastMouseY != mouseY) mouseOverStart = 0;

        if (bounds.contains(mouseX, mouseY)) {
            if (mouseOverStart == 0) mouseOverStart = System.currentTimeMillis();
        } else mouseOverStart = 0;

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    boolean shouldDraw() {
        if (!visible) return false;
        updateText();
        if (mouseOverStart == 0) return false;
        return System.currentTimeMillis() - mouseOverStart >= DELAY;
    }

    private void updateText() {
    }

    @SuppressWarnings("unused")
    public void setToolTipText(String... lines) {
        text.clear();
        if (lines != null) Collections.addAll(text, lines);
    }

    List<String> getToolTipText() {
        return text;
    }

}

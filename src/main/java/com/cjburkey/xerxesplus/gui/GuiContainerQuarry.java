package com.cjburkey.xerxesplus.gui;

import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.block.BlockQuarry;
import com.cjburkey.xerxesplus.config.ModConfig;
import com.cjburkey.xerxesplus.container.ContainerQuarry;
import com.cjburkey.xerxesplus.packet.PacketHandler;
import com.cjburkey.xerxesplus.packet.PacketQuarryToServer;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import com.cjburkey.xerxesplus.util.TextHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiContainerQuarry extends GuiInventoryBase {

    private static ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/container/gui_quarry.png");

    private static boolean energyLow;
    private static String energyString = getEnergyString(0, 0);
    private static String posString = "0, 0, 0";

    public GuiContainerQuarry(IInventory player, TileEntityInventory inventory) {
        super(new ContainerQuarry(player, inventory), BlockQuarry.INV_DEF, TEXTURE, player, inventory);
    }

    public void drawGuiContainerForegroundLayer(int mx, int my) {
        super.drawGuiContainerForegroundLayer(mx, my);

        PacketHandler.sendToServer(new PacketQuarryToServer(inventory.getPos()));

        fontRenderer.drawString(energyString, (xSize - fontRenderer.getStringWidth(energyString)) / 2, 18, (energyLow) ? 0xdd4040 : 0x404040);
        fontRenderer.drawString(posString, (xSize - fontRenderer.getStringWidth(posString)) / 2, 30, 0x404040);
    }

    public static void updateValues(int x, int y, int z, int energy, int maxEnergy, boolean energyLow) {
        energyString = getEnergyString(energy, maxEnergy);
        posString = String.format("%s, %s, %s", x, y, z);
        GuiContainerQuarry.energyLow = energyLow;
    }

    private static String getEnergyString(int energy, int maxEnergy) {
        final String unit = ModConfig.misc.energyUnits;
        return String.format("%s %s / %s %s", TextHelper.getFormatted(energy), unit, TextHelper.getFormatted(maxEnergy), unit);
    }

}

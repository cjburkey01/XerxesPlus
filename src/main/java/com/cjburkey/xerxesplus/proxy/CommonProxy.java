package com.cjburkey.xerxesplus.proxy;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.block.BlockContainerAdvanced;
import com.cjburkey.xerxesplus.block.BlockContainerBasic;
import com.cjburkey.xerxesplus.block.BlockContainerExtreme;
import com.cjburkey.xerxesplus.block.BlockXpStore;
import com.cjburkey.xerxesplus.container.ContainerInventory;
import com.cjburkey.xerxesplus.container.ContainerInventory.InventoryDefinition;
import com.cjburkey.xerxesplus.container.ContainerQuarry;
import com.cjburkey.xerxesplus.container.ContainerTrash;
import com.cjburkey.xerxesplus.crafting.FurnaceRecipe;
import com.cjburkey.xerxesplus.gui.GuiContainerQuarry;
import com.cjburkey.xerxesplus.gui.GuiContainerTrash;
import com.cjburkey.xerxesplus.gui.GuiHandler;
import com.cjburkey.xerxesplus.gui.GuiHandler.GuiRegister;
import com.cjburkey.xerxesplus.item.ModItems;
import com.cjburkey.xerxesplus.packet.PacketHandler;
import com.cjburkey.xerxesplus.tile.ModTiles;
import com.cjburkey.xerxesplus.tile.TileEntityInventory;
import com.cjburkey.xerxesplus.tile.TileEntityTrash;
import com.cjburkey.xerxesplus.util.XpCalcHelper;
import com.cjburkey.xerxesplus.world.XerxesPlusOreGeneration;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public static int guiBasicContainerId;
    public static int guiAdvancedContainerId;
    public static int guiExtremeContainerId;
    public static int guiXpStoreId;
    public static int guiQuarryId;
    public static int guiTrashId;

    private void registerGuis() {
        final String GB = "com.cjburkey.xerxesplus.gui.";

        guiBasicContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerBasic.INV_DEF, GB + "GuiContainerBasic"));
        guiAdvancedContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerAdvanced.INV_DEF, GB + "GuiContainerAdvanced"));
        guiExtremeContainerId = GuiHandler.addGui(new GuiInventoryRegister(BlockContainerExtreme.INV_DEF, GB + "GuiContainerExtreme"));
        guiXpStoreId = GuiHandler.addGui(new GuiInventoryRegister(BlockXpStore.INV_DEF, GB + "GuiContainerXpStore"));
        guiQuarryId = GuiHandler.addGui(new GuiRegister() {
            public Object onServer(EntityPlayer player, World world, int x, int y, int z) {
                return new ContainerQuarry(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
            }

            public Object onClient(EntityPlayer player, World world, int x, int y, int z) {
                return new GuiContainerQuarry(player.inventory, (TileEntityInventory) world.getTileEntity(new BlockPos(x, y, z)));
            }
        });
        guiTrashId = GuiHandler.addGui(new GuiRegister() {
            public Object onServer(EntityPlayer player, World world, int x, int y, int z) {
                return new ContainerTrash(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
            }

            public Object onClient(EntityPlayer player, World world, int x, int y, int z) {
                return new GuiContainerTrash(player.inventory, (TileEntityTrash) world.getTileEntity(new BlockPos(x, y, z)));
            }
        });
    }

    public void construction(FMLConstructionEvent e) {
        XerxesPlus.logger.info("Constructing Xerxes Plus");
    }

    public void preinit(FMLPreInitializationEvent e) {
        XerxesPlus.logger.info("Preinitializing Xerxes Plus");

        XerxesPlus.logger.info("Registering tile entities");
        ModTiles.commonPreinit();

        XerxesPlus.logger.info("Initializing xp calculations");
        XpCalcHelper.commonPreinit();

        XerxesPlus.logger.info("Registering GUI handler");
        NetworkRegistry.INSTANCE.registerGuiHandler(XerxesPlus.instance, new GuiHandler());

        XerxesPlus.logger.info("Registering GUIs");
        registerGuis();

        XerxesPlus.logger.info("Registering packets");
        PacketHandler.commonPreinit();

        ModItems.tabXerxesPlus.setBackgroundImageName("xerxesplus_background.png");
    }

    public void init(FMLInitializationEvent e) {
        XerxesPlus.logger.info("Initializing Xerxes Plus");

        XerxesPlus.logger.info("Registering ore generation");
        GameRegistry.registerWorldGenerator(new XerxesPlusOreGeneration(), 0);

        XerxesPlus.logger.info("Registering furnace recipes");
        FurnaceRecipe.commonInit();
    }

    public void postinit(FMLPostInitializationEvent e) {
        XerxesPlus.logger.info("Postinitializing Xerxes Plus");
    }

    public static class GuiInventoryRegister extends GuiRegister {

        private InventoryDefinition invDef;
        private Class<?> guiClass;

        private GuiInventoryRegister(InventoryDefinition invDef, String guiClass) {
            this.invDef = invDef;
            try {
                this.guiClass = Class.forName(guiClass);
            } catch (ClassNotFoundException e) {
                XerxesPlus.logger.warn("GUI class not found. This is either a server or we're broken!");
                this.guiClass = null;
            }
        }

        public Object onServer(EntityPlayer player, World world, int x, int y, int z) {
            return new ContainerInventory(invDef, player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
        }

        public Object onClient(EntityPlayer player, World world, int x, int y, int z) {
            try {
                if (guiClass == null) {
                    return null;
                }
                return guiClass.getConstructor(IInventory.class, TileEntityInventory.class).newInstance(player.inventory, (TileEntityInventory) world.getTileEntity(new BlockPos(x, y, z)));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                XerxesPlus.logger.error("Failed to create inventory GUI on client: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

    }

}

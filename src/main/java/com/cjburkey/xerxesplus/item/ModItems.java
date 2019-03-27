package com.cjburkey.xerxesplus.item;

import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.block.ModBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("WeakerAccess")
@EventBusSubscriber(modid = ModInfo.MODID)
public class ModItems {

    public static Item meticulumIngot;

    public static Item itemUpgradeOpsPerSecond;
    public static Item itemUpgradeFortune;
    public static Item itemUpgradeRadius;

    private static void createItems() {
        meticulumIngot = createItem(new Item(), "ingot_meticulum");

        itemUpgradeOpsPerSecond = createItem(new ItemUpgrade(0, 64), "upgrade_0");
        itemUpgradeFortune = createItem(new ItemUpgrade(1, 3), "upgrade_1");
        itemUpgradeRadius = createItem(new ItemUpgrade(2, 8), "upgrade_2");
    }

    private static void createOreDictionary() {
        OreDictionary.registerOre("ingotMeticulum", new ItemStack(meticulumIngot));
        OreDictionary.registerOre("oreMeticulum", new ItemStack(ModBlocks.blockMeticulumOre));
    }

    // -- STATIC ITEM REGISTRATION -- //

    public static final CreativeTabs tabXerxesPlus = new CreativeTabs("tab_xerxesplus") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.blockMeticulumOre, 1);
        }
    };

    private static final List<Item> items = new ArrayList<>();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        XerxesPlus.logger.info("Registering items");
        createItems();
        for (Block block : ModBlocks.getBlocks()) {
            createItem(new ItemBlock(block), Objects.requireNonNull(block.getRegistryName()).getResourcePath());
        }
        for (Item item : items) {
            e.getRegistry().register(item);
        }
        createOreDictionary();
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent e) {
        XerxesPlus.logger.info("Registering renders");
        for (Item item : items) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }
    }

    private static Item createItem(Item item, String unlocName) {
        item.setUnlocalizedName(unlocName);
        item.setRegistryName(ModInfo.MODID, unlocName);
        item.setCreativeTab(tabXerxesPlus);
        items.add(item);
        return item;
    }

}

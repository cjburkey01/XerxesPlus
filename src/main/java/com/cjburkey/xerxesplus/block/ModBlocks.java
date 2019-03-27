package com.cjburkey.xerxesplus.block;

import com.cjburkey.xerxesplus.ModInfo;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.item.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("WeakerAccess")
@EventBusSubscriber(modid = ModInfo.MODID)
public class ModBlocks {

    public static Block blockMeticulumOre;
    public static Block blockContainerBasic;
    public static Block blockContainerAdvanced;
    public static Block blockContainerExtreme;
    public static Block blockXpStore;
    public static Block blockQuarry;
    public static Block blockTrash;

    private static void createBlocks() {
        blockMeticulumOre = createBlock(new BlockOre(2.5f), "block_meticulum_ore");
        blockContainerBasic = createBlock(new BlockContainerBasic(), "block_container_basic");
        blockContainerAdvanced = createBlock(new BlockContainerAdvanced(), "block_container_advanced");
        blockContainerExtreme = createBlock(new BlockContainerExtreme(), "block_container_extreme");
        blockXpStore = createBlock(new BlockXpStore(), "block_xp_store");
        blockQuarry = createBlock(new BlockQuarry(), "block_quarry");
        blockTrash = createBlock(new BlockTrash(), "block_trash");
    }

    // -- STATIC BLOCK REGISTRATION -- //

    private static final ObjectArrayList<Block> blocks = new ObjectArrayList<>();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        XerxesPlus.logger.info("Registering blocks");
        createBlocks();
        for (Block block : blocks) {
            e.getRegistry().register(block);
        }
    }

    private static Block createBlock(Block block, String unlocName) {
        block.setUnlocalizedName(unlocName);
        block.setRegistryName(ModInfo.MODID, unlocName);
        block.setCreativeTab(ModItems.tabXerxesPlus);
        blocks.add(block);
        return block;
    }

    public static Block[] getBlocks() {
        return blocks.toArray(new Block[0]);
    }

}

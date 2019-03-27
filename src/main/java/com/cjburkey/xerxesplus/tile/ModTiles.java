package com.cjburkey.xerxesplus.tile;

import com.cjburkey.xerxesplus.ModInfo;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTiles {

    public static void commonPreinit() {
        register(TileEntityContainerBasic.class, "tile_container_basic");
        register(TileEntityContainerAdvanced.class, "tile_container_advanced");
        register(TileEntityContainerExtreme.class, "tile_container_extreme");
        register(TileEntityXpStore.class, "tile_xp_store");
        register(TileEntityQuarry.class, "tile_quarry");
        register(TileEntityTrash.class, "tile_trash");
    }

    private static void register(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(ModInfo.MODID, name));
    }

}

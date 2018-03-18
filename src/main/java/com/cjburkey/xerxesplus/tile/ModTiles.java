package com.cjburkey.xerxesplus.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTiles {
	
	public static void commonPreinit() {
		GameRegistry.registerTileEntity(TileEntityContainerBasic.class, "tile_container_basic");
		GameRegistry.registerTileEntity(TileEntityContainerAdvanced.class, "tile_container_advanced");
		GameRegistry.registerTileEntity(TileEntityContainerExtreme.class, "tile_container_extreme");
		GameRegistry.registerTileEntity(TileEntityXpStore.class, "tile_xp_store");
		GameRegistry.registerTileEntity(TileEntityQuarry.class, "tile_quarry");
	}
	
}
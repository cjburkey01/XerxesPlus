package com.cjburkey.xerxesplus.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTiles {
	
	public static void commonPreinit() {
		GameRegistry.registerTileEntity(TileEntityContainerBasic.class, "tile_container_basic");
	}
	
}
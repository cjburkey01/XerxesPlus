package com.cjburkey.burkeyspower.proxy;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {
	
	public void construction(FMLConstructionEvent e) {
		super.construction(e);
	}
	
	public void preinit(FMLPreInitializationEvent e) {
		super.preinit(e);
	}
	
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}
	
	public void postinit(FMLPostInitializationEvent e) {
		super.postinit(e);
	}
	
}
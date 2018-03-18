package com.cjburkey.xerxesplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cjburkey.xerxesplus.crafting.PounderRecipe;
import com.cjburkey.xerxesplus.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = "required:jaopca", clientSideOnly = false,
	serverSideOnly = false, modLanguage = "java", canBeDeactivated = false)
public class XerxesPlus {
	
	public static final Logger logger = LogManager.getLogger(ModInfo.MODID);
	
	@Instance(owner = ModInfo.MODID)
	public static XerxesPlus instance;
	
	@SidedProxy(clientSide = ModInfo.PROXY_BASE + ".ClientProxy", serverSide = ModInfo.PROXY_BASE + ".ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void construction(FMLConstructionEvent e) {
		proxy.construction(e);
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent e) {
		proxy.preinit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent e) {
		proxy.postinit(e);
	}
	
	@EventHandler
	public void commReceive(IMCEvent e) {
		for (IMCMessage msg : e.getMessages()) {
			if (msg.key.equals("AddPounderRecipe")) {
				PounderRecipe.onAddRecipe(msg);
			}
		}
	}
	
}
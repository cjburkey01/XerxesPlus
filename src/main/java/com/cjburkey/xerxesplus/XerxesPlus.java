package com.cjburkey.xerxesplus;

import com.cjburkey.xerxesplus.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION,
        dependencies = "required:jaopca")
public class XerxesPlus {

    public static final Logger logger = LogManager.getLogger(ModInfo.MODID);

    @Instance(owner = ModInfo.MODID)
    public static XerxesPlus instance;

    @SidedProxy(clientSide = ModInfo.PROXY_BASE + ".ClientProxy", serverSide = ModInfo.PROXY_BASE + ".ServerProxy")
    public static CommonProxy proxy;

    @SuppressWarnings("unused")
    @EventHandler
    public void construction(FMLConstructionEvent e) {
        proxy.construction(e);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void preinit(FMLPreInitializationEvent e) {
        proxy.preinit(e);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void postinit(FMLPostInitializationEvent e) {
        proxy.postinit(e);
    }

}

package com.cjburkey.xerxesplus.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler {
	
	private static final List<GuiRegister> guis = new ArrayList<>();
	
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (guis.size() > id) {
			return guis.get(id).onServer(player, world, x, y, z);
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (guis.size() > id) {
			return guis.get(id).onClient(player, world, x, y, z);
		}
		return null;
	}
	
	public static int addGui(GuiRegister reg) {
		if (reg == null) {
			return -1;
		}
		guis.add(reg);
		return guis.size() - 1;
	}
	
	public static abstract class GuiRegister {
		
		public abstract Object onServer(EntityPlayer player, World world, int x, int y, int z);
		public abstract Object onClient(EntityPlayer player, World world, int x, int y, int z);
		
	}
	
}
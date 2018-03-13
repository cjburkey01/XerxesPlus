package com.cjburkey.xerxesplus.packet;

import com.cjburkey.xerxesplus.ModInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	public static final String PACKET_VARIABLE_SEPARATOR = ";;;;";
	
	private static SimpleNetworkWrapper network;
	
	public static void commonPreinit() {
		network = new SimpleNetworkWrapper(ModInfo.MODID);
		
		network.registerMessage(PacketXpToClient.Handler.class, PacketXpToClient.class, 0, Side.CLIENT);
		network.registerMessage(PacketXpToServer.Handler.class, PacketXpToServer.class, 1, Side.SERVER);
		
		network.registerMessage(PacketTakeXpToServer.Handler.class, PacketTakeXpToServer.class, 2, Side.SERVER);
	}
	
	public static void sendTo(EntityPlayerMP ply, IMessage msg) {
		network.sendTo(msg, ply);
	}
	
	public static void sendToServer(IMessage msg) {
		network.sendToServer(msg);
	}
	
	//public static SimpleNetworkWrapper getNetwork() {
	//	return network;
	//}
	
}
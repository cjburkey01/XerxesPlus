package com.cjburkey.xerxesplus.packet;

import java.util.List;
import com.cjburkey.xerxesplus.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	public static final String PACKET_VARIABLE_SEPARATOR = ";;;;";
	
	private static SimpleNetworkWrapper network;
	private static byte i = 0;
	
	public static void commonPreinit() {
		network = new SimpleNetworkWrapper(ModInfo.MODID);
		
		network.registerMessage(PacketXpToClient.Handler.class, PacketXpToClient.class, i ++, Side.CLIENT);
		network.registerMessage(PacketXpToServer.Handler.class, PacketXpToServer.class, i ++, Side.SERVER);
		
		network.registerMessage(PacketTakeXpToServer.Handler.class, PacketTakeXpToServer.class, i ++, Side.SERVER);
		
		network.registerMessage(PacketQuarryToClient.Handler.class, PacketQuarryToClient.class, i ++, Side.CLIENT);
		network.registerMessage(PacketQuarryToServer.Handler.class, PacketQuarryToServer.class, i ++, Side.SERVER);
		
		network.registerMessage(PacketQuarryParticleToClient.Handler.class, PacketQuarryParticleToClient.class, i ++, Side.CLIENT);
	}
	
	public static void sendTo(EntityPlayerMP ply, IMessage msg) {
		if (msg == null) {
			return;
		}
		network.sendTo(msg, ply);
	}
	
	public static void sendToServer(IMessage msg) {
		if (msg == null) {
			return;
		}
		network.sendToServer(msg);
	}
	
	public static void sendAround(IMessage msg, World world, BlockPos around, int range) {
		if (world.isRemote) {
			return;
		}
		List<EntityPlayer> plys = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(around.getX() - range, around.getY() - range, around.getZ() - range, around.getX() + range, around.getY() + range, around.getZ() + range));
		for (EntityPlayer ply : plys) {
			sendTo((EntityPlayerMP) ply, msg);
		}
	}
	
	//public static SimpleNetworkWrapper getNetwork() {
	//	return network;
	//}
	
}
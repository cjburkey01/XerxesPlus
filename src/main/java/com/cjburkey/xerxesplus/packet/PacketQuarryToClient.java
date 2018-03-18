package com.cjburkey.xerxesplus.packet;

import java.util.regex.Pattern;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.gui.GuiContainerQuarry;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketQuarryToClient implements IMessage {
	
	private int x;
	private int y;
	private int z;
	private int energy;
	private int maxEnergy;
	private boolean energyLow;
	
	public PacketQuarryToClient() {
	}
	
	public PacketQuarryToClient(int x, int y, int z, int energy, int maxEnergy, boolean energyLow) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.energy = energy;
		this.maxEnergy = maxEnergy;
		this.energyLow = energyLow;
	}
	
	public void fromBytes(ByteBuf buf) {
		String found = ByteBufUtils.readUTF8String(buf);
		String[] split = found.split(Pattern.quote(PacketHandler.PACKET_VARIABLE_SEPARATOR));
		if (split.length != 6) {
			XerxesPlus.logger.warn("Failed to decode packet: Could not split quarry packet on client into data");
			return;
		}
		try {
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			z = Integer.parseInt(split[2]);
			energy = Integer.parseInt(split[3]);
			maxEnergy = Integer.parseInt(split[4]);
			energyLow = Boolean.parseBoolean(split[5]);
		} catch(Exception e) {
			XerxesPlus.logger.warn("Failed to decode packet: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, x + PacketHandler.PACKET_VARIABLE_SEPARATOR + y + PacketHandler.PACKET_VARIABLE_SEPARATOR + z + PacketHandler.PACKET_VARIABLE_SEPARATOR + energy + PacketHandler.PACKET_VARIABLE_SEPARATOR + maxEnergy + PacketHandler.PACKET_VARIABLE_SEPARATOR + energyLow);
	}
	
	public static class Handler implements IMessageHandler<PacketQuarryToClient, IMessage> {
		
		public IMessage onMessage(PacketQuarryToClient msg, MessageContext ctx) {
			if (msg == null) {
				return null;
			}
			GuiContainerQuarry.updateValues(msg.x, msg.y, msg.z, msg.energy, msg.maxEnergy, msg.energyLow);
			return null;
		}
		
	}
	
}
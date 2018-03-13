package com.cjburkey.xerxesplus.packet;

import java.util.regex.Pattern;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.gui.GuiContainerXpStore;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketXpToClient implements IMessage {
	
	private int xpLevel;
	private int xpStored;
	private int maxXp;
	
	public PacketXpToClient() {
	}
	
	public PacketXpToClient(int xpLevel, int xpStored, int maxXp) {
		this.xpLevel = xpLevel;
		this.xpStored = xpStored;
		this.maxXp = maxXp;
	}
	
	public void fromBytes(ByteBuf buf) {
		String found = ByteBufUtils.readUTF8String(buf);
		String[] split = found.split(Pattern.quote(PacketHandler.PACKET_VARIABLE_SEPARATOR));
		if (split.length != 3) {
			XerxesPlus.logger.warn("Failed to decode packet: Could not split XP Store packet on client into xp stored and maximum");
			return;
		}
		try {
			xpLevel = Integer.parseInt(split[0]);
			xpStored = Integer.parseInt(split[1]);
			maxXp = Integer.parseInt(split[2]);
		} catch(Exception e) {
			XerxesPlus.logger.warn("Failed to decode packet: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, xpLevel + PacketHandler.PACKET_VARIABLE_SEPARATOR + xpStored + PacketHandler.PACKET_VARIABLE_SEPARATOR + maxXp);
	}
	
	public static class Handler implements IMessageHandler<PacketXpToClient, IMessage> {
		
		public IMessage onMessage(PacketXpToClient msg, MessageContext ctx) {
			if (msg == null) {
				return null;
			}
			GuiContainerXpStore.updateValues(msg.xpLevel, msg.xpStored, msg.maxXp);
			return null;
		}
		
	}
	
}
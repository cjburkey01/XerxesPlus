package com.cjburkey.xerxesplus.packet;

import java.util.regex.Pattern;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.config.ModConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketQuarryParticleToClient implements IMessage {
	
	private int x;
	private int y;
	private int z;
	
	public PacketQuarryParticleToClient() {
	}
	
	public PacketQuarryParticleToClient(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void fromBytes(ByteBuf buf) {
		String found = ByteBufUtils.readUTF8String(buf);
		String[] split = found.split(Pattern.quote(PacketHandler.PACKET_VARIABLE_SEPARATOR));
		if (split.length != 3) {
			XerxesPlus.logger.warn("Failed to decode packet: Could not split quarry packet on client into data");
			return;
		}
		try {
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			z = Integer.parseInt(split[2]);
		} catch(Exception e) {
			XerxesPlus.logger.warn("Failed to decode packet: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, x + PacketHandler.PACKET_VARIABLE_SEPARATOR + y + PacketHandler.PACKET_VARIABLE_SEPARATOR + z);
	}
	
	public static class Handler implements IMessageHandler<PacketQuarryParticleToClient, IMessage> {
		
		public IMessage onMessage(PacketQuarryParticleToClient msg, MessageContext ctx) {
			if (msg == null || !ModConfig.quarry.drawQuarryParticles) {
				return null;
			}
			World world = Minecraft.getMinecraft().player.world;
			world.spawnParticle(EnumParticleTypes.FLAME, msg.x + 0.5d, msg.y + 0.5d, msg.z + 0.5d, 0.0d, 0.0d, 0.0d);
			return null;
		}
		
	}
	
}
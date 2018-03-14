package com.cjburkey.xerxesplus.packet;

import java.util.regex.Pattern;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.tile.TileEntityXpStore;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTakeXpToServer implements IMessage {
	
	private int x;
	private int y;
	private int z;
	private int levels;
	
	public PacketTakeXpToServer() {
	}
	
	public PacketTakeXpToServer(int x, int y, int z, int levels) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.levels = levels;
	}
	
	public PacketTakeXpToServer(BlockPos pos, int levels) {
		this(pos.getX(), pos.getY(), pos.getZ(), levels);
	}
	
	public void fromBytes(ByteBuf buf) {
		String found = ByteBufUtils.readUTF8String(buf);
		String[] split = found.split(Pattern.quote(PacketHandler.PACKET_VARIABLE_SEPARATOR));
		if (split.length != 4) {
			XerxesPlus.logger.warn("Failed to decode packet: Could not split XP Store packet on server into blockpos");
			return;
		}
		try {
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			z = Integer.parseInt(split[2]);
			levels = Integer.parseInt(split[3]);
		} catch(Exception e) {
			XerxesPlus.logger.warn("Failed to decode packet: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, x + PacketHandler.PACKET_VARIABLE_SEPARATOR + y + PacketHandler.PACKET_VARIABLE_SEPARATOR + z + PacketHandler.PACKET_VARIABLE_SEPARATOR + levels);
	}
	
	public static class Handler implements IMessageHandler<PacketTakeXpToServer, IMessage> {
		
		public IMessage onMessage(PacketTakeXpToServer msg, MessageContext ctx) {
			if (msg == null) {
				return null;
			}
			BlockPos pos = new BlockPos(msg.x, msg.y, msg.z);
			TileEntity ent = ctx.getServerHandler().player.world.getTileEntity(pos);
			if (ent == null || !(ent instanceof TileEntityXpStore)) {
				return null;
			}
			((TileEntityXpStore) ent).updateStorage(ctx.getServerHandler().player, msg.levels, (msg.levels == Integer.MAX_VALUE || msg.levels == Integer.MIN_VALUE));
			return null;
		}
		
	}
	
}
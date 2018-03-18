package com.cjburkey.xerxesplus.packet;

import java.util.regex.Pattern;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.tile.TileEntityQuarry;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketQuarryToServer implements IMessage {
	
	private int x;
	private int y;
	private int z;
	
	public PacketQuarryToServer() {
	}
	
	public PacketQuarryToServer(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PacketQuarryToServer(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public void fromBytes(ByteBuf buf) {
		String found = ByteBufUtils.readUTF8String(buf);
		String[] split = found.split(Pattern.quote(PacketHandler.PACKET_VARIABLE_SEPARATOR));
		if (split.length != 3) {
			XerxesPlus.logger.warn("Failed to decode packet: Could not split get blockpos from quarry packet");
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
	
	public static class Handler implements IMessageHandler<PacketQuarryToServer, PacketQuarryToClient> {
		
		public PacketQuarryToClient onMessage(PacketQuarryToServer msg, MessageContext ctx) {
			if (msg == null) {
				return null;
			}
			BlockPos pos = new BlockPos(msg.x, msg.y, msg.z);
			TileEntity ent = ctx.getServerHandler().player.world.getTileEntity(pos);
			if (ent != null && ent instanceof TileEntityQuarry) {
				TileEntityQuarry quarry = (TileEntityQuarry) ent;
				BlockPos p = quarry.getCurrentPosition();
				return new PacketQuarryToClient(p.getX(), p.getY(), p.getZ(), quarry.getEnergyStored(), quarry.getMaxEnergyStored(), quarry.isEnergyLow());
			}
			return null;
		}
		
	}
	
}
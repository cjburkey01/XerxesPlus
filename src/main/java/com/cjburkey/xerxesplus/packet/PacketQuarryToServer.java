package com.cjburkey.xerxesplus.packet;

import com.cjburkey.xerxesplus.tile.TileEntityQuarry;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketQuarryToServer implements IMessage {

    private int x;
    private int y;
    private int z;

    @SuppressWarnings("unused")
    public PacketQuarryToServer() {
    }

    private PacketQuarryToServer(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketQuarryToServer(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(x)
                .writeInt(y)
                .writeInt(z);
    }

    public static class Handler implements IMessageHandler<PacketQuarryToServer, PacketQuarryToClient> {

        public PacketQuarryToClient onMessage(PacketQuarryToServer msg, MessageContext ctx) {
            if (msg == null) {
                return null;
            }
            BlockPos pos = new BlockPos(msg.x, msg.y, msg.z);
            TileEntity ent = ctx.getServerHandler().player.world.getTileEntity(pos);
            if (ent instanceof TileEntityQuarry) {
                TileEntityQuarry quarry = (TileEntityQuarry) ent;
                BlockPos p = quarry.getCurrentPosition();
                return new PacketQuarryToClient(p.getX(), p.getY(), p.getZ(), quarry.getEnergyStored(), quarry.getMaxEnergyStored(), quarry.isEnergyLow());
            }
            return null;
        }

    }

}

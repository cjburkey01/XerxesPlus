package com.cjburkey.xerxesplus.packet;

import com.cjburkey.xerxesplus.tile.TileEntityXpStore;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTakeXpToServer implements IMessage {

    private int x;
    private int y;
    private int z;
    private int levels;

    @SuppressWarnings("unused")
    public PacketTakeXpToServer() {
    }

    private PacketTakeXpToServer(int x, int y, int z, int levels) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.levels = levels;
    }

    public PacketTakeXpToServer(BlockPos pos, int levels) {
        this(pos.getX(), pos.getY(), pos.getZ(), levels);
    }

    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        levels = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(x)
                .writeInt(y)
                .writeInt(z)
                .writeInt(levels);
    }

    public static class Handler implements IMessageHandler<PacketTakeXpToServer, IMessage> {

        public IMessage onMessage(PacketTakeXpToServer msg, MessageContext ctx) {
            if (msg == null) {
                return null;
            }
            BlockPos pos = new BlockPos(msg.x, msg.y, msg.z);
            TileEntity ent = ctx.getServerHandler().player.world.getTileEntity(pos);
            if (!(ent instanceof TileEntityXpStore)) {
                return null;
            }
            ((TileEntityXpStore) ent).updateStorage(ctx.getServerHandler().player, msg.levels, (msg.levels == Integer.MAX_VALUE || msg.levels == Integer.MIN_VALUE));
            return null;
        }

    }

}

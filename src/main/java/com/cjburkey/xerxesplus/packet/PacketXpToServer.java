package com.cjburkey.xerxesplus.packet;

import com.cjburkey.xerxesplus.config.ModConfig;
import com.cjburkey.xerxesplus.tile.TileEntityXpStore;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketXpToServer implements IMessage {

    private int x;
    private int y;
    private int z;

    @SuppressWarnings("unused")
    public PacketXpToServer() {
    }

    private PacketXpToServer(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketXpToServer(BlockPos pos) {
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

    public static class Handler implements IMessageHandler<PacketXpToServer, PacketXpToClient> {

        public PacketXpToClient onMessage(PacketXpToServer msg, MessageContext ctx) {
            if (msg == null) {
                return null;
            }
            BlockPos pos = new BlockPos(msg.x, msg.y, msg.z);
            TileEntity ent = ctx.getServerHandler().player.world.getTileEntity(pos);
            if (ent instanceof TileEntityXpStore) {
                TileEntityXpStore xpStore = (TileEntityXpStore) ent;
                return new PacketXpToClient(xpStore.experienceLevel, (int) Math.floor(xpStore.experience * xpStore.getXpBarCapacity()), ModConfig.experienceStore.maxXp);
            }
            return null;
        }

    }

}

package com.cjburkey.xerxesplus.packet;

import com.cjburkey.xerxesplus.gui.GuiContainerXpStore;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketXpToClient implements IMessage {

    private int xpLevel;
    private int xpStored;
    private int maxXp;

    @SuppressWarnings("unused")
    public PacketXpToClient() {
    }

    PacketXpToClient(int xpLevel, int xpStored, int maxXp) {
        this.xpLevel = xpLevel;
        this.xpStored = xpStored;
        this.maxXp = maxXp;
    }

    public void fromBytes(ByteBuf buf) {
        xpLevel = buf.readInt();
        xpStored = buf.readInt();
        maxXp = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(xpLevel)
                .writeInt(xpStored)
                .writeInt(maxXp);
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

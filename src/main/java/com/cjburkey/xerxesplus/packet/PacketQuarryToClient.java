package com.cjburkey.xerxesplus.packet;

import com.cjburkey.xerxesplus.gui.GuiContainerQuarry;
import io.netty.buffer.ByteBuf;
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

    @SuppressWarnings("unused")
    public PacketQuarryToClient() {
    }

    PacketQuarryToClient(int x, int y, int z, int energy, int maxEnergy, boolean energyLow) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.energyLow = energyLow;
    }

    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        energy = buf.readInt();
        maxEnergy = buf.readInt();
        energyLow = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(x)
                .writeInt(y)
                .writeInt(z)
                .writeInt(energy)
                .writeInt(maxEnergy)
                .writeBoolean(energyLow);
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

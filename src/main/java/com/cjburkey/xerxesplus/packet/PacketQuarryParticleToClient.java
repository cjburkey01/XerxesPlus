package com.cjburkey.xerxesplus.packet;

import com.cjburkey.xerxesplus.config.ModConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketQuarryParticleToClient implements IMessage {

    private int x;
    private int y;
    private int z;

    @SuppressWarnings("unused")
    public PacketQuarryParticleToClient() {
    }

    public PacketQuarryParticleToClient(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

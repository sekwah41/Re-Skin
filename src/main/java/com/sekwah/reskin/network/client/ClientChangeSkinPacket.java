package com.sekwah.reskin.network.client;

import com.sekwah.reskin.client.ClientEventHook;
import com.sekwah.reskin.client.ClientSkinManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class ClientChangeSkinPacket implements IMessage {

    private String uuid;
    private String url;

    public ClientChangeSkinPacket() {
    }

    public ClientChangeSkinPacket(String uuid, String url) {
        this.uuid = uuid;
        this.url = url;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        this.uuid = packetBuffer.readString(Short.MAX_VALUE);
        this.url = packetBuffer.readString(Short.MAX_VALUE);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeString(this.uuid);
        packetBuffer.writeString(this.url);
    }

    public static class Handler implements IMessageHandler<ClientChangeSkinPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientChangeSkinPacket message, MessageContext ctx) {
            ClientSkinManager.setSkin(UUID.fromString(message.uuid), message.url);
            return null;
        }
    }
}

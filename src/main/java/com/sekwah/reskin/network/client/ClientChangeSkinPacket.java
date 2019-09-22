package com.sekwah.reskin.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientChangeSkinPacket implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<ClientChangeSkinPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientChangeSkinPacket message, MessageContext ctx) {

            return null;
        }
    }
}

package com.sekwah.reskin.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerSetSkinPacket implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<ServerSetSkinPacket, IMessage> {

        @Override
        public IMessage onMessage(ServerSetSkinPacket message, MessageContext ctx) {

            return null;
        }
    }
}

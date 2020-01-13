package com.sekwah.reskin.network.server;

import com.sekwah.reskin.common.CustomSkinManager;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerRequestSkinsPacket implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<ServerRequestSkinsPacket, IMessage> {

        @Override
        public IMessage onMessage(ServerRequestSkinsPacket message, MessageContext ctx) {
            CustomSkinManager.sendAllToPlayer(ctx.getServerHandler().player, false);
            return null;
        }
    }
}

package com.sekwah.reskin.network.server;

import com.sekwah.reskin.ReSkin;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class ServerRequestSkins {

    public static void encode(ServerRequestSkins msg, FriendlyByteBuf outBuffer) {
    }

    public static ServerRequestSkins decode(FriendlyByteBuf inBuffer) {
        return new ServerRequestSkins();
    }

    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        ReSkin.LOGGER.info("Request Skin Packet");
        ServerRequestSkins msg = decode(buf);
            /*server.execute(() ->
                    CustomSkinManager.sendAllToPlayer(ctx.get().getSender(), false));*/
    }
}

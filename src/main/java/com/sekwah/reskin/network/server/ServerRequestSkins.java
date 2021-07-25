package com.sekwah.reskin.network.server;

import com.sekwah.reskin.CustomSkinManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerRequestSkins {

    public static void encode(ServerRequestSkins msg, FriendlyByteBuf outBuffer) {
    }

    public static ServerRequestSkins decode(FriendlyByteBuf inBuffer) {
        return new ServerRequestSkins();
    }

    public static class Handler {
        public static void handle(ServerRequestSkins msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    CustomSkinManager.sendAllToPlayer(ctx.get().getSender(), false));
            ctx.get().setPacketHandled(true);
        }
    }
}

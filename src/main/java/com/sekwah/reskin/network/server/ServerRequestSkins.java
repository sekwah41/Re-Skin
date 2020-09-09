package com.sekwah.reskin.network.server;

import com.sekwah.reskin.CustomSkinManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerRequestSkins {

    public static void encode(ServerRequestSkins msg, PacketBuffer outBuffer) {
    }

    public static ServerRequestSkins decode(PacketBuffer inBuffer) {
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

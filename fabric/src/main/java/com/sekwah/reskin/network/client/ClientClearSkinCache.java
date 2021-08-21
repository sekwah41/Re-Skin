package com.sekwah.reskin.network.client;

import com.sekwah.reskin.ReSkin;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;

public class ClientClearSkinCache {

    public static void encode(ClientClearSkinCache msg, FriendlyByteBuf outBuffer) {
    }

    public static ClientClearSkinCache decode(FriendlyByteBuf inBuffer) {
        return new ClientClearSkinCache();
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        ClientClearSkinCache msg = decode(buf);
        ReSkin.LOGGER.info("Clear Skin Cache Packet");
//            client.execute(() ->
//                    ClientSkinManager.clearSkinCache());
    }
}

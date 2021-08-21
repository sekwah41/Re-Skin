package com.sekwah.reskin.network.client;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.network.NetworkConst;
import com.sekwah.reskin.network.PacketEncoder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ClientClearSkinCache implements PacketEncoder {

    @Override
    public ResourceLocation getPacketId() {
        return NetworkConst.CLIENT_CLEAR_SKIN_CACHE_PACKET;
    }

    @Override
    public FriendlyByteBuf encode() {
        return PacketByteBufs.create();
    }

    public void encode(ClientClearSkinCache msg, FriendlyByteBuf outBuffer) {
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

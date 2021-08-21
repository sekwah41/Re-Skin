package com.sekwah.reskin.network.client;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.client.ClientSkinManager;
import com.sekwah.reskin.network.NetworkConst;
import com.sekwah.reskin.network.PacketEncoder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

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

    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        ReSkin.LOGGER.info("Clear Skin Cache Packet");
        server.execute(ClientSkinManager::clearSkinCache);
    }
}

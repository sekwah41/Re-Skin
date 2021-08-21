package com.sekwah.reskin.network.server;

import com.sekwah.reskin.network.NetworkConst;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class ServerPacketHandler {

    public static void registerServerPackets() {
        wrapPacket(NetworkConst.SERVER_REQUEST_SKINS_PACKET, ServerRequestSkins::receive);
    }

    public static void wrapPacket(ResourceLocation location, ServerPlayNetworking.PlayChannelHandler recieve) {
        ServerPlayNetworking.registerGlobalReceiver(location, recieve);
    }
}

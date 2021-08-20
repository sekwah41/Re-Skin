package com.sekwah.reskin.network.server;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.network.NetworkConst;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class ServerPacketHandler {

    public static void registerServerPackets() {
        wrapPacket(NetworkConst.SERVER_REQUEST_SKINS_PACKET);
    }

    public static void wrapPacket(ResourceLocation location) {
        ServerPlayNetworking.registerGlobalReceiver(location, (server, player, handler, buf, responseSender) -> {
            ReSkin.LOGGER.info("Server packet {}", location.toString());
        });
    }
}

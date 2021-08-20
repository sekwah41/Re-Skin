package com.sekwah.reskin.network.client;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.network.NetworkConst;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ClientPacketHandler {

    public static void registerClientPackets() {
        wrapPacket(NetworkConst.CLIENT_SKIN_CHANGE_PACKET);
        wrapPacket(NetworkConst.CLIENT_CLEAR_SKIN_CACHE_PACKET);
    }

    public static void wrapPacket(ResourceLocation location) {
        ClientPlayNetworking.registerGlobalReceiver(location, (client, handler, buf, responseSender) -> {
            ReSkin.LOGGER.info("Client packet {}", location.toString());
        });
    }
}

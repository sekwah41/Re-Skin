package com.sekwah.reskin.network.client;

import com.sekwah.reskin.network.NetworkConst;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ClientPacketHandler {

    public static void registerClientPackets() {
        wrapPacket(NetworkConst.CLIENT_SKIN_CHANGE_PACKET, ClientChangeSkin::receive);
        wrapPacket(NetworkConst.CLIENT_CLEAR_SKIN_CACHE_PACKET, ClientClearSkinCache::receive);
    }

    public static void wrapPacket(ResourceLocation location, ClientPlayNetworking.PlayChannelHandler recieve) {
        ClientPlayNetworking.registerGlobalReceiver(location, recieve);
    }
}

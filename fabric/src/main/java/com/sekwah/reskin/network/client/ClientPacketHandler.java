package com.sekwah.reskin.network.client;

import com.sekwah.reskin.network.NetworkConst;
import com.sekwah.reskin.network.PacketEncoder;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

/**
 * These are for packets that will be sent to the client.
 *
 *  The name may be confusing but its to keep common to the forge project.
 */
public class ClientPacketHandler {

    public static void registerClientPackets() {
        wrapPacket(NetworkConst.CLIENT_SKIN_CHANGE_PACKET, ClientChangeSkin::receive);
        wrapPacket(NetworkConst.CLIENT_CLEAR_SKIN_CACHE_PACKET, ClientClearSkinCache::receive);
    }

    public static void wrapPacket(ResourceLocation location, ServerPlayNetworking.PlayChannelHandler recieve) {
        ServerPlayNetworking.registerGlobalReceiver(location, recieve);
    }

    public static void sendToPlayer(PacketEncoder packet, ServerPlayer player) {
        ServerPlayNetworking.send(player, packet.getPacketId(), packet.encode());
    }

    public static void sendToAll(PacketEncoder packet, MinecraftServer server) {
        for(ServerPlayer player : PlayerLookup.all(server)) {
            ServerPlayNetworking.send(player, packet.getPacketId(), packet.encode());
        }
    }
}

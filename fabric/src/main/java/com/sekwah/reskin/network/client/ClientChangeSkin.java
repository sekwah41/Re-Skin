package com.sekwah.reskin.network.client;

import com.sekwah.reskin.ReSkin;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class ClientChangeSkin {

    public final UUID uuid;
    public final String url;
    public final String bodyType;
    public final boolean isTransparent;

    public ClientChangeSkin(UUID uuid, String url, String bodyType, boolean isTransparent) {
        this.uuid = uuid;
        this.url = url;
        this.bodyType = bodyType;
        this.isTransparent = isTransparent;
    }

    public static void encode(ClientChangeSkin msg, FriendlyByteBuf outBuffer) {
        outBuffer.writeUUID(msg.uuid);
        outBuffer.writeUtf(msg.url);
        outBuffer.writeUtf(msg.bodyType);
        outBuffer.writeBoolean(msg.isTransparent);
    }

    public static ClientChangeSkin decode(FriendlyByteBuf inBuffer) {
        UUID uuid = inBuffer.readUUID();
        String url = inBuffer.readUtf();
        String bodyType = inBuffer.readUtf();
        boolean isTransparent = inBuffer.readBoolean();

        return new ClientChangeSkin(uuid, url, bodyType, isTransparent);
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        ClientChangeSkin msg = decode(buf);
        ReSkin.LOGGER.info("Change Skin Packet");
//      client.execute(() ->
//          ClientSkinManager.setSkin(msg.uuid, msg.url, msg.bodyType, msg.isTransparent));
    }
}

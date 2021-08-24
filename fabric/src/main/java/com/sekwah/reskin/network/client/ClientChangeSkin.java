package com.sekwah.reskin.network.client;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.client.ClientSkinManager;
import com.sekwah.reskin.network.NetworkConst;
import com.sekwah.reskin.network.PacketEncoder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.UUID;

public class ClientChangeSkin implements PacketEncoder {

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

    @Override
    public ResourceLocation getPacketId() {
        return NetworkConst.CLIENT_SKIN_CHANGE_PACKET;
    }

    @Override
    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUUID(this.uuid);
        buf.writeUtf(this.url);
        buf.writeUtf(this.bodyType);
        buf.writeBoolean(this.isTransparent);
        return buf;
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
        client.execute(() ->
                ClientSkinManager.setSkin(msg.uuid, msg.url, msg.bodyType, msg.isTransparent));
    }
}

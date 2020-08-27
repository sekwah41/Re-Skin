package com.sekwah.reskin.network.client;

import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientChangeSkin {

    public final String uuid;
    public final String url;
    public final boolean isTransparent;

    public ClientChangeSkin(String uuid, String url, boolean isTransparent) {
        this.uuid = uuid;
        this.url = url;
        this.isTransparent = isTransparent;
    }

    public static void encode(ClientChangeSkin msg, PacketBuffer outBuffer) {
        outBuffer.writeString(msg.uuid);
        outBuffer.writeString(msg.url);
        outBuffer.writeBoolean(msg.isTransparent);
    }

    public static ClientChangeSkin decode(PacketBuffer inBuffer) {

        String uuid = inBuffer.readString();
        String url = inBuffer.readString();
        boolean isTransparent = inBuffer.readBoolean();

        return new ClientChangeSkin(uuid, url, isTransparent);
    }

    public static class Handler {
        public static void handle(ClientChangeSkin msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ClientSkinManager.setSkin(UUID.fromString(msg.uuid), msg.url, msg.isTransparent);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

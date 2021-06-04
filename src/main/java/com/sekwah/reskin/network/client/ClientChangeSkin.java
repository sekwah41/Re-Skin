package com.sekwah.reskin.network.client;

import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientChangeSkin {

    public final String uuid;
    public final String url;
    public final String bodyType;
    public final boolean isTransparent;

    public ClientChangeSkin(String uuid, String url, String bodyType, boolean isTransparent) {
        this.uuid = uuid;
        this.url = url;
        this.bodyType = bodyType;
        this.isTransparent = isTransparent;
    }

    public static void encode(ClientChangeSkin msg, PacketBuffer outBuffer) {
        outBuffer.writeUtf(msg.uuid);
        outBuffer.writeUtf(msg.url);
        outBuffer.writeUtf(msg.bodyType);
        outBuffer.writeBoolean(msg.isTransparent);
    }

    public static ClientChangeSkin decode(PacketBuffer inBuffer) {

        String uuid = inBuffer.readUtf();
        String url = inBuffer.readUtf();
        String bodyType = inBuffer.readUtf();
        boolean isTransparent = inBuffer.readBoolean();

        return new ClientChangeSkin(uuid, url, bodyType, isTransparent);
    }

    public static class Handler {
        public static void handle(ClientChangeSkin msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    ClientSkinManager.setSkin(UUID.fromString(msg.uuid), msg.url, msg.bodyType, msg.isTransparent));
            ctx.get().setPacketHandled(true);
        }
    }
}

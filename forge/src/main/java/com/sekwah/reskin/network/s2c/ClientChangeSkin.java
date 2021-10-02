package com.sekwah.reskin.network.s2c;

import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

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

    public static class Handler {
        public static void handle(ClientChangeSkin msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    ClientSkinManager.setSkin(msg.uuid, msg.url, msg.bodyType, msg.isTransparent));
            ctx.get().setPacketHandled(true);
        }
    }
}

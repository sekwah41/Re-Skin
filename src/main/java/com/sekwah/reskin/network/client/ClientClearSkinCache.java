package com.sekwah.reskin.network.client;

import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientClearSkinCache {

    public static void encode(ClientClearSkinCache msg, PacketBuffer outBuffer) {
    }

    public static ClientClearSkinCache decode(PacketBuffer inBuffer) {
        return new ClientClearSkinCache();
    }

    public static class Handler {
        public static void handle(ClientClearSkinCache msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                ClientSkinManager.clearSkinCache());
            ctx.get().setPacketHandled(true);
        }
    }
}

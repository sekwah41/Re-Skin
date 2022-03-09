package com.sekwah.reskin.network.s2c;

import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientClearSkinCache {

    public static void encode(ClientClearSkinCache msg, FriendlyByteBuf outBuffer) {
    }

    public static ClientClearSkinCache decode(FriendlyByteBuf inBuffer) {
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

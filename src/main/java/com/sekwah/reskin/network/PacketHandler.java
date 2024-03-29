package com.sekwah.reskin.network;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.network.s2c.ClientClearSkinCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    public static final String PROTOCOL_VERSION = "2";

    /**
     * Could just use {@link NetworkRegistry#newSimpleChannel} but this is more descriptive.
     */
    public static final SimpleChannel SKIN_CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(ReSkin.MOD_ID, "skin_data"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void init() {
        SKIN_CHANNEL.registerMessage(1, ClientClearSkinCache.class, ClientClearSkinCache::encode, ClientClearSkinCache::decode, ClientClearSkinCache.Handler::handle);
    }

    public static void sendToPlayer(Object obj, ServerPlayer player) {
        SKIN_CHANNEL.sendTo(obj, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}

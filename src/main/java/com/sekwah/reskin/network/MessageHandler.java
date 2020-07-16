package com.sekwah.reskin.network;

import com.sekwah.reskin.ReSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MessageHandler {

    public static final String PROTOCOL_VERSION = "1";

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
        //SKIN_CHANNEL.registerMessage(0,/*stuff*/)
        // Old packets (Mimic these)
        /*packetNetwork.registerMessage(ClientChangeSkinPacket.Handler.class, ClientChangeSkinPacket.class, 0, Side.CLIENT);
        packetNetwork.registerMessage(ServerRequestSkinsPacket.Handler.class, ServerRequestSkinsPacket.class, 100, Side.SERVER);*/
    }
}

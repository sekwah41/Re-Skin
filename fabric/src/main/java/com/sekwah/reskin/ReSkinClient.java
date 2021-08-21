package com.sekwah.reskin;

import com.sekwah.reskin.network.client.ClientPacketHandler;
import net.fabricmc.api.ClientModInitializer;

public class ReSkinClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ReSkin.LOGGER.info("Initialising Re:Skin client");

    }

}

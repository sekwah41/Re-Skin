package com.sekwah.reskin.common;

import com.google.common.collect.Maps;
import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.network.client.ClientChangeSkinPacket;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.UUID;

/**
 * This handles server side who has what links for skins
 */
public class CustomSkinManager {

    private Map<String, ResourceLocation> cachedUrls = Maps.newHashMap();

    public static void setSkin(UUID uniqueID, String url) {
        /**
         * TODO swap to all tracking and handle skin loading more dynamically. Dont need to load 10000 skins for offline players or not nearby ones.
         */
        ReSkin.packetNetwork.sendToAll(new ClientChangeSkinPacket(uniqueID.toString(), url));
    }
}

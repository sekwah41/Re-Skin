package com.sekwah.reskin.common;

import com.google.common.collect.Maps;
import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.common.capabilities.SkinLocationProvider;
import com.sekwah.reskin.network.client.ClientChangeSkinPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.server.FMLServerHandler;
import java.util.Map;
import java.util.UUID;

/**
 * This handles server side who has what links for skins
 */
public class CustomSkinManager {

    private static Map<UUID, String> playerSkins = Maps.newHashMap();

    public static void playerLoggedOut(UUID uuid) {
        playerSkins.remove(uuid);
    }

    public static void sendAllToPlayer(EntityPlayerMP player, boolean excludeSelf) {
        for(Map.Entry<UUID, String> skin : playerSkins.entrySet()) {
            if(!(excludeSelf && skin.getKey() == player.getUniqueID())) {
                ReSkin.packetNetwork.sendTo(new ClientChangeSkinPacket(skin.getKey().toString(), skin.getValue()), player);
            }
        }
    }

    public static void setSkin(UUID uuid, String url) {
        EntityPlayer player = ReSkin.getProxy().getFromUUID(uuid);
        setSkin(player, url);
    }
  
    public static void setSkin(EntityPlayer player, String url) {
        if(player != null) {
            player.getCapability(SkinLocationProvider.SKIN_LOC, null).setSkin(url);
            if(url.length() > 0) {
                ReSkin.packetNetwork.sendToAll(new ClientChangeSkinPacket(player.getUniqueID().toString(), url, SkinConfig.allowTransparentSkin));
                playerSkins.put(player.getUniqueID(), url);
            }
        }
    }
}

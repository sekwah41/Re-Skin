package com.sekwah.reskin;

import com.google.common.collect.Maps;
import com.sekwah.reskin.capabilities.SkinLocationProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;

public class CustomSkinManager {

    private static Map<UUID, String> playerSkins = Maps.newHashMap();

    public static void setSkin(PlayerEntity target, String url) {
        if(target != null) {
            target.getCapability(SkinLocationProvider.SKIN_LOC, null).ifPresent(skinCap -> skinCap.setSkin(url));
            // ToDo Add skin packet sending
        }
    }


    /**
     * Send all the loaded skins to a player
     * @param player
     * @param excludeSelf
     */
    public static void sendAllToPlayer(ServerPlayerEntity player, boolean excludeSelf) {
        for(Map.Entry<UUID, String> skin : playerSkins.entrySet()) {
            if(!(excludeSelf && skin.getKey() == player.getUniqueID())) {

            }
        }

    }

    public static void playerLoggedOut(UUID uniqueID) {

    }
}

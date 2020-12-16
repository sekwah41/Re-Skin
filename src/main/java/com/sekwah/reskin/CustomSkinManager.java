package com.sekwah.reskin;

import com.google.common.collect.Maps;
import com.sekwah.reskin.capabilities.SkinLocationProvider;
import com.sekwah.reskin.config.SkinConfig;
import com.sekwah.reskin.network.PacketHandler;
import com.sekwah.reskin.network.client.ClientChangeSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import java.util.Map;
import java.util.UUID;

public class CustomSkinManager {

    private static Map<UUID, String> playerSkins = Maps.newHashMap();

    public static void setSkin(PlayerEntity target, String url) {
        if(target != null) {
            target.getCapability(SkinLocationProvider.SKIN_LOC, null).ifPresent(skinCap -> skinCap.setSkin(url));
            if(url.length() > 0) {
                PacketHandler.SKIN_CHANNEL.send(PacketDistributor.ALL.noArg(), new ClientChangeSkin(target.getUniqueID().toString(), url, SkinConfig.ALLOW_TRANSPARENT_SKIN.get()));
                playerSkins.put(target.getUniqueID(), url);
            }
        }
    }


    /**
     * Send all the loaded skins to a player
     * @param player
     * @param excludeSelf
     */
    public static void sendAllToPlayer(ServerPlayerEntity player, boolean excludeSelf) {
        for(Map.Entry<UUID, String> skin : playerSkins.entrySet()) {
            if(!(excludeSelf && skin.getKey() == player.getUniqueID()) && skin.getValue() != null) {
                PacketHandler.sendToPlayer(new ClientChangeSkin(skin.getKey().toString(), skin.getValue(), SkinConfig.ALLOW_TRANSPARENT_SKIN.get()), player);
            }
        }

    }

    public static void playerLoggedOut(UUID uuid) {
        playerSkins.remove(uuid);
    }
}

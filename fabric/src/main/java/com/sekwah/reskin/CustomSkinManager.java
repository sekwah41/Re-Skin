package com.sekwah.reskin;

import com.google.common.collect.Maps;
import com.sekwah.reskin.components.ISkinData;
import com.sekwah.reskin.components.SkinComponent;
import com.sekwah.reskin.config.SkinConfig;
import com.sekwah.reskin.network.client.ClientChangeSkin;
import com.sekwah.reskin.network.client.ClientPacketHandler;
import com.sekwah.reskin.server.ServerSkinData;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public class CustomSkinManager {

    private static Map<UUID, ServerSkinData> playerSkins = Maps.newHashMap();

    private static SkinConfig config = AutoConfig.getConfigHolder(SkinConfig.class).getConfig();

    public static void setSkin(Player target, String url) {
        if(target != null) {
            ISkinData skinData = SkinComponent.SKIN_DATA.get(target);
            skinData.setSkin(url);
            if(url.length() > 0) {
                ClientPacketHandler.sendToAll(new ClientChangeSkin(target.getUUID(), url, skinData.getModelType(), config.allowTransparentSkin), target.getServer());
                playerSkins.put(target.getUUID(), new ServerSkinData(url, skinData.getModelType()));
            }
        }
    }

    /**
     * Reset the skin of the target player.
     *
     * @param target the target player
     */
    public static void resetSkin(Player target) {
        setSkin(target, "reset");
    }

    public static void setModel(Player target, String modelType) {
        if(target != null) {
            ISkinData skinData = SkinComponent.SKIN_DATA.get(target);
            skinData.setModelType(modelType);
            if(modelType.length() > 0) {
                ClientPacketHandler.sendToAll(new ClientChangeSkin(target.getUUID(), skinData.getSkin(), modelType, config.allowTransparentSkin), target.getServer());
                playerSkins.put(target.getUUID(), new ServerSkinData(skinData.getSkin(), modelType));
            }
        }
    }

    /**
     * Send all the loaded skins to a player
     * @param player
     * @param excludeSelf
     */
    public static void sendAllToPlayer(ServerPlayer player, boolean excludeSelf) {
        for(Map.Entry<UUID, ServerSkinData> skin : playerSkins.entrySet()) {
            if(!(excludeSelf && skin.getKey() == player.getUUID()) && skin.getValue() != null) {
                ClientPacketHandler.sendToPlayer(new ClientChangeSkin(skin.getKey(), skin.getValue().url, skin.getValue().modelType, config.allowTransparentSkin), player);
            }
        }
    }

    public static void playerLoggedOut(UUID uuid) {
        playerSkins.remove(uuid);
    }
}

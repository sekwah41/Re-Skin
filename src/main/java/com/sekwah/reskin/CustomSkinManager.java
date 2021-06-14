package com.sekwah.reskin;

import com.google.common.collect.Maps;
import com.sekwah.reskin.capabilities.SkinLocationProvider;
import com.sekwah.reskin.config.SkinConfig;
import com.sekwah.reskin.network.PacketHandler;
import com.sekwah.reskin.network.client.ClientChangeSkin;
import com.sekwah.reskin.server.ServerSkinData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Map;
import java.util.UUID;

public class CustomSkinManager {

    private static Map<UUID, ServerSkinData> playerSkins = Maps.newHashMap();

    public static void setSkin(PlayerEntity target, String url) {
        if(target != null) {
            target.getCapability(SkinLocationProvider.SKIN_LOC, null).ifPresent(skinCap -> {
                skinCap.setSkin(url);
                if(url.length() > 0) {
                    PacketHandler.SKIN_CHANNEL.send(PacketDistributor.ALL.noArg(), new ClientChangeSkin(target.getUUID(), url, skinCap.getModelType(), SkinConfig.ALLOW_TRANSPARENT_SKIN.get()));
                    playerSkins.put(target.getUUID(), new ServerSkinData(url, skinCap.getModelType()));
                }
            });
        }
    }

    public static void setModel(PlayerEntity target, String modelType) {
        if(target != null) {
            target.getCapability(SkinLocationProvider.SKIN_LOC, null).ifPresent(skinCap -> {
                skinCap.setModelType(modelType);
                if(modelType.length() > 0) {
                    PacketHandler.SKIN_CHANNEL.send(PacketDistributor.ALL.noArg(), new ClientChangeSkin(target.getUUID(), skinCap.getSkin(), modelType, SkinConfig.ALLOW_TRANSPARENT_SKIN.get()));
                    playerSkins.put(target.getUUID(), new ServerSkinData(skinCap.getSkin(), modelType));
                }
            });
        }
    }

    /**
     * Send all the loaded skins to a player
     * @param player
     * @param excludeSelf
     */
    public static void sendAllToPlayer(ServerPlayerEntity player, boolean excludeSelf) {
        for(Map.Entry<UUID, ServerSkinData> skin : playerSkins.entrySet()) {
            if(!(excludeSelf && skin.getKey() == player.getUUID()) && skin.getValue() != null) {
                PacketHandler.sendToPlayer(new ClientChangeSkin(skin.getKey(), skin.getValue().url, skin.getValue().modelType, SkinConfig.ALLOW_TRANSPARENT_SKIN.get()), player);
            }
        }
    }

    public static void playerLoggedOut(UUID uuid) {
        playerSkins.remove(uuid);
    }
}

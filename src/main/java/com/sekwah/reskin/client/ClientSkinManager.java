package com.sekwah.reskin.client;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.sekwah.reskin.ReSkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class ClientSkinManager {

    private static TextureManager textureManager;

    private static ResourceLocation missing = new ResourceLocation("textures/entity/steve.png");

    private static final Map<UUID, ResourceLocation> playerSkinMap = Maps.newHashMap();

    private static final Map<UUID, ResourceLocation> originalSkinMap = Maps.newHashMap();

    public static void getTextureManager() {
        textureManager = Minecraft.getInstance().getTextureManager();
    }

    public static void clearSkinCache() {
        for(Map.Entry<String, ResourceLocation> resources : cachedUrls.entrySet()) {
            textureManager.deleteTexture(resources.getValue());
        }
        cachedUrls.clear();

        for(Map.Entry<UUID, ResourceLocation> entry : playerSkinMap.entrySet()) {
            ResourceLocation resourceLocation = originalSkinMap.getOrDefault(entry.getKey(), missing);
            playerSkinMap.put(entry.getKey(), resourceLocation == null ? missing : resourceLocation);
        }
    }

    public static void cleanupSkinData() {
        playerSkinMap.clear();
        originalSkinMap.clear();
    }

    /**
     * URL map
     */
    private static Map<String, ResourceLocation> cachedUrls = Maps.newHashMap();

    private static List<SkinLoadJob> texturesToLoad = new ArrayList<>();

    public static void setSkin(UUID uuid, String url, boolean isTransparent) {
        if(url.equalsIgnoreCase("reset")) {
            if(originalSkinMap.containsKey(uuid)) {
                playerSkinMap.put(uuid, originalSkinMap.get(uuid));
            }
        }
        else if(cachedUrls.containsKey(url)) {
            playerSkinMap.put(uuid, cachedUrls.get(url));
        }
        else {
            texturesToLoad.add(new SkinLoadJob(uuid, url, isTransparent));
        }
    }

    public static void loadQueuedSkins() {
        Iterator<SkinLoadJob> i = texturesToLoad.iterator();
        while (i.hasNext()) {
            SkinLoadJob loadJob = i.next();
            ResourceLocation resourcelocation = new ResourceLocation("reskin", "skins/" + loadJob.url.hashCode());
            ReSkin.LOGGER.info("Downloading skin from: {}", loadJob.url);

            HDDownloadingTexture downloadingTexture = new HDDownloadingTexture(null, loadJob.url, missing, loadJob.isTransparent, null);
            if(downloadingTexture != null) {
                textureManager.loadTexture(resourcelocation, downloadingTexture);
            }
            else {
                resourcelocation = missing;
            }

            cachedUrls.put(loadJob.url, resourcelocation);
            playerSkinMap.put(loadJob.uuid, resourcelocation);
            i.remove();
        }
    }

    public static void checkSkin(AbstractClientPlayerEntity player) {
        if(player.playerInfo == null) return;
        ResourceLocation currentSkin = player.playerInfo.playerTextures.get(MinecraftProfileTexture.Type.SKIN);
        ResourceLocation wantedSkin = playerSkinMap.get(player.getUniqueID());
        if(wantedSkin != null && currentSkin != wantedSkin) {
            if(!originalSkinMap.containsKey(player.getUniqueID())) {
                originalSkinMap.put(player.getUniqueID(), player.playerInfo.playerTextures.get(MinecraftProfileTexture.Type.SKIN));
            }
            player.playerInfo.playerTextures.put(MinecraftProfileTexture.Type.SKIN, wantedSkin);
            // Add storing this data into the packets and alter the commands to allow changing the models
            player.playerInfo.skinType = "default";
            //player.playerInfo.skinType = "slim";
        }
    }
}

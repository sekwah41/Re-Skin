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

    private static ClientSkinData missing = new ClientSkinData(new ResourceLocation("textures/entity/steve.png"), "default");

    private static final Map<UUID, ClientSkinData> playerSkinMap = Maps.newHashMap();

    private static final Map<UUID, ClientSkinData> originalSkinMap = Maps.newHashMap();

    private static Map<String, ResourceLocation> cachedUrls = Maps.newHashMap();

    private static List<SkinLoadJob> texturesToLoad = new ArrayList<>();

    public static void getTextureManager() {
        textureManager = Minecraft.getInstance().getTextureManager();
    }

    public static void clearSkinCache() {
        for(Map.Entry<String, ResourceLocation> resources : cachedUrls.entrySet()) {
            textureManager.release(resources.getValue());
        }
        cachedUrls.clear();

        for(Map.Entry<UUID, ClientSkinData> entry : playerSkinMap.entrySet()) {
            ClientSkinData skinData = originalSkinMap.getOrDefault(entry.getKey(), missing);
            playerSkinMap.put(entry.getKey(), skinData == null ? missing : skinData);
        }
    }

    public static void cleanupSkinData() {
        playerSkinMap.clear();
        originalSkinMap.clear();
    }

    public static void setSkin(UUID uuid, String url, String bodyType, boolean isTransparent) {
        if(url.equalsIgnoreCase("reset")) {
            if(originalSkinMap.containsKey(uuid)) {
                playerSkinMap.put(uuid, originalSkinMap.get(uuid));
            }
        }
        else if(cachedUrls.containsKey(url)) {
            playerSkinMap.put(uuid, new ClientSkinData(cachedUrls.get(url), bodyType));
        }
        else {
            texturesToLoad.add(new SkinLoadJob(uuid, url, bodyType, isTransparent));
        }
    }

    public static void loadQueuedSkins() {
        Iterator<SkinLoadJob> i = texturesToLoad.iterator();
        while (i.hasNext()) {
            SkinLoadJob loadJob = i.next();
            ResourceLocation resourceLocation = new ResourceLocation("reskin", "skins/" + loadJob.url.hashCode());
            ReSkin.LOGGER.info("Downloading skin from: {}", loadJob.url);

            HDDownloadingTexture downloadingTexture = new HDDownloadingTexture(null, loadJob.url, missing.resourceLocation, loadJob.isTransparent, null);
            if(downloadingTexture != null) {
                textureManager.register(resourceLocation, downloadingTexture);
            }
            else {
                resourceLocation = missing.resourceLocation;
            }

            cachedUrls.put(loadJob.url, resourceLocation);
            playerSkinMap.put(loadJob.uuid, new ClientSkinData(resourceLocation, loadJob.bodyType));
            i.remove();
        }
    }

    public static void checkSkin(AbstractClientPlayerEntity player) {
        if(player.playerInfo == null) return;
        ResourceLocation currentSkin = player.playerInfo.textureLocations.get(MinecraftProfileTexture.Type.SKIN);
        ClientSkinData wantedSkin = playerSkinMap.get(player.getUUID());
        if(wantedSkin != null) {
            if(currentSkin != wantedSkin.resourceLocation) {
                if(!originalSkinMap.containsKey(player.getUUID())) {
                    originalSkinMap.put(player.getUUID(), new ClientSkinData(player.playerInfo.textureLocations.get(MinecraftProfileTexture.Type.SKIN), player.playerInfo.skinModel));
                }
                player.playerInfo.textureLocations.put(MinecraftProfileTexture.Type.SKIN, wantedSkin.resourceLocation);
            }

            player.playerInfo.skinModel = wantedSkin.modelType;
        }
    }
}
